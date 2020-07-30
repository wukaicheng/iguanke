package cn.kaicity.app.iguangke.ui.feature.score

import android.view.LayoutInflater
import android.view.ViewGroup
import cn.kaicity.app.iguangke.data.bean.ScoreShowBean
import cn.kaicity.app.iguangke.databinding.LayoutScoreItemBinding
import cn.kaicity.app.iguangke.ui.base.BaseAdapter


class ScoreAdapter : BaseAdapter<LayoutScoreItemBinding, ScoreShowBean.ScoreBean>() {
    override fun getViewBinding(parent: ViewGroup): LayoutScoreItemBinding {
        return LayoutScoreItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun initView(binding: LayoutScoreItemBinding, position: Int, data: ScoreShowBean.ScoreBean) {
        binding.className.text = data.className
        binding.scoreText.text = data.score
    }
}