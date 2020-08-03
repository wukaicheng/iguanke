package cn.kaicity.app.iguangke.data.network.api

import cn.kaicity.app.iguangke.data.bean.*
import retrofit2.http.*

interface FeatureApi {

    @POST("exam/queryScore")
    suspend fun getScore(): ScoreBean


    @POST("card/getCard.do")
    @FormUrlEncoded
    suspend fun getMoney(
        @Field("pageNo") pageNo: Int,
        @Field("pageSize") size: Int = 10,
        @Field("code") code: Int = 0
    ): MoneyBean


    @POST("information/getInformationList.do")
    @FormUrlEncoded
    suspend fun getNews(
        @Field("pageNo") pageNo: Int,
        @Field("pageSize") size: Int = 10,
        @Field("typeId") id: Int = 144
    ): NewsListBean

    @POST("information/getInformation.do")
    @FormUrlEncoded
    suspend fun getNewsDetail(
        @Field("informationId") pageNo: Int
    ): NewsBean


    @GET("settings/get.do")
    suspend fun getSetting(): SettingBean

    @POST("course/listCourse.do")
    @FormUrlEncoded
    suspend fun getCourse(@Field("week") week: Int): CourseBean


    @GET
    suspend fun checkUpdate(@Url url: String = "https://gitee.com/kaicity/iguanke/raw/master/version.json"): VersionBean
}