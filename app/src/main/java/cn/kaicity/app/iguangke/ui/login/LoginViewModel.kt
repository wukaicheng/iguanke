package cn.kaicity.app.iguangke.ui.login

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.repository.LoginRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel

class LoginViewModel(private val mRepo: LoginRepository) : BaseViewModel() {

    val mLoginLiveData = MutableLiveData<StateBean<UserBean>>()

    fun login(account: String, password: String) {
        launchOnUI {
            mLoginLiveData.postValue(StateBean(StateBean.LOADING))
            mRepo.login(account, password, mLoginLiveData)
        }

    }


    fun saveUserBean(userBean: UserBean){
        mRepo.save(userBean)
    }
}