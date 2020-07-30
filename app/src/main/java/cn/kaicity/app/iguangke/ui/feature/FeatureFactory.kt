package cn.kaicity.app.iguangke.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.data.repository.FeatureRepository

class FeatureFactory(val mRepo:FeatureRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeatureViewModel(mRepo) as T
    }
}