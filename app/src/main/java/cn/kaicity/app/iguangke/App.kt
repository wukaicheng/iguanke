package cn.kaicity.app.iguangke

import android.app.Application
import android.content.Context
import com.umeng.commonsdk.UMConfigure

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        context =base!!
    }


    override fun onCreate() {
        super.onCreate()
        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
    }
    companion object{
        lateinit var context: Context
    }
}