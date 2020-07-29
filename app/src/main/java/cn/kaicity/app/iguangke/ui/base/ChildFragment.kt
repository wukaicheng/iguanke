package cn.kaicity.app.iguangke.ui.base

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer

abstract class ChildFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getHeaderViewBinding().headerTitle.transitionName = KEYS.SHARE_TITLE
        getHeaderViewBinding().headerTitle.text = arguments?.getString(KEYS.TITLE)

        getHeaderViewBinding().headerNavImg.visibility = View.VISIBLE
        getHeaderViewBinding().headerNavImg.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val params = arguments?.getParcelable<TransformationLayout.Params?>(KEYS.TRANS_PARAMS)
        onTransformationEndContainer(params)
    }


    abstract fun getHeaderViewBinding(): LayoutHeaderBinding
}