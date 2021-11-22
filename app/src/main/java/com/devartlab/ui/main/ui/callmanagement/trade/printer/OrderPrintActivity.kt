package com.devartlab.ui.main.ui.callmanagement.trade.printer

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import com.devartlab.data.room.tradedetails.TradeDetailsEntity
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.databinding.FragmentOrderPrintBinding
import com.devartlab.model.CustomerTrade
import com.devartlab.model.InvTrxSalesPurchaseModel
import com.devartlab.ui.main.ui.callmanagement.trade.printer.printerControl.BixolonPrinter
import com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract.SelectProductsActivity
import com.devartlab.ui.main.ui.trade.OrderPrintAdapter
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mazenrashed.printooth.ui.ScanningActivity


private const val TAG = "OrderPrintActivity"

class OrderPrintActivity : BaseActivity<FragmentOrderPrintBinding>(), OrderPrintAdapter.OnRemoveItem {

    lateinit var binding: FragmentOrderPrintBinding
    lateinit var viewModel: TradeViewModel
    lateinit var adapter: OrderPrintAdapter
    var subtotal = 0.0
    var txts = ArrayList<String>()
    var ids = ArrayList<Int>()
    var purchaseTypeEntity: PurchaseTypeEntity? = null
    var contractID = 0
    var products = ArrayList<ContractEntity>()
    var customerModel: PlanEntity? = null
    val list = ArrayList<ContractEntity>()
    override fun getLayoutId(): Int {
        return R.layout.fragment_order_print
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Pay"

        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)
        adapter = OrderPrintAdapter(this, ArrayList(), this)

        customerModel = intent.extras?.getParcelable("CUSTOMER_MODEL")
        purchaseTypeEntity = intent.getParcelableExtra("PurchaseTypeEntity")
        contractID = intent.getIntExtra("CONTRACT_ID", 0)
        val text = intent?.getStringExtra("PRODUCTS")!!


        setRecyclerViews()




        Log.d(TAG, "onCreate: " + text)

        val gson = GsonBuilder().create()
        products = gson.fromJson<java.util.ArrayList<ContractEntity>>(text, object : TypeToken<java.util.ArrayList<ContractEntity>>() {}.type)

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


            Log.d(TAG, "onCreate: ${purchaseTypeEntity?.paymentMethodId}")
            when (purchaseTypeEntity?.paymentMethodId) {


                1 -> { // cash ...

                    val list = ArrayList<TradeDetailsEntity>()
                    for (m in products) {

                        Log.d(TAG, "onCreate: " + m.itemId.toString())
                        val model = TradeDetailsEntity(null,
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
                                                       null)

                        list.add(model)
                    }
                    if (viewModel.dataManager?.offlineMood!!) {
                        var sendModel = TradeMasterEntity(null,
                                                          null,
                                                          purchaseTypeEntity?.invoiceTypeId,
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
                                                          false)
                        viewModel.insertTradeOffline(sendModel, list, customerModel!!, purchaseTypeEntity?.isFinanceTransaction!!)
                    }
                    else {

                        var sendModel = InvTrxSalesPurchaseModel(null,
                                                                 null,
                                                                 purchaseTypeEntity?.invoiceTypeId,
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
                                                                 false)

                        val json = Gson().toJsonTree(sendModel).asJsonObject
                        viewModel.InsertAndUpdate(json, purchaseTypeEntity?.isFinanceTransaction!!)
                    }


                }

                3 -> { // collect

                    val dialogBuilder = android.app.AlertDialog.Builder(this) // ...Irrelevant code for customizing the buttons and title
                    val inflater = this.layoutInflater
                    val dialogView = inflater.inflate(R.layout.choose_invoice_type, null)
                    dialogBuilder.setView(dialogView)
                    val paidET = dialogView.findViewById<View>(R.id.paid) as EditText
                    val addButton = dialogView.findViewById<View>(R.id.addButton) as Button

                    paidET.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                            if (charSequence.toString().toDouble() > binding.total.text.toString().toDouble()) {
                                paidET.setText(binding.total.text.toString())
                            }

                        }

                        override fun afterTextChanged(editable: Editable) {}
                    })

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
                                    null, null, null, null, null, null, null, null, null, null, null, null)

                            list.add(model)
                        }



                        if (viewModel.dataManager?.offlineMood!!) {
                            var sendModel = TradeMasterEntity(null,
                                                              null,
                                                              purchaseTypeEntity?.invoiceTypeId,
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
                                                              binding.total.text.toString().toDouble() - paidET.text.toString().toDouble(),
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
                                                              false)
                            viewModel.insertTradeOffline(sendModel, list, customerModel!!, purchaseTypeEntity?.isFinanceTransaction!!)
                        }
                        else {

                            var sendModel = InvTrxSalesPurchaseModel(null,
                                                                     null,
                                                                     purchaseTypeEntity?.invoiceTypeId,
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
                                                                     binding.total.text.toString().toDouble() - paidET.text.toString().toDouble(),
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
                                                                     false)


                            val json = Gson().toJsonTree(sendModel).asJsonObject
                            viewModel.InsertAndUpdate(json, purchaseTypeEntity?.isFinanceTransaction!!)
                        }


                    }
                    alertDialog.show()

                }

                else -> { // cash ...

                    val list = ArrayList<TradeDetailsEntity>()
                    for (m in products) {

                        Log.d(TAG, "onCreate: " + m.itemId.toString())
                        val model = TradeDetailsEntity(null,
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
                                                       null)

                        list.add(model)
                    }
                    if (viewModel.dataManager?.offlineMood!!) {
                        var sendModel = TradeMasterEntity(null,
                                                          null,
                                                          purchaseTypeEntity?.invoiceTypeId,
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
                                                          false)
                        viewModel.insertTradeOffline(sendModel, list, customerModel!!, purchaseTypeEntity?.isFinanceTransaction!!)
                    }
                    else {

                        var sendModel = InvTrxSalesPurchaseModel(null,
                                                                 null,
                                                                 purchaseTypeEntity?.invoiceTypeId,
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
                                                                 false)

                        val json = Gson().toJsonTree(sendModel).asJsonObject
                        viewModel.InsertAndUpdate(json, purchaseTypeEntity?.isFinanceTransaction!!)
                    }


                }

            }


        }

        binding.print.setOnClickListener { //  print()
        }

        binding.addNew.setOnClickListener {

            val intent = Intent(this, SelectProductsActivity::class.java)
            startActivityForResult(intent, 1)

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

                    ProgressLoading.dismiss() //binding.print.isEnabled = true
                }
                1 -> {

                    ProgressLoading.show(this)
                }
                10 -> {

                    ProgressLoading.dismiss() // finish()
                    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
                    print()
                }

                100 -> {

                    ProgressLoading.dismiss() //  finish()
                    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()

                    print()
                }

                9 -> {


                    finish()
                    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()

                    //  print()
                }

                90 -> {


                    finish()
                    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()

                    //  print()
                }

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


    fun print() {


        if (bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)!!) {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo_print)
            bxlPrinter?.printImage(bitmap, 500, BixolonPrinter.ALIGNMENT_CENTER, 50, 0, 1)
            bxlPrinter?.printText("\n", BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.ATTRIBUTE_BOLD, 1)


            bxlPrinter?.printText("Devart Lab\n", BixolonPrinter.ATTRIBUTE_BOLD, 0, 1)
            bxlPrinter?.printText("فاتورة بيع نقدي\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("اسم المندوب : احمد طاهر احمد\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("رقم المندوب : 01018388777\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("اسم العميل  : \n" + customerModel?.customerName, BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("رقم العميل : 01018388777 \n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)
            bxlPrinter?.printText("العنوان :  شبرا\n", BixolonPrinter.ALIGNMENT_LEFT, 0, 1)

            bxlPrinter?.printText("_______________________\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 2)

            bxlPrinter?.printText("المنتجات\n", BixolonPrinter.ALIGNMENT_CENTER, 0, 1)

            bxlPrinter?.printText("الاسم         السعر     الكمية   اجمالى" + "\n", BixolonPrinter.ALIGNMENT_RIGHT, 0, 1)


            /*     val charArray = CharArray(length)
               Arrays.fill(charArray, ' ')
               val str = String(charArray)*/

            for (m in products) {

                if (m.count!! > 0) {

                    bxlPrinter?.printText(m.itemArName + "      " + m.price + "      " + m.count + "      " + (m.count!! * m.price!!).toString() + "\n",
                                          BixolonPrinter.ALIGNMENT_RIGHT,
                                          0,
                                          1)
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

            val data = ("Thank you for your order!\n" + "www.devartlab.com\n" + "TOGETHER FOR WORTHY LIFE.\n\n\n\n")
            bxlPrinter?.printText(data, BixolonPrinter.ALIGNMENT_CENTER, BixolonPrinter.ATTRIBUTE_BOLD, 1)
        }
        else {
            startConnectionActivity()
        }


    }


    fun startConnectionActivity() {
        val intent = Intent(this, PrinterConnectActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        bxlPrinter?.printerClose()
    }

    init {
        bxlPrinter = BixolonPrinter(this)
    }

    companion object {
        public var bxlPrinter: BixolonPrinter? = null


        fun showMsg(text: String) {
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setOnRemoveItem(model: ContractEntity) {


        list.clear()
        subtotal = 0.0
        products.remove(model)
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

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {


                val text = data?.getStringExtra(("PRODUCTS"))

                val gson = GsonBuilder().create()
                val newList =
                    gson.fromJson<java.util.ArrayList<ContractEntity>>(text, object : TypeToken<java.util.ArrayList<ContractEntity>>() {}.type)


                subtotal = 0.0
                list.clear()
                products.addAll(newList)
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


            }


            // replace_fragment(OrderPrintFragment(), "OrderPrintFragment", theList)

        }

    }


}
