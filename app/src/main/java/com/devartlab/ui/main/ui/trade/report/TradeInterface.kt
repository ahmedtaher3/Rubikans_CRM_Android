package com.devartlab.ui.main.ui.trade.report

interface TradeInterface {

    fun onTradeStartDay(id: String , name: String)

    fun onTradeEndDay(firstAnswer: String, secondAnswer: String, thirdAnswer: String)
}