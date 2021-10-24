package com.devartlab.ui.main.ui.trade.report

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices

class EndDayDialog(context: Context, private val myAPI: ApiServices, private val tradeInterface: TradeInterface) : Dialog(context) {


    lateinit var close: ImageView
    lateinit var adapter: ReportCustomersAdapter
    lateinit var customers: RecyclerView

    lateinit var firstQuestion: EditText
    lateinit var secondQuestion: EditText
    lateinit var thirdQuestion: EditText
    lateinit var endDay: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_day_dialog)

        close = findViewById(R.id.close)


        firstQuestion = findViewById(R.id.firstQuestion)
        secondQuestion = findViewById(R.id.secondQuestion)
        thirdQuestion = findViewById(R.id.thirdQuestion)

        endDay = findViewById(R.id.endDay)


        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })


        endDay.setOnClickListener(View.OnClickListener {

            tradeInterface.onTradeEndDay(firstQuestion.text.toString(), secondQuestion.text.toString(), thirdQuestion.text.toString())
            dismiss()
        })


    }


}
