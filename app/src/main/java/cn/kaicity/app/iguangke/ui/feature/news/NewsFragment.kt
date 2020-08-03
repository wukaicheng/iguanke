package cn.kaicity.app.iguangke.ui.feature.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.NewsItem
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.databinding.FragmentNewslistBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.other.ChildFragment
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.iguangke.util.showSnack
import com.github.nukc.stateview.StateView

class NewsFragment : ChildFragment() {

    private lateinit var viewBinding: FragmentNewslistBinding

    private lateinit var viewModel: NewsViewModel

    private lateinit var mAdapter: NewsListAdapter

    private var pageNo = 1

    private var isLoadMore = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewslistBinding.inflate(inflater)
        viewModel = ViewModelProvider(getMainActivity(), InjectorUtil.getNewsRFactory()).get(
            NewsViewModel::class.java
        )
        initRecycler()
        return viewBinding.root
    }

    private fun initRecycler() {
        mAdapter = NewsListAdapter()
        viewBinding.recycler.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recycler.adapter = mAdapter

        viewBinding.refreshLayout.setEnableRefresh(false)
        viewBinding.refreshLayout.setOnLoadMoreListener {
            isLoadMore = true
            pageNo++
            viewModel.getNews(pageNo)
        }

        mAdapter.setOnItemClick { position, binding, data ->

            binding.imageView.transitionName = KEYS.SHARE_IMAGE + position
            val url = data.logos.first()
            val extras = FragmentNavigatorExtras(
                binding.imageView to url
            )

            val bundle = bundleOf(KEYS.URL to url, KEYS.TITLE to data.name, KEYS.ID to data.id)
            findNavController().navigate(
                R.id.action_newsFragment_to_newDetailFragment,
                bundle,
                null,
                extras
            )
        }
        viewBinding.stateView.showLoading()
        viewModel.getNews(pageNo)

        viewBinding.stateView.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                viewModel.getNews(pageNo)
            }
        }
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
                            viewModel.mNewsListLiveData.value?.addAll(this.items)
                            mAdapter.addData(this.items)
                            viewBinding.refreshLayout.finishLoadMore()
                            if (this.items.size < 10) {
                                viewBinding.refreshLayout.setNoMoreData(true)
                            }

                        } else {

                            if (viewModel.mNewsListLiveData.value == null) {
                                viewBinding.stateView.showContent()
                                val list = ArrayList<NewsItem>()
                                list.addAll(this.items)
                                viewModel.mNewsListLiveData.value = list
                            }

                        }
                    }
                }
            }
        })

        viewModel.mNewsListLiveData.observe(this, Observer {
            mAdapter.replaceData(it)
        })
    }
}