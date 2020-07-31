package cn.kaicity.app.iguangke.ui.feature.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.databinding.FragmentScoreBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.feature.FeatureViewModel
import cn.kaicity.app.iguangke.ui.other.ChildFragment
import cn.kaicity.app.iguangke.ui.user.UserViewModel
import cn.kaicity.app.iguangke.util.InjectorUtil
import com.github.nukc.stateview.StateView

class ScoreFragment : ChildFragment() {

    private lateinit var viewBinding: FragmentScoreBinding

    private lateinit var mUserViewModel: UserViewModel

    private lateinit var mFeatureViewModel: FeatureViewModel

    private lateinit var mAdapter: ScoreAdapter

    private lateinit var userBean: UserBean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScoreBinding.inflate(inflater)
        mUserViewModel = ViewModelProvider(getMainActivity()).get(UserViewModel::class.java)
        mFeatureViewModel =
            ViewModelProvider(getMainActivity(), InjectorUtil.getFeatureFactory()).get(
                FeatureViewModel::class.java
            )
        initRecyclerView()
        return viewBinding.root
    }

    private fun initRecyclerView() {
        viewBinding.stateView.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                getScore()
            }
        }

        mAdapter = ScoreAdapter()
        viewBinding.recycler.adapter = mAdapter
        viewBinding.recycler.layoutManager = LinearLayoutManager(requireContext())

    }


    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.header.root)
    }

    override fun observeLiveData() {

        mUserViewModel.mUserLiveData.observe(this, Observer {
            userBean = it
            setUserHeader(viewBinding.userHeader, it.name, it.xh, it.headImage)
            viewBinding.userHeader.endImg.visibility = View.GONE
            getScore()
        })


        mFeatureViewModel.mScoreLiveData.observe(this, Observer {

            when (it.status) {
                StateBean.EMPTY -> viewBinding.stateView.showEmpty()

                StateBean.FAIL -> viewBinding.stateView.showRetry()

                StateBean.SUCCESS -> {
                    viewBinding.stateView.showContent()
                    mAdapter.replaceData(it.bean!!.list)
                    viewBinding.scoreText.text = it.bean.termName
                }

            }
        })
    }

    private fun getScore() {
        viewBinding.recycler.visibility = View.VISIBLE
        viewBinding.stateView.showLoading()
        mFeatureViewModel.getScore(userBean.token, userBean.userId)
    }
}