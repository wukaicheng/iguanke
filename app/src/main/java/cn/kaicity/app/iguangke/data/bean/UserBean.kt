package cn.kaicity.app.iguangke.data.bean


/**
 *
 * @property name String 姓名
 * @property token String token
 * @property userId String userd
 * @property className String 班级名称
 * @property headImage String 头像链接
 * @property readIngYear Int 入学年份
 * @property disciplineName String 专业名称
 * @property collegeName String 学院名称
 * @property nickName String 昵称
 * @property studentID String 学号
 * @constructor
 */

data class UserBean(
    val name: String,
    val token: String,
    val userId: String,
    val className: String,
    val headImage: String,
    val readIngYear: String,
    val disciplineName: String,
    val collegeName: String,
    val nickName:String,
    val studentID: String
)