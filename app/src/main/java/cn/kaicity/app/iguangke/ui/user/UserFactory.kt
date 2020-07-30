package cn.kaicity.app.iguangke.ui.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.data.repository.UserRepository

class UserFactory(val mRepo:UserRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(mRepo) as T
    }
}