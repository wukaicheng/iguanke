package cn.kaicity.app.iguangke.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetWorkCreator private constructor() {

    private val blockList = arrayOf("cas_login", "information")

    companion object {
        var token: String? = null

        var userId: String? = null

        val instance: NetWorkCreator by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetWorkCreator()
        }
    }

    private val baseUrl = "https://app.gkd.edu.cn/app/"
    private val mHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .followRedirects(false)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .addInterceptor { it ->
            val request = it.request().newBuilder().run {
                addHeader("Accept", "application/json;charset=utf-8")
                addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36"
                )


                val url = it.request().url.toString()
                val inBlock = blockList.filter {str->
                    url.contains(str)
                }.isEmpty()

                if (url.startsWith(baseUrl) &&inBlock) {
                    if (token != null) {
                        addHeader("XPS-Token", token!!)
                    }

                    if (userId != null) {
                        addHeader("XPS-UserId", userId!!)
                    }
                }

                build()
            }


            it.proceed(request)
        }
        .build()


    private val mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(mHttpClient)
        .build()


    fun <T> create(service: Class<T>): T = mRetrofit.create(service)

}