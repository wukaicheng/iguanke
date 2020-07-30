package cn.kaicity.app.iguangke.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.databinding.LayoutLoadingviewBinding
import com.billy.android.loading.Gloading


@SuppressLint("ViewConstructor")
class LoadingView @JvmOverloads constructor(
    context: Context,
    private val retry: Runnable,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val viewBinding: LayoutLoadingviewBinding

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.layout_loadingview, this, true)
        viewBinding = LayoutLoadingviewBinding.bind(this)

        viewBinding.retry.setOnClickListener {
            retry.run()
        }
    }


    fun setStatus(status: Int) {
        viewBinding.text.visibility = View.VISIBLE
        viewBinding.progress.visibility = View.VISIBLE
        when (status) {

            Gloading.STATUS_LOAD_SUCCESS -> {
                viewBinding.root.visibility = View.GONE
            }

            Gloading.STATUS_LOADING -> {
                viewBinding.text.visibility=View.GONE
                viewBinding.retry.visibility = View.GONE
                viewBinding.progress.visibility = View.VISIBLE
            }

            Gloading.STATUS_EMPTY_DATA -> {
                viewBinding.text.setText(R.string.empty)
                viewBinding.progress.visibility = View.GONE
            }

            Gloading.STATUS_LOAD_FAILED -> {
                viewBinding.text.visibility = View.GONE
                viewBinding.progress.visibility = View.GONE
                viewBinding.retry.visibility = View.VISIBLE
            }
        }
    }
}