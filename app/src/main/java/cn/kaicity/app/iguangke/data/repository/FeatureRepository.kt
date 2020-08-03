package cn.kaicity.app.iguangke.data.repository

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.*
import cn.kaicity.app.iguangke.data.network.api.FeatureApi
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.iguangke.util.TimeUtil
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class FeatureRepository(private val api: FeatureApi) {

    companion object{

        val html="""
            <html>
                <head>
                </head>
                <body>
                <center> <span style="font-size: 25px;">%s</span></center>
                <div align="right">
                %s
                </div>
                %s
                </body>
            </html>
            
        """.trimIndent()
    }

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
            scoreList.addAll(it.scores)
        }


        return ScoreWithTerm("$year${termMap[term]}", scoreList)
    }

    suspend fun getMoney(
        mMoneyLiveData: MutableLiveData<StateBean<List<MoneyItem>>>,
        pageNum: Int
    ) {

        try {
            val bean = api.getMoney(pageNum)
            if (bean.result != "1" || bean.items.isEmpty()) {
                mMoneyLiveData.postValue(StateBean(StateBean.EMPTY))
            }
            mMoneyLiveData.postValue(StateBean(StateBean.SUCCESS, bean = bean.items))

        } catch (e: Exception) {
            LogUtil.log(e)
            e.printStackTrace()
            mMoneyLiveData.postValue(StateBean(StateBean.FAIL, msg = "${e.message}"))
        }

    }

    suspend fun getNews(mNewsLiveData: MutableLiveData<StateBean<NewsListBean>>, pageNo: Int) {
        try {

            val bean = api.getNews(pageNo)
            if (bean.result != "1" || bean.items.isEmpty()) {
                mNewsLiveData.postValue(StateBean(StateBean.EMPTY))
            }

            mNewsLiveData.postValue(StateBean(StateBean.SUCCESS, bean = bean))

        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.log(e)
            mNewsLiveData.postValue(StateBean(StateBean.FAIL, msg = "${e.message}"))
        }

    }

    suspend fun getNewsDetail(mNewsDetailLiveData: MutableLiveData<StateBean<NewDetailItem>>, id: Int) {

        try {

            val bean = api.getNewsDetail(id)
            if (bean.result != "1" && bean.item.content.isEmpty()) {
                mNewsDetailLiveData.postValue(StateBean(StateBean.EMPTY))
            }
            bean.item.content=createHtml(bean.item.content,bean.item.name,bean.item.createDate)
            mNewsDetailLiveData.postValue(StateBean(StateBean.SUCCESS, bean=bean.item))
        } catch (e: Exception) {
            e.printStackTrace()
            mNewsDetailLiveData.postValue(StateBean(StateBean.FAIL, msg = "${e.message}"))
        }
    }

    private fun createHtml(content: String, name: String, createDate: Long): String {
        val newStr=String.format(html,name,TimeUtil.longToStrWithTime(createDate),content)
        return getNewData(newStr)
    }

    private fun getNewData(data: String): String {
        val document: Document = Jsoup.parse(data)
        val pElements: Elements = document.select("p:has(img)")
        for (pElement in pElements) {
            pElement.attr("style", "text-align:center")
            pElement.attr("max-width","100%")
                .attr("height", "auto")
        }
        val imgElements: Elements = document.select("img")
        for (imgElement in imgElements) {
            //重新设置宽高
            imgElement.attr("max-width", "100%")
                .attr("height", "auto")
            imgElement.attr("style", "max-width:100%;height:auto")
        }
        return document.toString()
    }
}