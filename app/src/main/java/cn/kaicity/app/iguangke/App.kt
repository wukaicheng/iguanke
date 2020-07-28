package cn.kaicity.app.iguangke

import android.app.Application
import android.content.Context

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        App.context=base!!
    }

    companion object{
        lateinit var context: Context
    }
}