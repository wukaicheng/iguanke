package cn.kaicity.app.iguangke.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.LoginBean
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.network.api.GKApi
import cn.kaicity.app.iguangke.util.EncryptUtil
import cn.kaicity.app.iguangke.util.LogUtil
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.Headers
import java.util.regex.Pattern

class LoginRepository(private val service: GKApi) {

    suspend fun login(
        account: String,
        passwd: String,
        mLoginLiveData: MutableLiveData<StateBean<UserBean>>
    ) {
        try {

            val password = EncryptUtil.rsa(passwd)
            val ticket = service.getTicketByLogin(account, password).string()

            isRealRes(ticket, "TGT")

            val st = service.getSTByTicket(
                "https://cas.gkd.edu.cn/lyuapServer/v1/tickets/$ticket",
                "CASTGC=$ticket"
            ).string()

            isRealRes(st, "ST")

            val res =
                service.getLocationByST("https://app.gkd.edu.cn/app/user/cas_login?ticket=$st")
            val cookie = res.headers()["Set-Cookie"]
            if (cookie.isNullOrEmpty())
                throw Exception("登录失败，未知错误1")

            val htmlText =
                service.casLogin("https://app.gkd.edu.cn/app/user/cas_login", cookie ?: "").string()
            var str = getAccountFromHtml(htmlText)
            if (str.isNullOrEmpty())
                throw  Exception("登录失败，未知错误2")

            val js = JsonParser.parseString(str).asJsonObject
            val loginResult =
                service.login(js.get("account").asString, js.asJsonObject.get("pwd").asString)

            val userBean = createUserBean(loginResult.body(),loginResult.headers())
            LogUtil.log(userBean.toString())
            mLoginLiveData.postValue(StateBean(StateBean.SUCCESS, "success",userBean))

        } catch (e: Exception) {
            LogUtil.log(e.message)
            mLoginLiveData.postValue(StateBean(StateBean.FAIL, "$e.message"))
        }
    }

    @Throws(Exception::class)
    private fun createUserBean(
        bean: LoginBean?,
        headers: Headers
    ): UserBean {
        if (bean==null){
            throw Exception("登录失败，未知错误3")
        }

        if (bean.result == "1") {
            return bean.item.run {
                UserBean(
                    headImage = headImage,
                    name = nickName,
                    token = headers["XPS-Token"]?:"",
                    userId = headers["XPS-UserId"]?:"",
                    xh = xh,
                    className = bjmc,
                    readIngYear = rxnj,
                    disciplineName = zymc,
                    collegeName = yxmc
                )
            }
        }

        throw Exception("登录失败，未知错误4")
    }

    /**
     * @param string String
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun isRealRes(string: String, start: String) {
        if (!string.startsWith(start))
            throw Exception("登录失败，账号或密码错误")
    }

    private fun getAccountFromHtml(html: String): String? {
        val pattern = Pattern.compile("\\{\"[\\S]+\\}")
        val matcher = pattern.matcher(html)
        matcher.find()
        return matcher.group()
    }

    fun save(userBean: UserBean) {
        val edit=App.context.getSharedPreferences(KEYS.USER,Context.MODE_PRIVATE).edit()
        val userData=Gson().toJson(userBean)
        edit.putString(KEYS.USER,userData)
        edit.apply()
    }

}
