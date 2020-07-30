package cn.kaicity.app.iguangke.ui.main

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.repository.UserRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel

class MainViewModel(private val mRepo: UserRepository) : BaseViewModel() {

    val mUserLiveData = MutableLiveData<StateBean<UserBean>>()

    fun getUserBean() {

        launchOnUI {
            mRepo.getUserBean(mUserLiveData)
        }
    }
}