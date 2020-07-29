package cn.kaicity.app.iguangke.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.kaicity.app.iguangke.data.bean.StateBean
import cn.kaicity.app.iguangke.data.bean.UserBean
import cn.kaicity.app.iguangke.databinding.FragmentLoginBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.ui.base.ChildFragment
import cn.kaicity.app.iguangke.util.InjectionUtil
import com.google.android.material.snackbar.Snackbar

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
        viewModel = InjectionUtil.getLoginViewModel()
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
        showToast(msg)
    }

    private fun loginSuccess(bean: UserBean) {
        viewBinding.loginButton.complete()
        showToast("登录成功，正在返回")
    }


    private fun loading() {
        viewBinding.loginButton.start()
    }

    private fun showToast(string: String) {
        Snackbar.make(viewBinding.root, string, Snackbar.LENGTH_LONG).show()
    }
}