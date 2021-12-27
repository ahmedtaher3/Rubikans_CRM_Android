package com.devartlab.ui.main.ui.callmanagement.ranks

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.databinding.StartPointReportFragmentBinding
import com.devartlab.model.*
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.cycles.ChangeCycle
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StartPointReportFragment : BaseFragment<StartPointReportFragmentBinding>(), AdapterView.OnItemSelectedListener, StartPointReportAdapter.OnItemSelect, View.OnClickListener, ChooseEmployeeInterFace, StartPointInterface {
    private lateinit var viewModel: RanksViewModel
    private lateinit var binding: StartPointReportFragmentBinding
    private lateinit var adapterStart: StartPointReportAdapter
    private lateinit var adapterLate: StartPointReportAdapter
    private lateinit var adapterNot: StartPointReportAdapter

    var titleList: ArrayList<StartPointReportTitle>? = null
    var fullList = ArrayList<StartPointReport>()
    val listStart = java.util.ArrayList<StartPointReport>()
    val listLate = java.util.ArrayList<StartPointReport>()
    val listNot = java.util.ArrayList<StartPointReport>()

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
        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        adapterStart = StartPointReportAdapter(baseActivity, ArrayList(), this)
        adapterLate = StartPointReportAdapter(baseActivity, ArrayList(), this)
        adapterNot = StartPointReportAdapter(baseActivity, ArrayList(), this)


        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)
    }

    override fun getLayoutId(): Int {
        return R.layout.start_point_report_fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding

        accId = viewModel.dataManager.user.accId.toString()

        binding.recyclerViewStarted.adapter = adapterStart
        binding.recyclerViewNot.adapter = adapterNot
        binding.recyclerViewLate.adapter = adapterLate

        //viewModel.getStartPointReport(accId, DATE!!, ShiftID)

        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
            chooseEmployee?.setCanceledOnTouchOutside(true)
            val window = chooseEmployee?.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee?.show()

        }

        binding.filter.setOnClickListener {

            val startPointFilter = StartPointFilter(baseActivity, titleList!!, this)
            startPointFilter.setCanceledOnTouchOutside(true)
            val window = startPointFilter.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            startPointFilter.window?.setBackgroundDrawableResource(android.R.color.transparent)
            startPointFilter.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            startPointFilter.show()

        }
        binding.day.setOnClickListener {


            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        mYear = year
                        mMonth = monthOfYear
                        mDay = dayOfMonth

                        DATE = year.toString() + "-" + CommonUtilities.checkTwoDigits((monthOfYear + 1).toString()) + "-" + CommonUtilities.checkTwoDigits(dayOfMonth.toString())

                        binding.day.text = DATE

                        viewModel.getStartPointReport(accId, DATE!!, ShiftID)

                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        }


        val adapter = ArrayAdapter.createFromResource(baseActivity, R.array.spinner_list_item_array, R.layout.spinner_item_black)
        adapter.setDropDownViewResource(R.layout.spinner_item_black)
        binding.shiftSpinner.adapter = adapter
        binding.shiftSpinner.onItemSelectedListener = this






        setEmpData()
        setListeners()
        setObservers()

    }

    private fun setListeners() {

        binding.startedLayout.setOnClickListener(this)
        binding.lateLayout.setOnClickListener(this)
        binding.notLayout.setOnClickListener(this)
    }


    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, Observer {

            System.out.println(it.toString())



            titleList = it.data.startPointReportTitle

            fullList.clear()
            listStart.clear()
            listLate.clear()
            listNot.clear()

            for (m in it.data.startPointReport) {
                if (!m.empArName.equals("Vacant"))
                    fullList.add(m)
            }



            for (model in fullList!!) {
                if (!model.empArName.equals("Vacant")) {
                    try {
                        model.address = getAddress(model.startLat?.toDouble()!!, model.startLang?.toDouble()!!)

                    } catch (e: java.lang.Exception) {

                    }


                    if (!model.planStartTime.isNullOrEmpty() && !model.reportStartTime.isNullOrEmpty()) {

                        if (CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt() > 20) {
                            listLate.add(model)
                        } else if (CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt() >= 0) {
                            listStart.add(model)
                        } else if (CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt() < 0) {
                            listStart.add(model)
                        }

                    } else if (model.planStartTime.isNullOrEmpty() && !model.reportStartTime.isNullOrEmpty()) {
                        listLate.add(model)
                    } else if (model.planStartTime.isNullOrEmpty() && model.reportStartTime.isNullOrEmpty()) {
                        listNot.add(model)
                    }


                }

            }

            adapterStart.setMyData(listStart)
            adapterLate.setMyData(listLate)
            adapterNot.setMyData(listNot)
            try {
                ProgressLoading.dismiss()
            } catch (e: java.lang.Exception) {
            }

        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {


            when (it) {
                1 -> {
                    context?.let { it1 -> ProgressLoading.show(it1) }
                }
                0 -> {

                }
            }

        })
    }

    private fun setEmpData() {
        binding.empImage?.setImageResource(R.drawable.user_logo)
        if (!viewModel.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(viewModel.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }

        binding.empTitle.text = viewModel.dataManager.user.title
        binding.empName.text = viewModel.dataManager.user.nameAr
        binding.empId.text = viewModel.dataManager.user.empId.toString()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Report"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }


    override fun setOnItemSelect(model: StartPointReport) {

    }

    fun getAddress(latitude: Double, longitude: Double): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(activity, Locale("ar"))
        var address = ""
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            //  throw RuntimeException(e.message)
        }
        return address
    }



    override fun onClick(p0: View) {

        when (p0.id) {


            R.id.startedLayout -> {

                binding.startedLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.blue_background)
                binding.lateLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.white_background)
                binding.notLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.white_background)

                binding.startedTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.white))
                binding.lateTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))
                binding.notTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))


                binding.recyclerViewStarted.visibility = View.VISIBLE
                binding.recyclerViewNot.visibility = View.GONE
                binding.recyclerViewLate.visibility = View.GONE
            }

            R.id.lateLayout -> {


                binding.lateLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.blue_background)
                binding.startedLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.white_background)
                binding.notLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.white_background)

                binding.lateTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.white))
                binding.startedTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))
                binding.notTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))

                binding.recyclerViewStarted.visibility = View.GONE
                binding.recyclerViewNot.visibility = View.GONE
                binding.recyclerViewLate.visibility = View.VISIBLE
            }

            R.id.notLayout -> {


                binding.notLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.blue_background)
                binding.startedLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.white_background)
                binding.lateLayout.background = ContextCompat.getDrawable(baseActivity, R.drawable.white_background)

                binding.notTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.white))
                binding.startedTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))
                binding.lateTextView.setTextColor(ContextCompat.getColor(baseActivity, R.color.colorPrimary))

                binding.recyclerViewStarted.visibility = View.GONE
                binding.recyclerViewNot.visibility = View.VISIBLE
                binding.recyclerViewLate.visibility = View.GONE
            }


        }

    }


    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee?.dismiss()
        binding.empImage.setImageResource(R.drawable.user_logo);
        if (model?.fileImage != null)
        {
            Glide.with(baseActivity)
                    .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }
        else

        {
            Glide.with(baseActivity)
                    .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }
        binding.empName.text = model?.empName
        binding.empId.text = model?.empId.toString()
        binding.empTitle.text = model?.empTitle

        accId = model?.empAccountId.toString()

        viewModel.getStartPointReport(accId, DATE!!, ShiftID)


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var x = getResources().getStringArray(R.array.spinner_list_item_array)

        Shift = x[p2]

        if (x[p2].equals("AM Shift")) {
            ShiftID = "8"
        } else {
            ShiftID = "9"
        }



        viewModel.getStartPointReport(accId, DATE!!, ShiftID)
    }

    override fun onTitleSelect(model: StartPointReportTitle) {
        listStart.clear()
        listLate.clear()
        listNot.clear()
        val list = ArrayList<StartPointReport>()

        for (m in fullList) {
            if (m.jobId?.toInt() == model.jobId)
                list.add(m)
        }

        for (model in list!!) {
            if (!model.empArName.equals("Vacant")) {
                try {
                    model.address = getAddress(model.startLat?.toDouble()!!, model.startLang?.toDouble()!!)

                } catch (e: java.lang.Exception) {

                }

                if (model.startPointStatus == 1) {
                    listStart.add(model)
                } else if (model.startPointStatus == 2) {
                    listLate.add(model)

                } else if (model.startPointStatus == 3) {
                    listNot.add(model)

                }

            }

        }

        adapterStart.setMyData(listStart)
        adapterLate.setMyData(listLate)
        adapterNot.setMyData(listNot)

    }


}
