package cn.kaicity.app.iguangke.data.bean

data class NewsBean(
    val item: NewDetailItem,
    val result: String
)

data class NewDetailItem(
    val allowComment: Boolean,
    var content: String,
    val createDate: Long,
    val discussionNum: Int,
    val favoriteStatus: String,
    val id: Int,
    val image: String,
    val images: List<Any>,
    val introStyleCode: Int,
    val isUrgent: Boolean,
    val name: String,
    val styleCode: Int,
    val thumbImage: String
)