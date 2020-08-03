package cn.kaicity.app.iguangke.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import cn.kaicity.app.iguangke.data.bean.MainLayoutBean
import cn.kaicity.app.iguangke.databinding.ItemMainBinding
import cn.kaicity.app.iguangke.ui.base.BaseAdapter

class MainAdapter() :
    BaseAdapter<ItemMainBinding, MainLayoutBean>() {

    companion object {
        const val LONG_ITEM = 1
        const val SHORT_ITEM = 2
    }


    override fun getItemViewType(position: Int): Int {
        val data = getData(position)
        return if (data.isBig) {
            LONG_ITEM
        } else {
            SHORT_ITEM
        }
    }


    override fun getViewBinding(parent: ViewGroup): ItemMainBinding {
        return ItemMainBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun initView(binding: ItemMainBinding, position: Int, data: MainLayoutBean) {
        binding.itemIcon.setImageResource(data.image)
        binding.itemTitle.text = data.title
        binding.itemSubTitle.text = data.subTitle
        binding.background.setBackgroundColor(data.color)

    }

}