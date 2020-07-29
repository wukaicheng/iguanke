package cn.kaicity.app.iguangke.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.LoginBean
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.databinding.FragmentMainBinding
import cn.kaicity.app.iguangke.databinding.LayoutMainBigitemBinding
import cn.kaicity.app.iguangke.databinding.LayoutMainItemBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.showSnack
import com.bumptech.glide.Glide
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer


class MainFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var mAdapter: MultiAdapter

    private var userState = StateBean.FAIL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentMainBinding.inflate(layoutInflater)
        initRecyclerView()
        initUserHeader()
        viewModel.getUserBean()
        return viewBinding.root
    }

    private fun initUserHeader() {
        viewBinding.header.headerTitle.setText(R.string.app_name)
        showErrorUserHeader()
        viewBinding.userHeader.root.setOnClickListener {
            when (userState) {
                StateBean.FAIL -> {
                    startFragmentWithData(
                        viewBinding.transformationLayout, viewBinding.userHeader.userName, "登录账号",
                        R.id.action_mainFragment_to_loginFragment
                    )
                }

                StateBean.SUCCESS -> {
                    showSnack("success")
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, InjectorUtil.getMainFactory()).get(MainViewModel::class.java)
        onTransformationStartContainer()
    }

    private fun updateUserHeader(stateBean: StateBean<UserBean>) {
        when (stateBean.status) {
            StateBean.SUCCESS -> {
                userState = StateBean.SUCCESS
                stateBean.bean?.also {
                    viewBinding.userHeader.userName.text = it.name
                    viewBinding.userHeader.userClass.text = it.className
                    Glide.with(this).load(it.headImage).into(viewBinding.userHeader.userIcon)
                }

            }

            StateBean.TIMEOUT -> {
                userState = StateBean.FAIL
                showSnack("登录状态过期，请重新登录")
                showErrorUserHeader()
            }

            StateBean.FAIL -> {
                userState = StateBean.FAIL
                showErrorUserHeader()
            }
        }
    }

    private fun showErrorUserHeader() {
        viewBinding.userHeader.userName.setText(R.string.no_login)
        viewBinding.userHeader.userClass.setText(R.string.app_name)
        viewBinding.userHeader.userIcon.setImageResource(R.drawable.head_img1)
    }

    override fun observeLiveData() {
        viewModel.mUserLiveData.observe(viewLifecycleOwner, Observer {
            updateUserHeader(it)
        })
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