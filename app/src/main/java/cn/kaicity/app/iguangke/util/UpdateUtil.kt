package cn.kaicity.app.iguangke.util

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.KEYS
import cn.kaicity.app.iguangke.data.bean.VersionBean
import java.io.File


class UpdateUtil(val bean: VersionBean) {


    fun start(context: Context) {
        val shared = context.getSharedPreferences(KEYS.SETTING, Context.MODE_PRIVATE)
        val ignore = shared.getInt(KEYS.IGNORE_VERSION, 0)

        if (ignore != bean.version) {
            AlertDialog.Builder(context).setTitle("发现新版本").setMessage(createExplain(bean))
                .setPositiveButton("立即下载") { _, _ ->
                    startDown(bean.downUrl, bean.versionName, context)
                }
                .setNegativeButton("下次再说", null)
                .setNeutralButton("忽略此版本") { _, _ ->
                    shared.edit().putInt(KEYS.IGNORE_VERSION, bean.version).apply()
                }.show()
        }

    }


    private fun startDown(url: String, versionName: String, context: Context) {
        val task = DownloadManager.Request(Uri.parse(url))
        task.setTitle(context.getString(R.string.app_name))
        task.setDescription(versionName)

        //设置是否允许手机在漫游状态下下载
        task.setAllowedOverRoaming(false)
        task.setMimeType("application/vnd.android.package-archive")
        task.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        task.setDestinationInExternalFilesDir(
            context,
            Environment.DIRECTORY_DOWNLOADS,
            "$versionName.apk"
        )

        val downloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadId = downloadManager.enqueue(task)

        context.registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (context != null) {
                        waitDownloadSuccess(downloadId, versionName, downloadManager, context)
                    }
                }
            },
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun waitDownloadSuccess(
        id: Long,
        versionName: String,
        downloadManager: DownloadManager,
        context: Context
    ) {
        val query = DownloadManager.Query()
        //通过下载的id查找
        query.setFilterById(id)
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val status =
                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                installApk(
                    context,
                    File(
                        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                        "$versionName.apk"
                    )
                )
            }
        }

    }


    private fun installApk(context: Context, file: File) {
        val mSdkInt = Build.VERSION.SDK_INT
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (mSdkInt >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val contentUri = FileProvider.getUriForFile(
                context,
                "cn.kaicity.app.iguangke.fileProvider",
                file
            )
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }

        context.startActivity(intent)
    }

    private fun createExplain(bean: VersionBean): String {
        val sb = StringBuilder()
        sb.append("新版本")
        sb.append(bean.versionName)
        sb.append("已发布\n")
        sb.append("更新说明：\n")
        sb.append(bean.explain)
        return sb.toString()
    }

}