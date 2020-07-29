package cn.kaicity.app.iguangke.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.databinding.FragmentMainBinding
import cn.kaicity.app.iguangke.databinding.LayoutMainBigitemBinding
import cn.kaicity.app.iguangke.databinding.LayoutMainItemBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment
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
        initUserHeader()
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onTransformationStartContainer()
    }

    private fun initUserHeader() {
        viewBinding.header.headerTitle.text = getString(R.string.app_name)
        viewBinding.userHeader.root.setOnClickListener {
            startFragmentWithData(
                viewBinding.transformationLayout, viewBinding.userHeader.userName, "登录账号",
                R.id.action_mainFragment_to_loginFragment
            )
        }
    }

    override fun observeLiveData() {

    }

    private fun initRecyclerView() {

        val layoutManager = GridLayoutManager(requireContext(), 2)
        mAdapter = MultiAdapter(Multi.mMultiDataList)
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
            var itemText: TextView? = null


            //case
            if (mAdapter.getItemViewType(position) == MultiAdapter.ONE_ITEM) {
                transformationLayout = (binding as LayoutMainBigitemBinding).transformationLayout
                itemText = binding.itemTitle

            } else if (mAdapter.getItemViewType(position) == MultiAdapter.TWO_ITEM) {
                transformationLayout = (binding as LayoutMainItemBinding).transformationLayout
                itemText = binding.itemTitle
            }

            startFragmentWithData(transformationLayout!!, itemText!!, data.title, data.fragmentId)
        }

    }

    private fun startFragmentWithData(
        transLayout: TransformationLayout,
        itemText: TextView,
        title: String,
        id: Int
    ) {
        transLayout.transitionName = KEYS.SHARE_LAYOUT
        itemText.transitionName = KEYS.SHARE_TITLE

        val bundle = transLayout.getBundle(KEYS.TRANS_PARAMS)
        bundle.putString(KEYS.TITLE, title)
        bundle.putParcelable(KEYS.TRANS_PARAMS, transLayout.getParams())
        val extras = FragmentNavigatorExtras(itemText to KEYS.SHARE_TITLE)
        findNavController().navigate(id, bundle, null, extras)

    }
}