package cn.kaicity.app.iguangke.ui.feature.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.databinding.FragmentCourseBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.other.ChildFragment
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.iguangke.util.TimeUtil
import cn.kaicity.app.iguangke.util.showSnack

class CourseFragment : ChildFragment() {

    private lateinit var viewBinding: FragmentCourseBinding

    private lateinit var mAdapter: CourseAdapter

    private lateinit var viewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCourseBinding.inflate(inflater)
        viewModel = ViewModelProvider(
            this,
            InjectorUtil.getCourseFactory()
        ).get(CourseViewModel::class.java)
        initRecycler()
        return viewBinding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initRecycler() {
        mAdapter = CourseAdapter()
        viewBinding.picker.adapter = mAdapter
        viewModel.getCourse()
        viewBinding.picker.addOnItemChangedListener { _, adapterPosition ->
            viewBinding.weekNum.text="第${adapterPosition+1}周"
        }
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.root)
    }

    override fun observeLiveData() {

        viewModel.mCourseLiveData.observe(viewLifecycleOwner, Observer {

            it.bean?.let { it1 ->
                mAdapter.addData(it1)
                viewBinding.picker.scrollToPosition(TimeUtil.getWeek())
            }
        })
    }



}