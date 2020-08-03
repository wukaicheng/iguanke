package cn.kaicity.app.iguangke.ui.feature.news

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.NewDetailItem
import cn.kaicity.app.iguangke.data.bean.NewsItem
import cn.kaicity.app.iguangke.data.bean.NewsListBean
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.repository.FeatureRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel

class NewsViewModel(val mRepo:FeatureRepository) : BaseViewModel() {

    val mNewsLiveData  = MutableLiveData<StateBean<NewsListBean>>()

    val mNewsDetailLiveData = MutableLiveData<StateBean<NewDetailItem>>()

    val mNewsListLiveData = MutableLiveData<ArrayList<NewsItem>>()

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