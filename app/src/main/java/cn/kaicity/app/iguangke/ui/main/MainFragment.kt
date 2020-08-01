package cn.kaicity.app.iguangke.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.databinding.FragmentMainBinding
import cn.kaicity.app.iguangke.databinding.ItemMainBigBinding
import cn.kaicity.app.iguangke.databinding.ItemMainBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment
import cn.kaicity.app.iguangke.ui.user.UserViewModel
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.iguangke.util.MultiUtil
import cn.kaicity.app.iguangke.util.showSnack


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

    override fun onStart() {
        super.onStart()
    }

    private fun initUserHeader() {
        viewBinding.header.headerTitle.setText(R.string.app_name)
        setUserHeader(
            viewBinding.userHeader,
            getString(R.string.no_login),
            getString(R.string.app_name),
            R.drawable.head_img1
        )
        viewBinding.userHeader.root.setOnClickListener {
            when (userState) {
                StateBean.FAIL -> {
                    startFragmentWithData(
                        viewBinding.userHeader.userName, "登录账号",
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
    }

    private fun updateUserHeader(stateBean: StateBean<UserBean>) {
        when (stateBean.status) {
            StateBean.SUCCESS -> {
                userState = StateBean.SUCCESS
                stateBean.bean?.also {
                    setUserHeader(viewBinding.userHeader, it.nickName, it.className, it.headImage)

                    val mUserViewModel =
                        ViewModelProvider(getMainActivity(), InjectorUtil.getUserFactory()).get(
                            UserViewModel::class.java
                        )
                    mUserViewModel.mUserLiveData.postValue(it)
                }

            }


            StateBean.TIMEOUT -> {
                userState = StateBean.FAIL
                showSnack("登录状态过期，请重新登录")
                setUserHeader(
                    viewBinding.userHeader,
                    getString(R.string.no_login),
                    getString(R.string.app_name),
                    R.drawable.head_img1
                )
            }

            StateBean.FAIL -> {
                userState = StateBean.FAIL
                setUserHeader(
                    viewBinding.userHeader,
                    getString(R.string.no_login),
                    getString(R.string.app_name),
                    R.drawable.head_img1
                )
            }
        }
    }

    override fun observeLiveData() {
        viewModel.mUserLiveData.observe(viewLifecycleOwner, Observer {
            updateUserHeader(it)
        })
    }

    private fun initRecyclerView() {

        val layoutManager = GridLayoutManager(requireContext(), 2)
        mAdapter = MultiAdapter(MultiUtil.mMultiDataList)
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

            if (userState != StateBean.SUCCESS && data.needLogin) {
                showSnackWithLogin()
                return@setOnItemClick
            }
            var itemText: TextView? = null


            //case
            if (mAdapter.getItemViewType(position) == MultiAdapter.ONE_ITEM) {
                (binding as ItemMainBigBinding)
                itemText = binding.itemTitle

            } else if (mAdapter.getItemViewType(position) == MultiAdapter.TWO_ITEM) {
                (binding as ItemMainBinding)
                itemText = binding.itemTitle
            }
            startFragmentWithData(itemText!!, data.title, data.fragmentId)
        }

    }

    private fun showSnackWithLogin() {
        showSnack("请先登录", "登录") {
            startFragmentWithData(
                viewBinding.userHeader.userName, "登录账号",
                R.id.action_mainFragment_to_loginFragment
            )
        }
    }

    private fun startFragmentWithData(
        itemText: TextView,
        title: String,
        id: Int
    ) {
        itemText.transitionName = title
        val extras = FragmentNavigatorExtras(itemText to title)
        val bundle = bundleOf(Pair("title", title))
        findNavController().navigate(id, bundle, null, extras)

    }
}