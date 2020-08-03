package cn.kaicity.app.iguangke.data.repository

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.CourseBean
import cn.kaicity.app.iguangke.data.bean.CourseItem
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.network.api.FeatureApi
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.timetable.Schedule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CourseRepository(private val api: FeatureApi) {

    private val mShared = App.context.getSharedPreferences(KEYS.COURSE, Context.MODE_PRIVATE)

    private val mEdit = mShared.edit()

    suspend fun getCourse(
        mCourseLiveData: MutableLiveData<StateBean<List<ArrayList<Schedule>>>>
    ) {
        val startTime = getStartTime()
        if (startTime == null) {
            mCourseLiveData.postValue(StateBean(StateBean.FAIL, "初始化失败"))
            return
        }

        val list = arrayListOf<ArrayList<Schedule>>()

        //每学期20周
        for (week in 1..20) {

            var bean = getCourseByCache(week)
            if (bean == null) {
                bean = getCourseByNetWork(week)
            }

            if (bean != null) {
                val newBean = decodeBean(bean)
                list.add(newBean)
            }
        }

        if (list.size < 20) {
            mCourseLiveData.postValue(StateBean(StateBean.FAIL, "获取课表失败，请重试"))
        }

        //延迟半秒，等界面加载完毕
        Thread.sleep(500)
        mCourseLiveData.postValue(StateBean(StateBean.SUCCESS, bean = list))

    }


    private fun getCourseByCache(week: Int): CourseBean? {

        val courseData = mShared.getString(KEYS.WEEK + week, null) ?: return null
        val type = object : TypeToken<CourseBean>() {}.type
        return Gson().fromJson<CourseBean>(courseData, type)
    }

    private suspend fun getCourseByNetWork(week: Int): CourseBean? {
        return try {
            val bean = api.getCourse(week)
            mEdit.putString(KEYS.WEEK + week, Gson().toJson(bean)).apply()
            bean

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun decodeBean(bean: CourseBean): ArrayList<Schedule> {
        if (bean.result != "1") return arrayListOf()
        val list = arrayListOf<Schedule>()
        list.addAll(decodeItemBean(bean.items.one))
        list.addAll(decodeItemBean(bean.items.two))
        list.addAll(decodeItemBean(bean.items.three))
        list.addAll(decodeItemBean(bean.items.four))
        list.addAll(decodeItemBean(bean.items.five))
        return list
    }

    private fun decodeItemBean(item: List<CourseItem>): ArrayList<Schedule> {

        val list = arrayListOf<Schedule>()
        for (course in item) {
            val classArray = course.djj.split(",")
            list.add(
                Schedule(
                    classTitle = course.kcmc,
                    classPlace = course.jsmc,
                    day = course.xqj.toInt() - 1,
                    professorName = course.teacher_xm,
                    startClass = classArray.first().toInt() - 1,
                    endClass = classArray.last().toInt() - 1
                )
            )

        }

        return list
    }

    private suspend fun getStartTime(): String? {
        val startTime = mShared.getString(KEYS.START_TIME, null)
        return try {
            val newStartTime = api.getSetting().config.start_time
            mEdit.putString(KEYS.START_TIME, newStartTime).apply()
            newStartTime
        } catch (e: Exception) {
            e.printStackTrace()
            startTime
        }
    }


}