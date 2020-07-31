package cn.kaicity.app.iguangke.ui.feature

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.ScoreShowBean
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.repository.FeatureRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel
import cn.kaicity.app.iguangke.util.LogUtil

class FeatureViewModel(private val mRepo: FeatureRepository) : BaseViewModel() {

    val mScoreLiveData = MutableLiveData<StateBean<ScoreShowBean>>()

    fun getScore(token: String, id: String) {
        launchOnUI {
            mRepo.getScore(mScoreLiveData, token, id)
        }
    }
}