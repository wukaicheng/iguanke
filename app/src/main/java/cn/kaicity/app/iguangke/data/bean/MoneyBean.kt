package cn.kaicity.app.iguangke.data.bean


data class MoneyBean(
    val balance: String,
    val canMoney: Int,
    val deadline: String,
    val items: List<MoneyItem>,
    val maxMoney: Int,
    val notReceivedMoney: Int,
    val result: String,
    val returnCode: String,
    val ykth: String
)

data class MoneyItem(
    val balance: String,
    val deadline: String,
    val transactionAmount: String,
    val transactionCode: String,
    val transactionDate: String,
    val transactionTitle: String
)