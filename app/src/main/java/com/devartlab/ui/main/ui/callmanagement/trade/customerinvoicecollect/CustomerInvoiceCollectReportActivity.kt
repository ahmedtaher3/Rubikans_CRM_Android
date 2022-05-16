package com.devartlab.ui.main.ui.callmanagement.trade.customerinvoicecollect

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.collect.CollectEntity
import com.devartlab.data.room.invoicedetailes.CustomerInvoiceEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.databinding.EmployeeInvoiceCollectFragmentBinding
import com.devartlab.model.DevartLabReportsFilterDTO
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.main.ui.callmanagement.trade.TradeReportsViewModel
import com.devartlab.ui.main.ui.callmanagement.trade.customerinvoice.CustomerInvoiceCollectReportAdapter
import com.devartlab.ui.main.ui.cycles.ChangeCycle
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.InputFilterMinMax
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "CustomerInvoiceCollectR"

class CustomerInvoiceCollectReportActivity : BaseActivity<EmployeeInvoiceCollectFragmentBinding>(), CustomerInvoiceCollectReportAdapter.OnPayClick {
    private lateinit var viewModel: TradeReportsViewModel
    private lateinit var binding: EmployeeInvoiceCollectFragmentBinding
    private lateinit var adapter: CustomerInvoiceCollectReportAdapter
    private lateinit var filterModel: DevartLabReportsFilterDTO

    private var mDrawableBuilder: TextDrawable? = null

    var fullList = ArrayList<CustomerInvoiceEntity>()


    var chooseEmployee: ChooseEmployee? = null
    var changeCycle: ChangeCycle? = null
    var fromDate = ""
    var toDate = ""
    var cycleId = 0
    var accId = "0"
    private var DATE: String? = null
    var fmt: SimpleDateFormat? = null
    private var Shift: String = "AM Shift"
    private var ShiftID: String = "8"


    var total = 0.0

    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)
    lateinit var customerModel: PlanEntity
    override fun getLayoutId(): Int {
        return R.layout.employee_invoice_collect_fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.collect_invoices)


        viewModel = ViewModelProviders.of(this).get(TradeReportsViewModel::class.java)
        adapter = CustomerInvoiceCollectReportAdapter(this, ArrayList(), this)

        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)

        customerModel = intent?.getParcelableExtra<PlanEntity>("CUSTOMER_MODEL")!!

        setName(customerModel.customerName, CommonUtilities.randomColor)
        binding.name.text = customerModel.customerName
        binding.customerClass.text = customerModel._class
        binding.brick.text = customerModel.brick


        filterModel = DevartLabReportsFilterDTO(2,
                                                1000,
                                                1,
                                                viewModel.dataManager.user.accId.toString(),
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                false,
                                                null,
                                                null,
                                                null,
                                                null,
                                                false,
                                                null,
                                                customerModel.customerid,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null)



        binding.recycler.adapter = adapter

        accId = viewModel.dataManager.user.accId.toString()
        viewModel.getSalesPurchaseReport(filterModel)




        binding.pay.setOnClickListener {


            val dialogBuilder = android.app.AlertDialog.Builder(this) // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.choose_invoice_type, null)
            dialogBuilder.setView(dialogView)
            val paidET = dialogView.findViewById<View>(R.id.paid) as EditText
            val addButton = dialogView.findViewById<View>(R.id.addButton) as Button


            paidET.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    if (charSequence.toString().toDouble() > total)
                    {
                        paidET.setText(total.toString())
                    }

                }

                override fun afterTextChanged(editable: Editable) {}
            })


            var isPaid = true

            val alertDialog = dialogBuilder.create()
            addButton.setOnClickListener {


                val paidText = paidET.text.toString()

                Toast.makeText(this@CustomerInvoiceCollectReportActivity, paidText, Toast.LENGTH_SHORT).show()
                val list = ArrayList<CollectEntity>()


                var paid = paidText.toDouble()

                for (model in fullList) {

                    var singlePaid = 0.0
                    Log.d(TAG, "paid" + paid.toString())
                    if (paid > model.totalReminder!!) {
                        singlePaid = model.totalReminder!!
                        paid -= model.totalReminder!!

                        Log.d(TAG, "if" + paid.toString())
                        Log.d(TAG, "totalReminder" + model.totalReminder!!.toString() + "paid" + paid.toString())

                    }
                    else {
                        singlePaid = paid
                        paid = 0.0
                        Log.d(TAG, "else" + paid.toString())
                        Log.d(TAG, "totalReminder" + model.totalReminder!!.toString() + "paid" + paid.toString())

                    }


                    list.add(CollectEntity(null,
                                           model.invoiceId,
                                           model.invoiceTypeId,
                                           null,
                                           null,
                                           null,
                                           singlePaid,
                                           null,
                                           null,
                                           null,
                                           null,
                                           null,
                                           customerModel.customerid?.toInt(),
                                           null,
                                           null))
                }


                viewModel.collectMoney(list)

            }
            alertDialog.show()


        }

        setObservers()

    }

    fun setName(title: String?, color: Int) {
        var letter = "A"
        if (title != null && !title.isEmpty()) {
            letter = title.substring(0, 1)
        }
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color)
        binding.image.setImageDrawable(mDrawableBuilder)
    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, Observer {

            Log.d(TAG, "setObservers: "+Gson().toJson(it))

            for (model in it) {
                total += model.totalReminder!!
            }
            binding.totalReminder.text = "$total L.E"

            if (!it.isNullOrEmpty()) {
                System.out.println(it.toString())
                fullList.clear()

                for (i in it) {
                    if (i.totalReminder!! > 0.0) {
                        fullList.add(i)
                    }
                }
                adapter.setMyData(fullList)
            }


        })
        viewModel.responseCollect.observe(this, Observer {
            System.out.println(it.toString())
            if (it.isSuccesed) {
                Toast.makeText(this, "isSucceed", Toast.LENGTH_SHORT).show()

            }
            else {
                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }

        })
        viewModel.progress.observe(this, Observer {


            when (it) {
                1 -> {
                    this?.let { ProgressLoading.show(it) }
                }
                0 -> {
                    ProgressLoading.dismiss()
                }

                10 -> {
                    ProgressLoading.dismiss()
                    Toast.makeText(this, getString(R.string.collect_done), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

        })
    }


    override fun setOnPayClick(model: CustomerInvoiceEntity) {


        val dialogBuilder = android.app.AlertDialog.Builder(this) // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.choose_invoice_type, null)
        dialogBuilder.setView(dialogView)
        val paidET = dialogView.findViewById<View>(R.id.paid) as EditText
        val addButton = dialogView.findViewById<View>(R.id.addButton) as Button

        var isPaid = true

        paidET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if (charSequence.toString().toDouble() > model.totalReminder!!)
                {
                    paidET.setText(model.totalReminder?.toString())
                }

            }

            override fun afterTextChanged(editable: Editable) {}
        })


        val alertDialog = dialogBuilder.create()
        addButton.setOnClickListener {


            val list = ArrayList<CollectEntity>()
            list.add(CollectEntity(null,
                                                            model.invoiceId,
                                                            model.invoiceTypeId,
                                                            null,
                                                            null,
                                                            null,
                                                            paidET.text.toString().toDouble(),
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            customerModel.customerid?.toInt(),
                                                            null,
                                                            null)


            )
            viewModel.collectMoney(list)

        }
        alertDialog.show()


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




}
