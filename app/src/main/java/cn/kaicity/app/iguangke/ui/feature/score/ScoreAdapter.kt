package cn.kaicity.app.iguangke.ui.feature.score

import android.view.LayoutInflater
import android.view.ViewGroup
import cn.kaicity.app.iguangke.data.bean.ScoreX
import cn.kaicity.app.iguangke.databinding.ItemScoreBinding
import cn.kaicity.app.iguangke.ui.base.BaseAdapter


class ScoreAdapter : BaseAdapter<ItemScoreBinding, ScoreX>() {
    override fun getViewBinding(parent: ViewGroup): ItemScoreBinding {
        return ItemScoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

    override fun initView(binding: ItemScoreBinding, position: Int, data: ScoreX) {
        binding.className.text = data.course
        binding.scoreText.text = data.score
    }
}