package cn.kaicity.app.iguangke.data.repository

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.*
import cn.kaicity.app.iguangke.data.network.api.FeatureApi

class FeatureRepository(private val api: FeatureApi) {

    suspend fun getScore(mScoreLiveData: MutableLiveData<StateBean<ScoreWithTerm>>) {
        try {
            val scoreBean = api.getScore()
            val bean = decodeScore(scoreBean)
            if (bean == null) {
                mScoreLiveData.postValue(StateBean(StateBean.EMPTY))
            }
            mScoreLiveData.postValue(StateBean(StateBean.SUCCESS, bean = bean))
        } catch (e: Exception) {
            mScoreLiveData.postValue(StateBean(StateBean.FAIL))
        }


    }


    private fun decodeScore(scoreBean: ScoreBean): ScoreWithTerm? {

        if (scoreBean.result != "1") {
            return null
        }

        val termMap = mapOf(Pair(0, "上学期"), Pair(1, "下学期"))
        val year = scoreBean.years[0]
        val term = scoreBean.scoreList.lastIndex

        val scoreList = mutableListOf<ScoreX>()
        scoreBean.scoreList.filter {
            it.term == term
        }.forEach {
            it.scores.forEach {
                scoreList.add(it)
            }
        }

        return ScoreWithTerm("$year${termMap[term]}", scoreList)
    }

    suspend fun getMoney(mMoneyLiveData: MutableLiveData<StateBean<List<MoneyItem>>>,pageNum:Int) {
        val bean=api.getMoney(pageNum)
        if(bean.result!="1"||bean.items.isEmpty()){
            mMoneyLiveData.postValue(StateBean(StateBean.EMPTY))
        }

        mMoneyLiveData.postValue(StateBean(StateBean.SUCCESS,bean = bean.items))
    }

}