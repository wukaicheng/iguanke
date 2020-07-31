package cn.kaicity.app.iguangke.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import cn.kaicity.app.iguangke.data.bean.MultiLayoutBean
import cn.kaicity.app.iguangke.databinding.ItemMainBigBinding
import cn.kaicity.app.iguangke.databinding.ItemMainBinding

class MultiAdapter(private val multiData: List<MultiLayoutBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onItemClick: ((position: Int, binding: ViewBinding, data: MultiLayoutBean) -> Unit)? =
        null

    companion object {
        const val ONE_ITEM = 1
        const val TWO_ITEM = 2
    }


    override fun getItemViewType(position: Int): Int {
        val data = multiData[position]
        return if (data.isBig) {
            ONE_ITEM
        } else {
            TWO_ITEM
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ONE_ITEM) {
            return OneViewHolder(
                ItemMainBigBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return TwoViewHolder(
                ItemMainBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return multiData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = multiData[position]
        if (getItemViewType(position) == ONE_ITEM) {
            val binding = (holder as OneViewHolder).bindIng
            binding.itemIcon.setImageResource(data.image)
            binding.itemTitle.text = data.title
            binding.itemSubTitle.text = data.subTitle
            binding.background.setBackgroundColor(data.color)

            onItemClick?.run {
                binding.root.setOnClickListener {
                    this(position, binding, data)
                }
            }
        }
        if (getItemViewType(position) == TWO_ITEM) {
            val binding = (holder as TwoViewHolder).bindIng
            binding.itemIcon.setImageResource(data.image)
            binding.itemTitle.text = data.title
            binding.itemSubTitle.text = data.subTitle
            binding.background.setBackgroundColor(data.color)

            onItemClick?.run {
                binding.root.setOnClickListener {
                    this(position, binding, data)
                }
            }
        }

    }


    fun setOnItemClick(function: (position: Int, binding: ViewBinding, data: MultiLayoutBean) -> Unit) {
        this.onItemClick = function

    }


    class OneViewHolder(val bindIng: ItemMainBigBinding) :
        RecyclerView.ViewHolder(bindIng.root)

    class TwoViewHolder(val bindIng: ItemMainBinding) :
        RecyclerView.ViewHolder(bindIng.root)

}