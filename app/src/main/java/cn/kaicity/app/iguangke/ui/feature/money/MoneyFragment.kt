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
import cn.kaicity.app.iguangke.ui.user.UserViewModel
import cn.kaicity.app.iguangke.util.InjectorUtil

class MoneyFragment : ChildFragment() {

    private lateinit var mFeatureViewModel: FeatureViewModel

    private lateinit var viewBinding: FragmentMoneyBinding

    private lateinit var mAdapter: MoneyAdapter

    private var pageNo=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMoneyBinding.inflate(inflater)

        mFeatureViewModel = ViewModelProvider(
            getMainActivity(),
            InjectorUtil.getFeatureFactory()
        ).get(FeatureViewModel::class.java)
        initRecycler()
        return viewBinding.root
    }

    private fun initRecycler() {
        mAdapter=MoneyAdapter()
        viewBinding.recycler.layoutManager=LinearLayoutManager(requireContext())
        viewBinding.recycler.adapter= mAdapter
        mFeatureViewModel.getMoney(pageNo)
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.root)
    }

    override fun observeLiveData() {

        mFeatureViewModel.mMoneyLiveData.observe(this, Observer {

            when (it.status) {
                StateBean.EMPTY -> viewBinding.stateView.showEmpty()

                StateBean.FAIL -> viewBinding.stateView.showRetry()

                StateBean.SUCCESS -> {
                    viewBinding.stateView.showContent()
                    mAdapter.replaceData(it.bean!!)
                    it.bean[0].run{
                        viewBinding.balance.text=balance
                        viewBinding.lastDay.text=deadline
                    }
                }

            }
        })

    }
}