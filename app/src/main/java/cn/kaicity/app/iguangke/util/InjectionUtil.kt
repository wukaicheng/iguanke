package cn.kaicity.app.iguangke.util

import cn.kaicity.app.iguangke.data.network.GKRetrofitService
import cn.kaicity.app.iguangke.data.repository.LoginRepository
import cn.kaicity.app.iguangke.ui.login.LoginViewModel

object InjectionUtil {

    private fun getLoginRepository(): LoginRepository = LoginRepository(GKRetrofitService.service)

    fun getLoginViewModel(): LoginViewModel = LoginViewModel(getLoginRepository())

}