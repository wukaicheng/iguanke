package cn.kaicity.app.iguangke.data.network.api

import cn.kaicity.app.iguangke.data.bean.ScoreBean
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST

interface FeatureApi {

    @POST("exam/queryScore")
    suspend fun getScore(@Header("XPS-Token") token:String,@Header("XPS-UserId") id:String):ScoreBean
}