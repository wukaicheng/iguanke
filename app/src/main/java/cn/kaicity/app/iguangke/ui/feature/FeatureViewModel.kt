package cn.kaicity.app.iguangke.ui.feature

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.*
import cn.kaicity.app.iguangke.data.repository.FeatureRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel

class FeatureViewModel(private val mRepo: FeatureRepository) : BaseViewModel() {

    val mScoreLiveData = MutableLiveData<StateBean<ScoreWithTerm>>()

    val mMoneyLiveData = MutableLiveData<StateBean<List<MoneyItem>>>()



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


}