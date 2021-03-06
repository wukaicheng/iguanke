package cn.kaicity.app.iguangke.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.databinding.LayoutUserHeaderBinding
import cn.kaicity.app.iguangke.ui.MainActivity
import com.bumptech.glide.Glide

abstract class BaseFragment : Fragment() {

    fun getMainActivity(): MainActivity {
        return requireActivity() as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
    }


    fun setUserHeader(userHeader: LayoutUserHeaderBinding,title:String,subTitle:String,url:String){
        userHeader.userName.text = title
        userHeader.userClass.text = subTitle
        Glide.with(this).load(url).into(userHeader.userIcon)

    }    fun setUserHeader(userHeader: LayoutUserHeaderBinding,title:String,subTitle:String,id:Int){
        userHeader.userName.text = title
        userHeader.userClass.text = subTitle
        userHeader.userIcon.setImageResource(id)

    }

    abstract fun observeLiveData()
}