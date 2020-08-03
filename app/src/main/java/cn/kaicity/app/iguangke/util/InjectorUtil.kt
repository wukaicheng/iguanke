package cn.kaicity.app.iguangke.util

import cn.kaicity.app.iguangke.data.network.GKRetrofitService
import cn.kaicity.app.iguangke.data.repository.CourseRepository
import cn.kaicity.app.iguangke.data.repository.FeatureRepository
import cn.kaicity.app.iguangke.data.repository.LoginRepository
import cn.kaicity.app.iguangke.data.repository.UserRepository
import cn.kaicity.app.iguangke.ui.feature.FeatureFactory
import cn.kaicity.app.iguangke.ui.feature.course.CourseFactory
import cn.kaicity.app.iguangke.ui.feature.news.NewsFactory
import cn.kaicity.app.iguangke.ui.login.LoginFactory
import cn.kaicity.app.iguangke.ui.main.MainFactory
import cn.kaicity.app.iguangke.ui.user.UserFactory

object InjectorUtil {

    private fun getLoginRepository(): LoginRepository = LoginRepository(GKRetrofitService.loginApi)

    private fun getUserRepository(): UserRepository = UserRepository(GKRetrofitService.loginApi,GKRetrofitService.featureApi)

    private fun getCourseRepository(): CourseRepository =
        CourseRepository(GKRetrofitService.featureApi)

    private fun getFeatureRepository(): FeatureRepository =
        FeatureRepository(GKRetrofitService.featureApi)

    fun getLoginFactory(): LoginFactory = LoginFactory(getLoginRepository())

    fun getMainFactory(): MainFactory = MainFactory(getUserRepository())

    fun getUserFactory(): UserFactory = UserFactory(getUserRepository())

    fun getFeatureFactory(): FeatureFactory = FeatureFactory(getFeatureRepository())

    fun getNewsRFactory(): NewsFactory = NewsFactory(getFeatureRepository())

    fun getCourseFactory(): CourseFactory = CourseFactory(getCourseRepository())
}