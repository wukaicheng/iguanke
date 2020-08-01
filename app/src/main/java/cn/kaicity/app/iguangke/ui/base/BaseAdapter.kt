package cn.kaicity.app.iguangke.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : ViewBinding, D> : RecyclerView.Adapter<BaseAdapter.ViewHolder<T>>() {

    private var list: ArrayList<D> = arrayListOf()

    private var onItemClick: ((position: Int, binding: T, data: D) -> Unit)? =
        null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return ViewHolder(
            getViewBinding(parent)
        )
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val bean = list[position]
        val binding = holder.bindIng
        initView(binding, position, bean)
        binding.root.setOnClickListener {
            if (onItemClick != null) {
                onItemClick!!(position, holder.bindIng, bean)
            }
        }
    }

    fun getData(position: Int): D {

        return this.list[position]
    }

    open fun replaceData(list: List<D>) {
        this.list =ArrayList(list)
        notifyDataSetChanged()
    }

    open fun addData(list: List<D>) {
        val index = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(index, list.size)
    }

    open fun updateData(bean: D, position: Int) {
        if (list.size > position) {
            list[position] = bean
            notifyItemChanged(position)
        }
    }

    open fun removeData(bean: D): Int {
        val position: Int = this.list.indexOf(bean)
        if (position >= 0) {
            this.list.remove(bean)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size - position);
        }
        return position
    }

    fun setOnItemClick(function: (position: Int, binding: T, data: D) -> Unit) {
        this.onItemClick = function

    }

    fun getInflater(parent: ViewGroup):LayoutInflater{
        return LayoutInflater.from(parent.context)
    }


    abstract fun getViewBinding(parent: ViewGroup): T

    abstract fun initView(binding: T, position: Int, data: D)


    class ViewHolder<T : ViewBinding>(val bindIng: T) :
        RecyclerView.ViewHolder(bindIng.root) {

    }

}