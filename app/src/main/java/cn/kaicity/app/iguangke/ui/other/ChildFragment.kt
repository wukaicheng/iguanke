package cn.kaicity.app.iguangke.ui.other

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment

abstract class ChildFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title=arguments?.getString(KEYS.TITLE)
        getHeaderViewBinding().headerTitle.transitionName =title
        getHeaderViewBinding().headerTitle.text = title

        getHeaderViewBinding().headerNavImg.visibility = View.VISIBLE
        getHeaderViewBinding().headerNavImg.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    abstract fun getHeaderViewBinding(): LayoutHeaderBinding
}