package cn.kaicity.app.iguangke.ui.feature.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.data.repository.CourseRepository

class CourseFactory(private val mRepo:CourseRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return CourseViewModel(mRepo) as T
    }
}