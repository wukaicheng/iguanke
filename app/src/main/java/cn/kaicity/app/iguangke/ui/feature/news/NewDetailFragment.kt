package cn.kaicity.app.iguangke.ui.feature.news

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import androidx.transition.TransitionInflater
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.databinding.FragmentNewsDetailBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment
import cn.kaicity.app.iguangke.ui.feature.FeatureViewModel
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.LogUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout


class NewDetailFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentNewsDetailBinding

    private lateinit var viewModel: FeatureViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsDetailBinding.inflate(inflater)
        viewModel = ViewModelProvider(getMainActivity(), InjectorUtil.getFeatureFactory()).get(
            FeatureViewModel::class.java
        )
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(KEYS.URL)
        initImage(url)
        initToolbar()
        val id=arguments?.getInt(KEYS.ID)
        id?.run {

            viewModel.getNewsDetail(id)
        }
    }

    private fun initImage(url: String?) {
        viewBinding.background.transitionName = url
        val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_background)
            .onlyRetrieveFromCache(true)
            .dontTransform()

        Glide.with(this).load(url).apply(requestOptions).into(viewBinding.background)
    }

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

        viewBinding.webView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            viewBinding.appBarLayout.setExpanded(scrollX<oldScrollX,true)
        }
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
                    viewBinding.stateView.showContent()
                    viewBinding.webView.loadData(it.bean?.content,"text/html", "UTF-8")
                    LogUtil.log(it.bean)
                }

            }
        })
    }


}