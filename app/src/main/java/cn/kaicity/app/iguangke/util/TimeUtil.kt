package cn.kaicity.app.iguangke.util

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {
    companion object{
        fun longToStr(time:Long):String{
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(time)).toString()
        }
        fun longToStrWithTime(time:Long):String{
            return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(time)).toString()
        }



    }
}