package cn.kaicity.app.iguangke.ui.feature.news

import android.annotation.SuppressLint
import android.view.ViewGroup
import cn.kaicity.app.iguangke.data.bean.NewsItem
import cn.kaicity.app.iguangke.databinding.ItemNewsBinding
import cn.kaicity.app.iguangke.ui.base.BaseAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class NewsListAdapter : BaseAdapter<ItemNewsBinding, NewsItem>() {
    override fun getViewBinding(parent: ViewGroup): ItemNewsBinding {
        return ItemNewsBinding.inflate(getInflater(parent), parent, false)
    }

    override fun initView(binding: ItemNewsBinding, position: Int, data: NewsItem) {

        Glide.with(binding.imageView).load(data.logos.first()).into(binding.imageView)
        binding.title.text=data.name
        binding.views.text=data.pageView.toString()
        binding.time.text=longToDate(data.createDate)
    }

    fun longToDate(time:Long):String{
        return SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(Date(time)).toString()
    }
}