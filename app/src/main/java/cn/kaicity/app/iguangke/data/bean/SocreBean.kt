package cn.kaicity.app.iguangke.data.bean

data class ScoreBean(
    val result: String,
    val scoreList: List<Score>,
    val years: List<String>
)

data class Score(
    val scores: List<ScoreX>,
    val term: Int
)

data class ScoreX(
    val course: String,
    val credit: String,
    val no: String,
    val point: String,
    val score: String,
    val term: String,
    val year: String
)

data class ScoreWithTerm(val year: String, val score: List<ScoreX>)
