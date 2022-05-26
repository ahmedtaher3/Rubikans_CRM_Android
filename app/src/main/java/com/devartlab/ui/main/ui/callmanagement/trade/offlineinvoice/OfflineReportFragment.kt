package com.devartlab.ui.main.ui.callmanagement.trade.offlineinvoice

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.databinding.EmployeeInvoiceReportFragmentBinding
import com.devartlab.model.DevartLabReportsFilterDTO
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.callmanagement.trade.TradeReportsViewModel
import com.devartlab.ui.main.ui.cycles.ChangeCycle
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OfflineReportFragment : BaseFragment<EmployeeInvoiceReportFragmentBinding>(),
    ChooseEmployeeInterFace {
    private lateinit var viewModel: TradeReportsViewModel
    private lateinit var binding: EmployeeInvoiceReportFragmentBinding
    private lateinit var adapter: OfflineReportAdapter
    private lateinit var filterModel: DevartLabReportsFilterDTO


    var fullList = ArrayList<TradeMasterEntity>()


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

    val c: Calendar = Calendar.getInstance()
    var mYear = c.get(Calendar.YEAR)
    var mMonth = c.get(Calendar.MONTH)
    var mDay = c.get(Calendar.DAY_OF_MONTH)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradeReportsViewModel::class.java)
        adapter = OfflineReportAdapter(baseActivity, ArrayList())



        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)
    }

    override fun getLayoutId(): Int {
        return R.layout.employee_invoice_report_fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        accId = viewModel.dataManager.user.accId.toString()

        binding.recycler.adapter = adapter


        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
            chooseEmployee?.setCanceledOnTouchOutside(true)
            val window = chooseEmployee?.window
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee?.show()

        }


        viewModel.getOfflineInvoices()
        setEmpData()
        setObservers()

    }


    private fun setObservers() {
        viewModel.responseLiveRoom.observe(viewLifecycleOwner, Observer {

            adapter.setMyData(it)
        })

        viewModel.responseLive.observe(viewLifecycleOwner, Observer {
            System.out.println(it.toString())


            if (!it.isNullOrEmpty()) {
                System.out.println(it.toString())
                fullList.clear()


            }


        })
        viewModel.responseLiveDashboard.observe(viewLifecycleOwner, Observer {
            System.out.println(it.toString())


            val customerInvoiceDashboard = it

            binding.allSales.text = customerInvoiceDashboard[0].totalValue.toString()
            binding.invoiceSales.text = customerInvoiceDashboard[0].numberofInvoice.toString()
            binding.customersSales.text = customerInvoiceDashboard[0].numberofCustomer.toString()

            binding.allPaied.text = customerInvoiceDashboard[0].totalPaied.toString()
            binding.invoicePaied.text = customerInvoiceDashboard[0].paiedInvoiceNumber.toString()
                    binding.customersPaied.text =
                        customerInvoiceDashboard[0].paiedInvoiceCustomer.toString()

                    binding.allReminder.text = customerInvoiceDashboard[0].totalReminder.toString()
                    binding.invoiceReminder.text =
                        customerInvoiceDashboard[0].reminderInvoiceNumber.toString()
                    binding.customersReminder.text =
                        customerInvoiceDashboard[0].reminderInvoiceCustomer.toString()


        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {


            when (it) {
                1 -> {
                    context?.let { ProgressLoading.show(it) }
                }
                0 -> {
                    ProgressLoading.dismiss()
                }
            }

        })
    }

    private fun setEmpData() {
        binding.empImage?.setImageResource(R.drawable.user_logo)
        if (!viewModel.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray =
                Base64.decode(viewModel.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }

        binding.empTitle.text = viewModel.dataManager.user.title
        binding.empName.text = viewModel.dataManager.user.nameAr
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Report"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee?.dismiss()
        binding.empImage.setImageResource(R.drawable.user_logo);
        if (model?.fileImage != null) {
            Glide.with(baseActivity)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/" + model.fileImage)
                .placeholder(binding.empImage?.drawable)
                .into(binding.empImage!!)
        } else {
            Glide.with(baseActivity)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/DefaultEmpImage.jpg")
                .placeholder(binding.empImage?.drawable)
                .into(binding.empImage!!)
        }
        binding.empName.text = model?.empName
        binding.empTitle.text = model?.empTitle

        accId = model?.empAccountId.toString()

        viewModel.getSalesPurchaseReport(filterModel)


    }


}
