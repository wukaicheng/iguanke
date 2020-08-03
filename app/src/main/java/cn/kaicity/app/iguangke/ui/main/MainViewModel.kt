package cn.kaicity.app.iguangke.ui.main

import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.bean.VersionBean
import cn.kaicity.app.iguangke.data.repository.UserRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

class MainViewModel(private val mRepo: UserRepository) : BaseViewModel() {

    val mUserLiveData = UnPeekLiveData<StateBean<UserBean>>()

    val mUpdateLiveData = UnPeekLiveData<VersionBean>()

    fun getUserBean() {

        launchOnUI {
            mRepo.getUserBean(mUserLiveData)
        }
    }

    fun checkUpdate(){

        launchOnUI {
            mRepo.checkUpdate(mUpdateLiveData)
        }
    }
}