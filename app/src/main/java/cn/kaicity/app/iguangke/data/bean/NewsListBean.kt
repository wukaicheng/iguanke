package cn.kaicity.app.iguangke.data.bean

data class NewsListBean(
    val items: List<NewsItem>,
    val result: String
)

data class NewsItem(
    val createDate: Long,
    val hotUrgent: Int,
    val id: Int,
    val introStyleCode: Int,
    val logos: List<String>,
    val name: String,
    val ownerResource: String,
    val ownerResourceName: String,
    val pageView: Int,
    val styleCode: Int
)