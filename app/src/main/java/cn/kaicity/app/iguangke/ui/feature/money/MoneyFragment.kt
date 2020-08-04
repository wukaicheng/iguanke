package cn.kaicity.app.iguangke.ui.feature.money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.databinding.FragmentMoneyBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.feature.FeatureViewModel
import cn.kaicity.app.iguangke.ui.other.ChildFragment
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.showSnack
import com.github.nukc.stateview.StateView

class MoneyFragment : ChildFragment() {

    private lateinit var mFeatureViewModel: FeatureViewModel

    private lateinit var viewBinding: FragmentMoneyBinding

    private lateinit var mAdapter: MoneyAdapter

    private var pageNo = 0

    private var isLoadMore = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMoneyBinding.inflate(inflater)
        pageNo = 0
        isLoadMore = false
        mFeatureViewModel = ViewModelProvider(
            getMainActivity(),
            InjectorUtil.getFeatureFactory()
        ).get(FeatureViewModel::class.java)
        initRecycler()
        return viewBinding.root
    }

    private fun initRecycler() {
        mAdapter = MoneyAdapter()
        viewBinding.recycler.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recycler.adapter = mAdapter
        viewBinding.refreshLayout.setEnableRefresh(false)

        viewBinding.refreshLayout.setEnableAutoLoadMore(false)
        //接口目前不支持加载更多

        viewBinding.refreshLayout.setOnLoadMoreListener {
            isLoadMore = true
            pageNo++
            mFeatureViewModel.getMoney(pageNo)
        }
        viewBinding.stateView.showLoading()
        mFeatureViewModel.getMoney(pageNo)

        viewBinding.stateView.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                mFeatureViewModel.getMoney(pageNo)
            }
        }
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.root)
    }

    override fun observeLiveData() {

        mFeatureViewModel.mMoneyLiveData.observe(this, Observer {

            when (it.status) {
                StateBean.EMPTY -> {
                    if (isLoadMore) {
                        viewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        viewBinding.stateView.showEmpty()
                    }

                }

                StateBean.FAIL -> {
                    if (isLoadMore) {
                        viewBinding.refreshLayout.finishLoadMore()
                        showSnack("加载下一页失败")
                    } else {
                        viewBinding.stateView.showRetry()
                    }

                }

                StateBean.SUCCESS -> {
                    it.bean?.run {
                        if (isLoadMore) {
                            mAdapter.addData(this)
                            viewBinding.refreshLayout.finishLoadMore()
                            if (size < 20) {
                                viewBinding.refreshLayout.setNoMoreData(true)
                            }

                        } else {
                            viewBinding.stateView.showContent()
                            mAdapter.replaceData(it.bean)
                            viewBinding.balance.text = get(0).balance
                            viewBinding.lastDay.text = get(0).deadline

                        }
                    }
                }

            }
        })

    }
}