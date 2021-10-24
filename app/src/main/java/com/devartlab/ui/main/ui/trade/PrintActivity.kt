package com.devartlab.ui.main.ui.trade

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.devartlab.R
import com.devartlab.ui.main.ui.callmanagement.trade.printer.PrinterConnectActivity
import com.devartlab.ui.main.ui.callmanagement.trade.printer.printerControl.BixolonPrinter


class PrintActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)
        startConnectionActivity()

    }

    init {
        bxlPrinter = BixolonPrinter(this@PrintActivity)
    }

    companion object {
        public var bxlPrinter: BixolonPrinter? = null


        fun showMsg(text: String) {
        }

    }


    fun startConnectionActivity() {
        val intent = Intent(this@PrintActivity, PrinterConnectActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        bxlPrinter?.printerClose()
    }

    fun print(view: View) {


        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo_print)
        bxlPrinter?.printImage(bitmap, 500, BixolonPrinter.ALIGNMENT_CENTER, 50, 0, 1)
        bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)
        bxlPrinter?.printText("-----------------------------\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)

        bxlPrinter?.printText("Devart Lab\n", BixolonPrinter.ATTRIBUTE_BOLD, 0, 1)
        bxlPrinter?.printText("فاتورة بيع نقدي\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
        bxlPrinter?.printText("اسم المندوب : احمد طاهر احمد\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
        bxlPrinter?.printText("رقم المندوب : 01018388777\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
        bxlPrinter?.printText("اسم العميل  : كارفور\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
        bxlPrinter?.printText("رقم العميل : 01018388777 \n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
        bxlPrinter?.printText("العنوان :  شبرا\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)

        bxlPrinter?.printText("-----------------------------\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)

        bxlPrinter?.printText("المنتجات\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 1)


        bxlPrinter?.printText("المنتجات\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)

        bxlPrinter?.printText("الاسم     السعر     الكمية   اجمالى\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 1)

        bxlPrinter?.printText("-----------------------------\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)
        bxlPrinter?.printText("الاجمالى = \n550", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)

        bxlPrinter?.printText("Total =  550\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)

        bxlPrinter?.printText("-----------------------------\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)

        val  data = ("Thank you for your order!\n" +
                "www.devartlab.com\n" +
                "TOGETHER FOR WORTHY LIFE.\n\n\n\n")
        bxlPrinter?.printText(data , BixolonPrinter.ALIGNMENT_CENTER, BixolonPrinter.ATTRIBUTE_BOLD, 1)


    }

}
