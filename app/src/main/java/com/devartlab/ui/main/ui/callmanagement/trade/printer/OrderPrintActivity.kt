package com.devartlab.ui.main.ui.callmanagement.trade.printer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.VanStoctaking
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.tradedetails.TradeDetailsEntity
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.databinding.FragmentOrderPrintBinding
import com.devartlab.model.*
import com.devartlab.ui.main.ui.trade.OrderPrintAdapter
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mazenrashed.printooth.ui.ScanningActivity
import kotlin.collections.ArrayList


private const val TAG = "OrderPrintActivity"

class OrderPrintActivity : BaseActivity<FragmentOrderPrintBinding>() {

    lateinit var binding: FragmentOrderPrintBinding
    lateinit var viewModel: TradeViewModel
    lateinit var adapter: OrderPrintAdapter
    var subtotal = 0.0
    var txts = ArrayList<String>()
    var ids = ArrayList<Int>()
    var typeID = 0
    var paymentMethodId = 0
    var contractID = 0
    var products = ArrayList<ContractEntity>()
    var customerModel: PlanEntity? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_order_print
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)
        adapter = OrderPrintAdapter(this, ArrayList())
        // bxlPrinter = BixolonPrinter(this)


        customerModel = intent.extras?.getParcelable("CUSTOMER_MODEL")
        typeID = intent.getIntExtra("INVOICE_TYPE_ID", 0)
        paymentMethodId = intent.getIntExtra("PaymentMethodId", 0)
        contractID = intent.getIntExtra("CONTRACT_ID", 0)


        setRecyclerViews()


        val list = ArrayList<ContractEntity>()

        val text = intent?.getStringExtra("PRODUCTS")!!

        Log.d(TAG, "onCreate: " + text)

        val gson = GsonBuilder().create()
        products = gson.fromJson<java.util.ArrayList<ContractEntity>>(
            text,
            object : TypeToken<java.util.ArrayList<ContractEntity>>() {}.type
        )

        for (m in products) {
            System.out.println(m.toString())

            if (m.count!! > 0) {
                list.add(m)
                subtotal = subtotal + (m.price!! * m.count!!)
            }
        }

        binding.subtotal.text = subtotal.toString()
        binding.tax.text = "0"
        binding.total.text = (subtotal + binding.tax.text.toString().toInt()).toString()


        adapter.setMyData(list)
        setObservers()


        val model = intent?.getParcelableExtra<CustomerTrade>("CustomerTrade")

        binding.order.setOnClickListener {


            /* var requestObject =
                 ReportsFilterModel(

                     _Option = 21,
                     LoginUserAccountId = viewModel.dataManager?.user?.accId,
                     EmployeeIdStr = viewModel.dataManager?.user?.empId?.toString(),
                     AccountIdStr = viewModel.dataManager?.user?.accId?.toString(),                    PageSize = 100,
                     PageNumber = 1,
                     AllowToBrowesAllRecord = true,
                     StoreIdStr = "91"
                  )

             viewModel.getMyProducts(requestObject)*/


            Log.d(TAG, "onCreate: $paymentMethodId")
            when (paymentMethodId) {


                1 -> {
                    // cash ...

                    val list = ArrayList<TradeDetailsEntity>()
                    for (m in products) {

                        Log.d(TAG, "onCreate: " + m.itemId.toString())
                        val model =
                            TradeDetailsEntity(
                                null,
                                null,
                                null,
                                m.itemId,
                                m.itemPrincipalUnitId,
                                m.count?.toDouble(),
                                null,
                                m.price?.toDouble(),
                                null,
                                m.count?.toDouble()!! * m.price?.toDouble()!!,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                viewModel.dataManager?.user?.storeId,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                            )

                        list.add(model)
                    }




                    if (viewModel.dataManager?.offlineMood!!) {
                        var sendModel = TradeMasterEntity(
                            null,
                            null,
                            typeID,
                            null,
                            CommonUtilities.getCurrentDate(),
                            null,
                            null,
                            null,
                            customerModel?.customerid,
                            viewModel.dataManager?.user?.storeId,
                            null,
                            null,
                            viewModel.dataManager?.user?.accId,
                            viewModel.dataManager?.user?.empId,
                            "android",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            binding.total.text.toString().toDouble(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            binding.total.text.toString().toDouble(),
                            0.0,
                            null,
                            products[0].contractId,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            false
                        )
                        viewModel.insertTradeOffline(sendModel, list)
                    } else {

                        var sendModel = InvTrxSalesPurchaseModel(
                            null,
                            null,
                            typeID,
                            null,
                            CommonUtilities.getCurrentDate(),
                            null,
                            null,
                            null,
                            customerModel?.customerid,
                            viewModel.dataManager?.user?.storeId,
                            null,
                            null,
                            viewModel.dataManager?.user?.accId,
                            viewModel.dataManager?.user?.empId,
                            "android",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            binding.total.text.toString().toDouble(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            binding.total.text.toString().toDouble(),
                            0.0,
                            null,
                            products[0].contractId,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            list,
                            null,
                            null,
                            null,
                            false
                        )

                        val json = Gson().toJsonTree(sendModel).asJsonObject
                        viewModel.InsertAndUpdate(json)
                    }


                }

                3 -> {
                    // collect

                    val dialogBuilder = android.app.AlertDialog.Builder(this)
                    // ...Irrelevant code for customizing the buttons and title
                    val inflater = this.layoutInflater
                    val dialogView = inflater.inflate(R.layout.choose_invoice_type, null)
                    dialogBuilder.setView(dialogView)

                    val paidET = dialogView.findViewById<View>(R.id.paid) as EditText
                    val addButton = dialogView.findViewById<View>(R.id.addButton) as Button


                    val alertDialog = dialogBuilder.create()

                    addButton.setOnClickListener {

                        val list = ArrayList<TradeDetailsEntity>()
                        for (m in products) {

                            Log.d(TAG, "onCreate: " + m.itemId.toString())
                            val model =
                                TradeDetailsEntity(
                                    null,
                                    null,
                                    null,
                                    m.itemId,
                                    m.itemPrincipalUnitId,
                                    m.count?.toDouble(),
                                    null,
                                    m.price?.toDouble(),
                                    null,
                                    m.count?.toDouble()!! * m.price?.toDouble()!!,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    viewModel.dataManager?.user?.storeId,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                                )

                            list.add(model)
                        }


                        var sendModel = InvTrxSalesPurchaseModel(
                            null,
                            null,
                            typeID,
                            null,
                            CommonUtilities.getCurrentDate(),
                            null,
                            null,
                            null,
                            customerModel?.customerid,
                            viewModel.dataManager?.user?.storeId,
                            null,
                            null,
                            viewModel.dataManager?.user?.accId,
                            viewModel.dataManager?.user?.empId,
                            "android",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            binding.total.text.toString().toDouble(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            paidET.text.toString().toDouble(),
                            binding.total.text.toString().toDouble() - paidET.text.toString()
                                .toDouble(),
                            null,
                            products[0].contractId,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            list,
                            null,
                            null,
                            null,
                            false
                        )


                        val json = Gson().toJsonTree(sendModel).asJsonObject


                        System.out.println(" sendModel " + json)
                        Log.d(TAG, "onCreate: " + json)

                        viewModel.InsertAndUpdate(json)
                        /* if (bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)!!) {
                             viewModel.InsertAndUpdate(json)
                         } else {
                             startConnectionActivity()
                         }*/

                    }
                    alertDialog.show()

                }

            }


        }

        binding.print.setOnClickListener {
            //  print()
        }



    }


    private fun setRecyclerViews() {

        binding.recyclerView?.adapter = adapter
        ScanningActivity

    }

    private fun setObservers() {


        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                    //  this.finish()

                    binding.print.isEnabled = true
                }
                1 -> {

                    ProgressLoading.show(this)
                }
                10 -> {

                    ProgressLoading.dismiss()
                    finish()
                    //  print()
                }
                9 -> {


                    finish()
                    //  print()
                }

            }
        })

        viewModel.myProductLive.observe(this, androidx.lifecycle.Observer {

            if (it.isSuccesed) {

                val list = it.data?.vanStoctaking as ArrayList<VanStoctaking>
                products


                for (m in products) {

                    Log.d(TAG, "products: " + m.toString())
                    for (n in list) {
                        Log.d(TAG, "list: " + n.toString())
                        if (m.itemId == n.itemID) {


                        }

                    }
                }

                val dialogBuilder = android.app.AlertDialog.Builder(this)
                // ...Irrelevant code for customizing the buttons and title
                val inflater = this.layoutInflater
                val dialogView = inflater.inflate(R.layout.choose_invoice_type, null)
                dialogBuilder.setView(dialogView)

                val paidET = dialogView.findViewById<View>(R.id.paid) as EditText
                val addButton = dialogView.findViewById<View>(R.id.addButton) as Button


                val alertDialog = dialogBuilder.create()

                addButton.setOnClickListener {

                    val list = ArrayList<TradeDetailsEntity>()
                    for (m in products) {

                        Log.d(TAG, "onCreate: " + m.itemId.toString())
                        val model =
                            TradeDetailsEntity(
                                null,
                                null,
                                null,
                                m.itemId,
                                m.itemPrincipalUnitId,
                                m.count?.toDouble(),
                                null,
                                m.price?.toDouble(),
                                null,
                                m.count?.toDouble()!! * m.price?.toDouble()!!,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                91,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                            )

                        list.add(model)
                    }


                    var sendModel = InvTrxSalesPurchaseModel(
                        null,
                        null,
                        typeID,
                        null,
                        CommonUtilities.getCurrentDate(),
                        null,
                        null,
                        null,
                        customerModel?.customerid,
                        viewModel.dataManager?.user?.storeId,
                        null,
                        null,
                        viewModel.dataManager?.user?.accId,
                        viewModel.dataManager?.user?.empId,
                        "android",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        binding.total.text.toString().toDouble(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        products[0].contractId,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        list,
                        null,
                        null,
                        null,
                        false
                    )


                    val json = Gson().toJsonTree(sendModel).asJsonObject


                    System.out.println(" sendModel " + json)
                    Log.d(TAG, "onCreate: " + json)

                    viewModel.InsertAndUpdate(json)
                    /* if (bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)!!) {
                         viewModel.InsertAndUpdate(json)
                     } else {
                         startConnectionActivity()
                     }*/

                }
                alertDialog.show()


            }
        })

    }


    private fun replace_fragment(fragment: Fragment, s: String) {

        this.supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_left,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_left
        )
            .add(
                R.id.Container,
                fragment
            )
            .addToBackStack("s")
            .commit()
    }


    /* fun print() {


         if (bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)!!) {
             val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo_print)
             bxlPrinter?.printImage(bitmap, 500, BixolonPrinter.ALIGNMENT_CENTER, 50, 0, 1)
             bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)


             bxlPrinter?.printText("Devart Lab\n", BixolonPrinter.ATTRIBUTE_BOLD, 0, 1)
             bxlPrinter?.printText("فاتورة بيع نقدي\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
             bxlPrinter?.printText("اسم المندوب : احمد طاهر احمد\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
             bxlPrinter?.printText("رقم المندوب : 01018388777\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
             bxlPrinter?.printText("اسم العميل  : كارفور\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
             bxlPrinter?.printText("رقم العميل : 01018388777 \n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
             bxlPrinter?.printText("العنوان :  شبرا\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)

             bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)

             bxlPrinter?.printText("المنتجات\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 1)

             bxlPrinter?.printText("الاسم         السعر     الكمية   اجمالى" + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)


             *//*     val charArray = CharArray(length)
                 Arrays.fill(charArray, ' ')
                 val str = String(charArray)*//*

            for (m in products) {

                if (m.count!! > 0) {

                    bxlPrinter?.printText(m.itemArName + "      " + m.price + "      " + m.count + "      " + (m.count!! * m.price!!).toString() + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)
                    subtotal = subtotal + (m.price!! * m.count!!)


                }
            }


            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)
            bxlPrinter?.printText("الاجمالى = ", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)
            bxlPrinter?.printText(binding.total.text.toString() + " L.E" + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)

            bxlPrinter?.printText("Total = ", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)
            bxlPrinter?.printText(binding.total.text.toString() + " L.E" + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)

            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)


            bxlPrinter?.printText("توقيع العميل: __________ " + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 2)

            bxlPrinter?.printText("توقيع البائع: __________ " + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 2)



            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 2)

            val data = ("Thank you for your order!\n" +
                    "www.devartlab.com\n" +
                    "TOGETHER FOR WORTHY LIFE.\n\n\n\n")
            bxlPrinter?.printText(data, BixolonPrinter.ALIGNMENT_CENTER, BixolonPrinter.ATTRIBUTE_BOLD, 1)
        } else {
            startConnectionActivity()
        }


    }*/


    fun startConnectionActivity() {
        val intent = Intent(this, PrinterConnectActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // bxlPrinter?.printerClose()
    }

    init {
        //  bxlPrinter = BixolonPrinter(this)
    }

    companion object {
        //   public var bxlPrinter: BixolonPrinter? = null


        fun showMsg(text: String) {
        }

    }


}
