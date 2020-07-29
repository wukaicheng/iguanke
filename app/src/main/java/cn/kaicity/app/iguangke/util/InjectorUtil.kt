package cn.kaicity.app.iguangke.util

import cn.kaicity.app.iguangke.data.network.GKRetrofitService
import cn.kaicity.app.iguangke.data.repository.LoginRepository
import cn.kaicity.app.iguangke.data.repository.UserRepository
import cn.kaicity.app.iguangke.ui.login.LoginFactory
import cn.kaicity.app.iguangke.ui.login.LoginViewModel
import cn.kaicity.app.iguangke.ui.main.MainFactory

object InjectorUtil {

    private fun getLoginRepository(): LoginRepository = LoginRepository(GKRetrofitService.service)

    private fun getUserRepository(): UserRepository = UserRepository(GKRetrofitService.service)

    fun getLoginFactory(): LoginFactory = LoginFactory(getLoginRepository())

    fun getMainFactory(): MainFactory = MainFactory(getUserRepository())

}