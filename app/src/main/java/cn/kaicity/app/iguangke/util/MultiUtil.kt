package cn.kaicity.app.iguangke.util

import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.bean.MainLayoutBean

class MultiUtil {
    companion object {
        val mMultiDataList: List<MainLayoutBean> by lazy {
            arrayListOf(
                MainLayoutBean(
                    R.id.action_mainFragment_to_scoreFragment,
                    "成绩查询",
                    "期末考试成绩",
                    R.drawable.ic_score,
                    isBig = false,
                    needLogin = true,
                    color = App.context.getColor(R.color.royal_blue)
                ),
                MainLayoutBean(
                    R.id.action_mainFragment_to_courseFragment,
                    "课表查看",
                    "本学期课程表",
                    R.drawable.ic_class,
                    false,
                    needLogin = true,
                    color = App.context.getColor(R.color.roman)
                ),
                MainLayoutBean(
                    R.id.action_mainFragment_to_moneyFragment,
                    "余额查询",
                    "一卡通余额以及消费记录",
                    R.drawable.ic_money,
                    true,
                    needLogin = true,
                    color = App.context.getColor(R.color.caribbean_green)
                ), MainLayoutBean(
                    R.id.action_mainFragment_to_newsFragment,
                    "新闻公告",
                    "观看学校官方新闻公告",
                    R.drawable.ic_news,
                    true,
                    false,
                    color = App.context.getColor(R.color.fuel_yellow)
                )

            )

        }

    }
}