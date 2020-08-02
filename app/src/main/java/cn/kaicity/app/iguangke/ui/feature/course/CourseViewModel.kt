package cn.kaicity.app.iguangke.ui.feature.course

import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.data.bean.CourseBean
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.repository.CourseRepository
import cn.kaicity.app.iguangke.ui.base.BaseViewModel
import cn.kaicity.app.timetable.Schedule

class CourseViewModel(val mRepo: CourseRepository) : BaseViewModel() {

    val mCourseLiveData = MutableLiveData<StateBean<List<ArrayList<Schedule>>>>()

    fun getCourse() {
        launchOnUI {
            mRepo.getCourse(mCourseLiveData)
        }
    }


}