package cn.kaicity.app.iguangke.data.network.api

import cn.kaicity.app.iguangke.data.bean.MoneyBean
import cn.kaicity.app.iguangke.data.bean.ScoreBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FeatureApi {

    @POST("exam/queryScore")
    suspend fun getScore(): ScoreBean

    @POST("card/getCard.do")
    @FormUrlEncoded
    suspend fun getMoney(
        @Field("pageNo") pageNo: Int,
        @Field("pageSize") size: Int = 20,
        @Field("code") code: Int = 0
    ): MoneyBean
}