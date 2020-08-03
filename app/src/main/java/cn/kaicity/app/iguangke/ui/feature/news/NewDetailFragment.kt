package cn.kaicity.app.iguangke.ui.feature.news

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.databinding.FragmentNewsDetailBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment
import cn.kaicity.app.iguangke.ui.feature.FeatureViewModel
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.iguangke.util.TimeUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.nukc.stateview.StateView
import com.google.android.material.appbar.AppBarLayout
import java.io.File


class NewDetailFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentNewsDetailBinding

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsDetailBinding.inflate(inflater)
        viewModel = ViewModelProvider(getMainActivity(), InjectorUtil.getNewsRFactory()).get(
            NewsViewModel::class.java
        )
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(KEYS.URL)
        initImage(url)
        initToolbar()
        val id = arguments?.getInt(KEYS.ID)
        id?.run {
            viewBinding.stateView.showLoading()
            viewModel.getNewsDetail(id)

            viewBinding.stateView.onRetryClickListener=object : StateView.OnRetryClickListener {
                override fun onRetryClick() {
                    viewModel.getNewsDetail(id)
                }
            }

        }


    }

    private fun initImage(url: String?) {
        viewBinding.background.transitionName = url
        val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_background)
            .onlyRetrieveFromCache(true)
            .dontTransform()

        Glide.with(this).load(url).apply(requestOptions).into(viewBinding.background)
    }

    // TODO: 2020/8/1 待优化
    private fun initToolbar() {
        getMainActivity().setSupportActionBar(viewBinding.toolbar)
        setHasOptionsMenu(true)
        getMainActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getMainActivity().setStatusBarColor(Color.TRANSPARENT)
        viewBinding.appBarLayout.setBackgroundColor(Color.WHITE)
        viewBinding.toolbarLayout.setCollapsedTitleTextColor(requireContext().getColor(R.color.primary_text))
        viewBinding.toolbarLayout.setExpandedTitleColor(Color.WHITE)

        viewBinding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset: Int ->

            val color = if (offset < -200) {
                getMainActivity().setStatusBarColor(Color.WHITE)
                requireActivity().getColor(R.color.primary_text)
            } else {
                getMainActivity().setStatusBarColor(Color.TRANSPARENT)
                Color.WHITE
            }

            viewBinding.toolbarLayout.setExpandedTitleColor(color)
            viewBinding.toolbar.navigationIcon?.colorFilter =
                PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        getMainActivity().setStatusBarColor(Color.WHITE)
    }

    override fun observeLiveData() {

        viewModel.mNewsDetailLiveData.observe(this, Observer {

            when (it.status) {
                StateBean.EMPTY -> viewBinding.stateView.showEmpty()

                StateBean.FAIL -> viewBinding.stateView.showRetry()

                StateBean.SUCCESS -> {
                    it.bean?.run {
                        viewBinding.stateView.showContent()
                        viewBinding.webView.loadDataWithBaseURL(
                            "",
                            content,
                            "text/html",
                            "UTF-8",
                            null
                        )
                    }

                }

            }
        })
    }

}