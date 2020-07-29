package cn.kaicity.app.iguangke.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnack(msg: String) {
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
}