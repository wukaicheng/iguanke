package cn.kaicity.app.iguangke.data.bean

data class ScoreShowBean(val termName: String, val list: List<ScoreBean>) {


    data class ScoreBean(val className: String, val score: String)
}
