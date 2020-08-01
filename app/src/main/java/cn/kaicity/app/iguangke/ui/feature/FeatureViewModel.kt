package cn.kaicity.app.iguangke.ui.feature

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.*
import cn.kaicity.app.iguangke.data.repository.FeatureRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel

class FeatureViewModel(private val mRepo: FeatureRepository) : BaseViewModel() {

    val mScoreLiveData = MutableLiveData<StateBean<ScoreWithTerm>>()

    val mMoneyLiveData = MutableLiveData<StateBean<List<MoneyItem>>>()

    val mNewsLiveData  =MutableLiveData<StateBean<NewsListBean>>()

    val mNewsDetailLiveData = MutableLiveData<StateBean<NewDetailItem>>()

    fun getScore() {
        launchOnUI {
            mRepo.getScore(mScoreLiveData)
        }
    }

    fun getMoney(pageNo: Int) {

        launchOnUI {
            mRepo.getMoney(mMoneyLiveData, pageNo)
        }
    }

    fun getNews(pageNo: Int){
        launchOnUI {
            mRepo.getNews(mNewsLiveData,pageNo)
        }
    }

    fun getNewsDetail(id:Int){
        launchOnUI {
         mRepo.getNewsDetail(mNewsDetailLiveData,id)
        }
    }
}