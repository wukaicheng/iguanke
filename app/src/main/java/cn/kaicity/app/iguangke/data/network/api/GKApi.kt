package cn.kaicity.app.iguangke.data.network.api

import cn.kaicity.app.iguangke.data.bean.LoginBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface GKApi {

    @POST("user/login.do")
    @FormUrlEncoded
    suspend fun login(
        @Field("account") account: String,
        @Field("password") password: String,
        @Field("sign") sign: Int = 2
    ): Response<LoginBean>


    @POST()
    @FormUrlEncoded
    suspend fun getTicketByLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("service") service: String = "https://app.gkd.edu.cn/app/user/cas_login",
        @Field("loginType") loginType: String = "",
        @Url url: String = "https://cas.gkd.edu.cn/lyuapServer/v1/tickets"
    ): ResponseBody


    @POST()
    @FormUrlEncoded
    suspend fun getSTByTicket(
        @Url url: String,
        @Header("Cookie") cookie: String,
        @Field("service") service: String = "https://app.gkd.edu.cn/app/user/cas_login"
    ): ResponseBody

    @POST("user/getUserByToken")
    suspend fun getUserByToken(
        @Header("XPS-Token") token: String,
        @Header("XPS-UserId") id: String
    ): LoginBean

    @GET
    suspend fun getLocationByST(@Url url: String): Response<ResponseBody>

    @GET
    suspend fun casLogin(@Url url: String, @Header("Cookie") cookie: String): ResponseBody

}