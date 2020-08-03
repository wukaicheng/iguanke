package cn.kaicity.app.iguangke.util

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnack(msg: String) {
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showSnack(msg: String, actionName: String?, action: ((View) -> Unit)? = null) {
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).setAction(actionName, action).show()

}

fun Fragment.showMessageDialog(title: String,msg: String ) {
    AlertDialog.Builder(this.requireContext())
        .setTitle(title).setMessage(msg)
        .setPositiveButton("确定", null)
        .show()
}


fun Fragment.showMessageDialog(title: String,msg: String,postClick:(dialog:DialogInterface,width:Int)->Unit ) {
    AlertDialog.Builder(this.requireContext())
        .setTitle(title).setMessage(msg)
        .setNegativeButton("取消",null)
        .setPositiveButton("确定", postClick)
        .show()
}

