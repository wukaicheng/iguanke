package cn.kaicity.app.iguangke.ui.other

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.setPadding
import cn.kaicity.app.iguangke.BuildConfig
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.databinding.FragmentAboutBinding
import cn.kaicity.app.iguangke.databinding.LayoutHeaderBinding
import cn.kaicity.app.iguangke.util.LogUtil
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutFragment : ChildFragment() {

    private lateinit var viewBinding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentAboutBinding.inflate(inflater)
        viewBinding.version.text = BuildConfig.VERSION_NAME
        val aboutView = createAboutView()

        viewBinding.root.addView(aboutView)
        return viewBinding.root
    }

    private fun createAboutView(): View {
R.layout.about_page
        return AboutPage(requireContext())
            .isRTL(false)
            .setDescription(getString(R.string.description))
            .addGroup("源代码")
            .addItem(getGitHubRepo())
            .addItem(getGiteeRepo())
            .addGroup(getString(R.string.contact_group))
            .addEmail("wukaicheng@kaicity.cn", "Email")
            .addGitHub("wukaicheng")
            .create()
    }

    override fun getHeaderViewBinding(): LayoutHeaderBinding {
        return LayoutHeaderBinding.bind(viewBinding.root)
    }

    private fun getGitHubRepo(): Element {
        val gitHubElement = Element()
        gitHubElement.title = "Github"
        gitHubElement.iconDrawable = R.drawable.about_icon_github
        gitHubElement.iconTint = R.color.about_github_color
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse("https://github.com/wukaicheng/iguanke")
        gitHubElement.intent = intent

        return gitHubElement
    }

    private fun getGiteeRepo(): Element {
        val giteeElement = Element()
        giteeElement.title = "Gitee"
        giteeElement.iconDrawable = R.drawable.gitee
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse("https://gitee.com/kaicity/iguanke")
        giteeElement.intent = intent

        return giteeElement
    }

    override fun observeLiveData() {

    }

}