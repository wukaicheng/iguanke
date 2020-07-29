package cn.kaicity.app.iguangke.data.network

import cn.kaicity.app.iguangke.data.network.api.GKApi

object GKRetrofitService {
    val service = NetWorkCreator.instance.create(GKApi::class.java)
}