package cn.kaicity.app.iguangke.data.bean

data class StateBean<T>(val status: Int,val msg:String="", val bean: T?=null)
{
    companion object{

        const val SUCCESS=0
        const val FAIL=1
        const val LOADING=2
    }
}