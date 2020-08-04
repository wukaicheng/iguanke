package cn.kaicity.app.iguangke.data.repository

import android.content.Context
import android.content.SharedPreferences
import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.BuildConfig
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.bean.VersionBean
import cn.kaicity.app.iguangke.data.network.NetWorkCreator
import cn.kaicity.app.iguangke.data.network.api.FeatureApi
import cn.kaicity.app.iguangke.data.network.api.LoginApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kunminx.architecture.ui.callback.UnPeekLiveData


class UserRepository(
    private val service: LoginApi,
    private val featureApi: FeatureApi
) {

    val sp: SharedPreferences = App.context.getSharedPreferences(KEYS.USER, Context.MODE_PRIVATE)

    fun saveUser(userBean: UserBean) {
        val userData = Gson().toJson(userBean)
        sp.edit().putString(KEYS.USER, userData).apply()

    }

    private fun getUserBySP(): UserBean? {

        val userData = sp.getString(KEYS.USER, null)
        if (userData != null && userData.isNotEmpty()) {

            val type = object : TypeToken<UserBean>() {}.type
            return Gson().fromJson<UserBean>(userData, type)
        }
        return null
    }


    private suspend fun getUserByToken(
        userBean: UserBean,
        mUserLiveData: UnPeekLiveData<StateBean<UserBean>>
    ) {
        val bean = service.getUserByToken()
        if (bean.result != "1") {
            mUserLiveData.postValue(StateBean(StateBean.TIMEOUT))
        } else {
            saveUser(userBean)
        }
    }

    suspend fun getUserBean(mUserLiveData: UnPeekLiveData<StateBean<UserBean>>) {

        val userBean = getUserBySP()
        if (userBean != null) {
            initTokenInOkHttp(userBean)
            mUserLiveData.postValue(StateBean(StateBean.SUCCESS, bean = userBean))
            getUserByToken(userBean, mUserLiveData)
        } else {
            mUserLiveData.postValue(StateBean(StateBean.FAIL))
        }

    }


    private fun initTokenInOkHttp(userBean: UserBean) {
        NetWorkCreator.token = userBean.token
        NetWorkCreator.userId = userBean.userId
    }


    fun clearCourse() {
        val shared: SharedPreferences =
            App.context.getSharedPreferences(KEYS.COURSE, Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.clear()
        editor.apply()
    }

    suspend fun checkUpdate(mUpdateLiveData: UnPeekLiveData<VersionBean>) {
        val bean = featureApi.checkUpdate()
        if (bean.version > BuildConfig.VERSION_CODE) {
            mUpdateLiveData.postValue(bean)
        }

    }

    fun loginOut() {
        sp.edit().clear().apply()
    }


}