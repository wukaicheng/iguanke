package cn.kaicity.app.iguangke.ui.user

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.databinding.FragmentUserBinding
import cn.kaicity.app.iguangke.ui.base.BaseFragment
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.iguangke.util.showMessageDialog
import cn.kaicity.app.iguangke.util.showSnack
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout

class UserFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentUserBinding

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentUserBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(
                getMainActivity(),
                InjectorUtil.getUserFactory()
            ).get(UserViewModel::class.java)

        return viewBinding.root
    }

    private fun initImage(url: String) {
        viewBinding.userAvatar.transitionName = KEYS.IMAGE
        val requestOptions = RequestOptions.placeholderOf(R.drawable.ic_background)
            .onlyRetrieveFromCache(true)
            .dontTransform()

        Glide.with(this).load(url).apply(requestOptions).into(viewBinding.userAvatar)
        Glide.with(this).load(url).apply(requestOptions).into(viewBinding.userAvatarBg)
        Glide.with(this).load(url).apply(requestOptions).into(viewBinding.image)

    }

    private fun initToolbar(nickName: String, studentId: String) {
        getMainActivity().setSupportActionBar(viewBinding.toolbar)
        setHasOptionsMenu(true)
        getMainActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewBinding.toolbarLayout.setBackgroundColor(Color.WHITE)
        viewBinding.headNickName.text = nickName
        viewBinding.headNickName.transitionName = KEYS.TITLE
        viewBinding.headStudentId.text = studentId

        viewBinding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset: Int ->

            val title = if (offset < -250) {
                nickName
            } else {
                ""
            }

            viewBinding.toolbarLayout.title = title
        })


        viewBinding.loginOut.setOnClickListener {
            showMessageDialog("退出账号", "确定退出当前账号？") { _, _ ->
                viewModel.loginOut()
                findNavController().navigateUp()
            }
        }

    }

    private fun updateView(bean: UserBean) {

        viewBinding.className.text = bean.className
        viewBinding.FirstYear.text = bean.readIngYear
        viewBinding.collegeName.text = bean.collegeName
        viewBinding.studentID.text = bean.studentID
        viewBinding.name.text = bean.name
        viewBinding.nickName.text = bean.nickName
        viewBinding.disciplineName.text = bean.disciplineName
    }

    override fun observeLiveData() {
        viewModel.mUserLiveData.observe(this, Observer {
            initImage(it.headImage)
            initToolbar(it.nickName, it.studentID)
            updateView(it)
        })

    }

}