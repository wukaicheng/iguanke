package cn.kaicity.app.iguangke.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.bean.MultiLayoutBean
import cn.kaicity.app.iguangke.databinding.FragmentMainBinding
import cn.kaicity.app.iguangke.databinding.LayoutMainBigitemBinding
import cn.kaicity.app.iguangke.databinding.LayoutMainItemBinding
import cn.kaicity.app.iguangke.ui.BaseFragment
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer

class MainFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentMainBinding

    private lateinit var mAdapter: MultiAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentMainBinding.inflate(layoutInflater)
        initRecyclerView()
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onTransformationStartContainer()
    }

    private fun initRecyclerView() {

        val layoutManager = GridLayoutManager(requireContext(), 2)
        mAdapter = MultiAdapter(mMultiDataList)
        viewBinding.recycler.layoutManager = layoutManager
        viewBinding.recycler.adapter = mAdapter

        //动态设置item大小
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mAdapter.getItemViewType(position)) {
                    MultiAdapter.ONE_ITEM -> 2
                    MultiAdapter.TWO_ITEM -> 1
                    else -> 0
                }
            }
        }

        mAdapter.setOnItemClick { position, binding, data ->

            var transformationLayout: TransformationLayout? = null
            var itemText:TextView? = null



            //case
            if (mAdapter.getItemViewType(position) == MultiAdapter.ONE_ITEM) {
                transformationLayout = (binding as LayoutMainBigitemBinding).transformationLayout
                itemText =(binding as LayoutMainBigitemBinding).itemTitle

            } else if (mAdapter.getItemViewType(position) == MultiAdapter.TWO_ITEM) {
                transformationLayout = (binding as LayoutMainItemBinding).transformationLayout
                itemText =(binding as LayoutMainBigitemBinding).itemTitle
            }


            transformationLayout?.run {
                this.transitionName = "transitionNameLayout"
                itemText?.transitionName="transText"

                val bundle = this.getBundle("TransformationParams")
                bundle.putParcelable("TransformationParams", this.getParams())

                val extras = FragmentNavigatorExtras(itemText!! to "transText")
                findNavController().navigate(data.fragmentId, bundle, null, extras)
            }

        }

    }


    private val mMultiDataList: List<MultiLayoutBean> by lazy {
        arrayListOf(
            MultiLayoutBean(
                R.id.action_mainFragment_to_scoreFragment,
                "成绩查询",
                "期末考试成绩",
                R.drawable.ic_score,
                true,
                getColor(R.color.royal_blue)
            ),
            MultiLayoutBean(
                0,
                "课表查看",
                "本学期课程表",
                R.drawable.ic_class,
                false,
                getColor(R.color.roman)
            ),
            MultiLayoutBean(
                0,
                "一卡通查询",
                "余额以及消费记录",
                R.drawable.ic_money,
                false,
                getColor(R.color.caribbean_green)
            )
        )

    }

    private fun Fragment.getColor(id: Int): Int {
        return requireActivity().getColor(id)
    }
}