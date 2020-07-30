package cn.kaicity.app.iguangke.util

import android.util.Log
import java.lang.Exception

class LogUtil {

    companion object {

        fun log(any: Any?) {
            if (any == null) {
                log("Object is null")
            } else {
                //广科大搞快点
                Log.d("gkd", any.toString())
            }
        }

        fun log(exception: Exception) {
            exception.stackTrace.forEach {
                log(it.toString())
            }
        }
    }


}