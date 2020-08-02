package cn.kaicity.app.iguangke.util

import android.content.Context
import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.data.KEYS
import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {
    companion object {
        fun longToStr(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(time)).toString()
        }

        fun longToStrWithTime(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(time))
                .toString()
        }


        fun getWeek(): Int {
            val share = App.context.getSharedPreferences(KEYS.COURSE, Context.MODE_PRIVATE)
            val startTime = share.getString(KEYS.START_TIME, null) ?: return 0

            val sf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDate = sf.parse(startTime) ?: return 0

            val nowDate = Date()

            val diff = nowDate.time - startDate.time

            var week = diff / (1000 * 60 * 60 * 24 * 7)
            if (week > 20) {
                week = 20
            }

            return week.toInt()

        }
    }
}