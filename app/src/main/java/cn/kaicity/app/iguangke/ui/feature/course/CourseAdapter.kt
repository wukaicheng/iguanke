package cn.kaicity.app.iguangke.ui.feature.course

import android.view.ViewGroup
import cn.kaicity.app.iguangke.databinding.ItemCourseBinding
import cn.kaicity.app.iguangke.ui.base.BaseAdapter
import cn.kaicity.app.timetable.Schedule

class CourseAdapter : BaseAdapter<ItemCourseBinding, ArrayList<Schedule>>() {

    private var listener : ((schedules: Schedule) -> Unit)? =null

    override fun getViewBinding(parent: ViewGroup): ItemCourseBinding {
        return ItemCourseBinding.inflate(getInflater(parent), parent, false)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun initView(binding: ItemCourseBinding, position: Int, data: ArrayList<Schedule>) {
        binding.timetable.add(data)
        if(listener!=null){
            binding.timetable.setOnStickerSelectEventListener(listener!!)
        }


    }


    fun setOnStickerSelectEventListener(block: (schedules: Schedule) -> Unit) {
        this.listener=block
    }

}