package cn.kaicity.app.iguangke.ui.feature.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.databinding.FragmentNewslistBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.feature.FeatureViewModel
import cn.kaicity.app.iguangke.ui.other.ChildFragment
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.showSnack

class NewsFragment : ChildFragment() {

    private lateinit var viewBinding: FragmentNewslistBinding

    private lateinit var viewModel: FeatureViewModel

    private lateinit var mAdapter: NewsListAdapter

    private var pageNo = 0

    private var isLoadMore = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewslistBinding.inflate(inflater)
        viewModel = ViewModelProvider(getMainActivity(), InjectorUtil.getFeatureFactory()).get(
            FeatureViewModel::class.java
        )
        initRecycler()
        return viewBinding.root
    }

    private fun initRecycler() {
        mAdapter= NewsListAdapter()
        viewBinding.recycler.layoutManager=LinearLayoutManager(requireContext())
        viewBinding.recycler.adapter=mAdapter

        viewBinding.refreshLayout.setEnableRefresh(false)
        viewBinding.refreshLayout.setOnLoadMoreListener {
            viewModel.getNews(pageNo)
        }

        viewModel.getNews(pageNo)
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return viewBinding.header
    }

    override fun observeLiveData() {
        viewModel.mNewsLiveData.observe(this, Observer {

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
                            mAdapter.addData(this.items)
                            viewBinding.refreshLayout.finishLoadMore()
                            if (this.items.size < 10) {
                                viewBinding.refreshLayout.setNoMoreData(true)
                            }

                        } else {
                            viewBinding.stateView.showContent()
                            mAdapter.replaceData(this.items)

                        }
                    }
                }

            }
        })
    }
}