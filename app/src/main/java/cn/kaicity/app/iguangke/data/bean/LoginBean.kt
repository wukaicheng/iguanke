package cn.kaicity.app.iguangke.data.bean

data class LoginBean(
    val item: LoginItem,
    val result: String
)

data class LoginItem(
    val headImage: String,
    val nickName: String,
    val xm:String,
    val phone: String,
    val xh: String,
    val bjmc: String,
    val yxmc: String,
    val zymc: String,
    val rxnj:String
)