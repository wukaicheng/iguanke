package cn.kaicity.app.iguangke.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class MainFactory(private val mRepo:UserRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mRepo) as T
    }
}