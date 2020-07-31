package cn.kaicity.app.iguangke.data.repository

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.LoginBean
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.network.api.LoginApi
import cn.kaicity.app.iguangke.util.EncryptUtil
import cn.kaicity.app.iguangke.util.LogUtil
import com.google.gson.JsonParser
import okhttp3.Headers
import java.util.regex.Pattern

@Suppress("BlockingMethodInNonBlockingContext")
class LoginRepository(private val service: LoginApi) {

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
                service.getLocationByST(st)
            val cookie = res.headers()["Set-Cookie"]
            if (cookie.isNullOrEmpty())
                throw Exception("登录失败，未知错误1")

            val htmlText =
                service.casLogin(cookie).string()
            val str = getAccountFromHtml(htmlText)
            if (str.isNullOrEmpty())
                throw  Exception("登录失败，未知错误2")

            val js = JsonParser.parseString(str).asJsonObject
            val loginResult =
                service.login(js.get("account").asString, js.asJsonObject.get("pwd").asString)

            val userBean = createUserBean(loginResult.body(), loginResult.headers())
            LogUtil.log(userBean.toString())
            mLoginLiveData.postValue(StateBean(StateBean.SUCCESS, bean = userBean))

        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.log(e)
            mLoginLiveData.postValue(StateBean(StateBean.FAIL, "${e.message}"))
        }
    }

    @Throws(Exception::class)
    private fun createUserBean(
        bean: LoginBean?,
        headers: Headers
    ): UserBean {
        if (bean == null) {
            throw Exception("登录失败，未知错误3")
        }

        if (bean.result == "1") {
            return bean.item.run {
                UserBean(
                    headImage = headImage,
                    name = xm,
                    nickName = nickName,
                    token = headers["XPS-Token"] ?: "",
                    userId = headers["XPS-UserId"] ?: "",
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

}
