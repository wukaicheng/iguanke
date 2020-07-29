package cn.kaicity.app.iguangke.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.data.repository.LoginRepository

@Suppress("UNCHECKED_CAST")
class LoginFactory(private val mRepo:LoginRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(mRepo) as T
    }
}