package cn.kaicity.app.iguangke.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.databinding.FragmentLoginBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.other.ChildFragment
import cn.kaicity.app.iguangke.ui.user.UserViewModel
import cn.kaicity.app.iguangke.util.InjectorUtil
import cn.kaicity.app.iguangke.util.showSnack

class LoginFragment : ChildFragment() {

    private lateinit var viewBinding: FragmentLoginBinding

    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentLoginBinding.inflate(inflater)
        initLoginButton()
        return viewBinding.root
    }

    private fun initLoginButton() {
        viewBinding.loginButton.setOnClickListener {

            viewModel.login(
                viewBinding.accountEdit.text.toString(),
                viewBinding.passwordEdit.text.toString()
            )
        }
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.header.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, InjectorUtil.getLoginFactory()).get(LoginViewModel::class.java)
    }

    override fun observeLiveData() {
        viewModel.mLoginLiveData.observe(viewLifecycleOwner,
            Observer<StateBean<UserBean>> {
                when (it.status) {
                    StateBean.FAIL -> loginFail(it.msg)

                    StateBean.SUCCESS -> loginSuccess(it.bean!!)

                    StateBean.LOADING -> loading()
                }
            })
    }

    private fun loginFail(msg: String) {
        viewBinding.loginButton.fail()
        showSnack(msg)
    }

    private fun loginSuccess(bean: UserBean) {
        viewBinding.loginButton.complete()
        showSnack("登录成功，正在返回")
        val mUserViewModel=ViewModelProvider(getMainActivity(),InjectorUtil.getUserFactory()).get(UserViewModel::class.java)
        mUserViewModel.save(bean)
        mUserViewModel.clearCourse()
        mUserViewModel.mUserLiveData.postValue(bean)
        findNavController().navigateUp()
    }


    private fun loading() {
        viewBinding.loginButton.start()
    }

}