package cn.kaicity.app.iguangke.ui.feature.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.data.repository.FeatureRepository

class NewsFactory(val mRepo:FeatureRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(mRepo) as T
    }
}