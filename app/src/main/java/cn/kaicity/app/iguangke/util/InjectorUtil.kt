package cn.kaicity.app.iguangke.util

import cn.kaicity.app.iguangke.data.network.GKRetrofitService
import cn.kaicity.app.iguangke.data.repository.FeatureRepository
import cn.kaicity.app.iguangke.data.repository.LoginRepository
import cn.kaicity.app.iguangke.data.repository.UserRepository
import cn.kaicity.app.iguangke.ui.feature.FeatureFactory
import cn.kaicity.app.iguangke.ui.login.LoginFactory
import cn.kaicity.app.iguangke.ui.login.LoginViewModel
import cn.kaicity.app.iguangke.ui.main.MainFactory
import cn.kaicity.app.iguangke.ui.user.UserFactory

object InjectorUtil {

    private fun getLoginRepository(): LoginRepository = LoginRepository(GKRetrofitService.loginApi)

    private fun getUserRepository(): UserRepository = UserRepository(GKRetrofitService.loginApi)

    private fun getFeatureRepository(): FeatureRepository =
        FeatureRepository(GKRetrofitService.featureApi)

    fun getLoginFactory(): LoginFactory = LoginFactory(getLoginRepository())

    fun getMainFactory(): MainFactory = MainFactory(getUserRepository())

    fun getUserFactory(): UserFactory = UserFactory(getUserRepository())

    fun getFeatureFactory(): FeatureFactory = FeatureFactory(getFeatureRepository())
}