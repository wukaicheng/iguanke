package cn.kaicity.app.iguangke.ui.feature.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.databinding.FragmentCourseBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.other.ChildFragment
import cn.kaicity.app.iguangke.util.*
import com.github.nukc.stateview.StateView
import java.lang.StringBuilder

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
        viewBinding.stateView.showLoading()
        viewModel.getCourse()
        viewBinding.picker.addOnItemChangedListener { _, adapterPosition ->
            viewBinding.weekNum.text = "第${adapterPosition + 1}周"
        }

        mAdapter.setOnStickerSelectEventListener {
            val sb = StringBuilder()
                .append("课程名称：")
                .append(it.classTitle)
                .append("\n")
                .append("教师名称：")
                .append(it.professorName)
                .append("\n")
                .append("上课地点：")
                .append(it.classPlace)
            showMessageDialog("课程详情", sb.toString())
        }


        viewBinding.stateView.onRetryClickListener=object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                viewModel.getCourse()
            }
        }
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.root)
    }

    override fun observeLiveData() {

        viewModel.mCourseLiveData.observe(this, Observer {

            when (it.status) {
                StateBean.EMPTY -> viewBinding.stateView.showEmpty()

                StateBean.FAIL -> {
                    showSnack(it.msg)
                    viewBinding.stateView.showRetry()
                }

                StateBean.SUCCESS -> {
                    mAdapter.replaceData(it.bean!!)
                    viewBinding.picker.scrollToPosition(TimeUtil.getWeek())
                    viewBinding.stateView.showContent()
                }

            }
        })
    }


}