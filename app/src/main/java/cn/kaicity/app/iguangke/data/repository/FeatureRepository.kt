package cn.kaicity.app.iguangke.data.repository

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.ScoreBean
import cn.kaicity.app.iguangke.data.bean.ScoreShowBean
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.network.api.FeatureApi
import cn.kaicity.app.iguangke.util.LogUtil
import kotlin.concurrent.thread

class FeatureRepository(private val api: FeatureApi) {

    suspend fun getScore(
        mScoreLiveData: MutableLiveData<StateBean<ScoreShowBean>>,
        token: String,
        id: String
    ) {
        try {
            val scoreBean = api.getScore(token, id)
            val bean = decodeScore(scoreBean)
            Thread.sleep(2000)
            if (bean == null) {
                mScoreLiveData.postValue(StateBean(StateBean.EMPTY))
            }
            mScoreLiveData.postValue(StateBean(StateBean.SUCCESS, bean = bean))
        } catch (e: Exception) {
            mScoreLiveData.postValue(StateBean(StateBean.FAIL))
        }


    }


    private fun decodeScore(scoreBean: ScoreBean): ScoreShowBean? {

        if (scoreBean.result != "1") {
            return null
        }

        val termMap = mapOf(Pair(0, "上学期"), Pair(1, "下学期"))
        val year = scoreBean.years[0]
        val term = scoreBean.scoreList.lastIndex

        val scoreList = mutableListOf<ScoreShowBean.ScoreBean>()
        scoreBean.scoreList.filter {
            it.term == term
        }.forEach {
            it.scores.forEach {
                scoreList.add(ScoreShowBean.ScoreBean(it.course, it.score))
            }
        }

        return ScoreShowBean("$year${termMap[term]}", scoreList)
    }

}