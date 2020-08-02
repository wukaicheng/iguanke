package cn.kaicity.app.timetable

import java.io.Serializable


data class Schedule(
    var classTitle: String = "",
    var classPlace: String = "",
    var professorName: String = "",
    var day: Int = 0,
    var startClass: Int = 0,
    var endClass: Int = 0
)
