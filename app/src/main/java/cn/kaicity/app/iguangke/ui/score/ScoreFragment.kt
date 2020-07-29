package cn.kaicity.app.iguangke.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kaicity.app.iguangke.databinding.FragmentScoreBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.base.ChildFragment

class ScoreFragment : ChildFragment() {

    private lateinit var viewBinding: FragmentScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentScoreBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.header.root)
    }

    override fun observeLiveData() {

    }
}