package cn.kaicity.app.iguangke.ui.feature.money

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.bean.MoneyItem
import cn.kaicity.app.iguangke.databinding.ItemMoneyBinding
import cn.kaicity.app.iguangke.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*

class MoneyAdapter : BaseAdapter<ItemMoneyBinding, MoneyItem>() {
    override fun getViewBinding(parent: ViewGroup): ItemMoneyBinding {
        return ItemMoneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    @SuppressLint("SetTextI18n")
    override fun initView(binding: ItemMoneyBinding, position: Int, data: MoneyItem) {
        binding.good.text = data.transactionTitle
        binding.payTime.text = data.transactionDate

        val startText: String
        val color = if (data.transactionCode == "1") {
            startText = "-"
            App.context.getColor(R.color.roman)
        } else {
            startText = "+"
            App.context.getColor(R.color.caribbean_green)
        }

        binding.moneyNum.text = startText + data.transactionAmount
        binding.moneyNum.setTextColor(color)

    }


}