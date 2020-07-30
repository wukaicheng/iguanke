package cn.kaicity.app.iguangke.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.network.api.LoginApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserRepository(private val service: LoginApi) {

    val sp = App.context.getSharedPreferences(KEYS.USER, Context.MODE_PRIVATE)

    fun saveUser(userBean: UserBean) {

        val userData = Gson().toJson(userBean)
        sp.edit().putString(KEYS.USER, userData).apply()

    }

    private fun getUserBySP():UserBean? {

        val userData=sp.getString(KEYS.USER,null)
        if (userData != null && userData.isNotEmpty()) {

            val type = object : TypeToken<UserBean>() {}.type
            return Gson().fromJson<UserBean>(userData, type)
        }
        return null
    }


    private suspend fun getUserByToken(
        userBean: UserBean,
        mUserLiveData: MutableLiveData<StateBean<UserBean>>
    ) {
        val bean = service.getUserByToken(userBean.token, userBean.userId)
        if (bean.result != "1") {
            mUserLiveData.postValue(StateBean(StateBean.TIMEOUT))
        }
        else{
            saveUser(userBean)
        }
    }

    suspend fun getUserBean(mUserLiveData: MutableLiveData<StateBean<UserBean>>) {

        val userBean = getUserBySP()
        if (userBean != null) {
            mUserLiveData.postValue(StateBean(StateBean.SUCCESS, bean = userBean))
            getUserByToken(userBean,mUserLiveData)
        } else {
            mUserLiveData.postValue(StateBean(StateBean.FAIL))
        }

    }

    suspend fun getOnlyUserBean(mUserLiveData: MutableLiveData<UserBean>) {

        val userBean = getUserBySP()
        if (userBean != null) {
            mUserLiveData.postValue(userBean)
        }
    }
}