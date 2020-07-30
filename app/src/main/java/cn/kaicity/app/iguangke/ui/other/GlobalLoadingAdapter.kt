package cn.kaicity.app.iguangke.ui.other

import android.view.View
import cn.kaicity.app.iguangke.util.LogUtil
import cn.kaicity.app.iguangke.view.LoadingView
import com.billy.android.loading.Gloading

class GlobalLoadingAdapter : Gloading.Adapter {
    override fun getView(holder: Gloading.Holder?, convertView: View?, status: Int): View {
        var loadingStatusView: LoadingView? = null
        if (convertView != null && convertView is LoadingView) {
            loadingStatusView = convertView
        }
        if (loadingStatusView == null) {
            loadingStatusView = LoadingView(holder!!.context,holder.retryTask)
        }
        loadingStatusView.setStatus(status)
        return loadingStatusView
    }


}