package cn.kaicity.app.iguangke.data.bean

import android.graphics.Color
import com.google.gson.annotations.SerializedName

data class CourseBean(
    val items: CourseGround,
    val result: String
)

data class CourseGround(
    @SerializedName("1")
    val one: List<CourseItem>,

    @SerializedName("2")
    val two: List<CourseItem>,

    @SerializedName("3")
    val three: List<CourseItem>,

    @SerializedName("4")
    val four: List<CourseItem>,

    @SerializedName("5")
    val five: List<CourseItem>

)

data class CourseItem(
    val djj: String,
    val jsmc: String,
    val kcmc: String,
    val teacher_xm: String,
    val xqj: String
)
