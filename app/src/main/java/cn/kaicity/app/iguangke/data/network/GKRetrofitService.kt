package cn.kaicity.app.iguangke.data.network

import cn.kaicity.app.iguangke.data.network.api.FeatureApi
import cn.kaicity.app.iguangke.data.network.api.LoginApi

object GKRetrofitService {
    val loginApi = NetWorkCreator.instance.create(LoginApi::class.java)

    val featureApi = NetWorkCreator.instance.create(FeatureApi::class.java)
}