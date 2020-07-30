package cn.kaicity.app.iguangke.util

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnack(msg: String) {
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showSnack(msg: String, actionName: String?, action: ((View) -> Unit)? = null) {
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).setAction(actionName, action).show()

}