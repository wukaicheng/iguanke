package cn.kaicity.app.iguangke.ui.user

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.data.repository.UserRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel

class UserViewModel(
    private val mRepo: UserRepository
) : BaseViewModel() {

    val mUserLiveData = MutableLiveData<UserBean>()

    fun save(bean: UserBean) {
        mRepo.saveUser(bean)
    }

}