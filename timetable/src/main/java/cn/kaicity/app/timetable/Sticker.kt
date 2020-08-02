package cn.kaicity.app.timetable

import android.widget.TextView
import java.io.Serializable
import java.util.*

class Sticker : Serializable {
    val view: ArrayList<TextView> = ArrayList()
    private val schedules: ArrayList<Schedule> = ArrayList<Schedule>()
    fun addTextView(v: TextView) {
        view.add(v)
    }

    fun addSchedule(schedule: Schedule) {
        schedules.add(schedule)
    }

    fun getSchedules(): ArrayList<Schedule> {
        return schedules
    }

}
