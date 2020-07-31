package cn.kaicity.app.iguangke.ui.main

import cn.kaicity.app.iguangke.App
import cn.kaicity.app.iguangke.R
import cn.kaicity.app.iguangke.data.bean.MultiLayoutBean

class Multi {
    companion object {
        val mMultiDataList: List<MultiLayoutBean> by lazy {
            arrayListOf(
                MultiLayoutBean(
                    R.id.action_mainFragment_to_moneyFragment,
                    "余额查询",
                    "一卡通余额以及消费记录",
                    R.drawable.ic_money,
                    true,
                    App.context.getColor(R.color.caribbean_green)
                ),
                MultiLayoutBean(
                    R.id.action_mainFragment_to_scoreFragment,
                    "成绩查询",
                    "期末考试成绩",
                    R.drawable.ic_score,
                    false,
                    App.context.getColor(R.color.royal_blue)
                ),
                MultiLayoutBean(
                    0,
                    "课表查看",
                    "本学期课程表",
                    R.drawable.ic_class,
                    false,
                    App.context.getColor(R.color.roman)
                )

            )

        }

    }
}