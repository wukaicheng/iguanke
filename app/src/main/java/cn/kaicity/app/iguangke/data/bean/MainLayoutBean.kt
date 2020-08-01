package cn.kaicity.app.iguangke.data.bean

data class MainLayoutBean(
    val fragmentId: Int,
    val title: String,
    val subTitle: String,
    val image: Int,
    val isBig: Boolean,
    val needLogin:Boolean,
    val color: Int
)