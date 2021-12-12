package com.devartlab.ui.main.ui.employeeservices.attendance

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentAttendanceBinding
import com.devartlab.model.EMployeeDayDetails_class
import com.devartlab.model.EMployeeDayList_Class
import com.devartlab.model.EmployeeData_class
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.AdModel
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.employeeservices.attendance.daydetails.DayDetailsDailog
import com.devartlab.ui.main.ui.employeeservices.expenses.add.AddVacationDialog
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class AttendanceFragment : BaseFragment<FragmentAttendanceBinding>(), ChooseEmployeeInterFace, ChangeDoctorData, AttendanceAdapter.OnDayClick {

    private lateinit var viewModel: AttendanceViewModel
    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var adapter: AttendanceAdapter
    private lateinit var empModel: FilterDataEntity
    lateinit var dialog: AddVacationDialog
    lateinit var mediaSource: SimpleMediaSource
    lateinit var dayDetailsDailog: DayDetailsDailog
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var dayDetailsList: ArrayList<EMployeeDayDetails_class>
    lateinit var fullList: ArrayList<EMployeeDayList_Class>
    lateinit var fakeList: ArrayList<EMployeeDayList_Class>
    lateinit var currentMonth: String
    lateinit var currentyear: String
    lateinit var chooseEmployeeInterFace: ChooseEmployeeInterFace


    override fun getLayoutId(): Int {
        return R.layout.fragment_attendance
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            chooseEmployeeInterFace = context as ChooseEmployeeInterFace
        } catch (castException: ClassCastException) {
            /** The activity does not implement the listener.  */
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
        dayDetailsList = ArrayList()
        fullList = ArrayList()
        fakeList = ArrayList()
        fakeList.add(EMployeeDayList_Class())

        empModel = requireArguments().getParcelable<FilterDataEntity>("EMP_MODEL") !!

        val dateFormatYear: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        val dateFormatMonth: DateFormat = SimpleDateFormat("MM", Locale.US)
        val date = Date()
        currentMonth = dateFormatMonth.format(date).toString()
        currentyear = dateFormatYear.format(date).toString()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        binding = viewDataBinding





        viewModel.getAttendance(empModel.empId.toString(), currentMonth, currentyear)


        setupRecyclerView()
        setObservers()

        var model = AdModel()
        for (m in viewModel.dataManager!!.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.ATTENDANCE) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)) {
            binding.constrAds.setVisibility(View.GONE)
        }
        else if (model.resourceLink.equals(null)&&model.paragraph.equals(null)) {
            binding.imageView.visibility = View.VISIBLE
            Glide.with(this).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
        }
        if (!model.webPageLink.equals("")) {
            binding.cardviewAds.setOnClickListener {
                openWebPage(model.webPageLink)
            }
        }
        when (model.type) {
            "Video" -> {
                binding.videoView.visibility = View.VISIBLE
                mediaSource = SimpleMediaSource(model.resourceLink)
                binding.videoView.play(mediaSource);
            }
            "Image" -> {

                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
            }
            "GIF" -> {
                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).asGif().load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView);
            }
            "Paragraph" -> {
                binding.textView.visibility = View.VISIBLE
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    binding.textView.setText(
//                        Html.fromHtml(
//                            model.paragraph,
//                            Html.FROM_HTML_MODE_LEGACY
//                        )
//                    );
//                } else
//                    binding.textView.setText(Html.fromHtml(model.paragraph))
                binding.textView.loadDataWithBaseURL(null, model.paragraph!!
                    ,  "text/html", "utf-8", null)
            }
            "Slider" -> {
                binding.bannerSlider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(context))
                binding.bannerSlider?.setInterval(5000)

                val list = ArrayList<String>()
                for (i in model.slideImages!!) {
                    list.add(i?.link!!)
                }
                binding.bannerSlider?.setAdapter(MainSliderAdapter(list))
            }
        }
        if (model.show_ad == true) {
            binding.btnHideShowAds.setVisibility(View.VISIBLE)
            binding.btnHideShowAds.setOnClickListener {
                if (binding.constrAds.visibility == View.VISIBLE) {
                    binding.constrAds.setVisibility(View.GONE)
                    binding.btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
                } else {
                    binding.constrAds.setVisibility(View.VISIBLE)
                    binding.btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
                }
            }
        }
        if (model.show_more == true) {
            binding.tvMoreThanAds.setVisibility(View.VISIBLE)
            binding.tvMoreThanAds.setOnClickListener {
                val  intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = AttendanceAdapter(baseActivity, this, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseActivity)
        binding.recyclerView.adapter = adapter
        adapter.setdrData(

                EmployeeData_class(
                        empModel.empId!!,
                        empModel.empTitle,
                        empModel.empTitle,
                        empModel.empName,
                        "",
                        "0.0 day deduction",
                        0
                ) , empModel)

        adapter.setMyData(fakeList)


    }

    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            if (it.isSuccesed) {
                adapter.setdrData(it.data.employeeData[0] , empModel)
                dayDetailsList = it.data.eMployeeDayDetails
                fullList = it.data.eMployeeDayList_Class
                fullList.add(0, EMployeeDayList_Class())
                if (fullList.isNullOrEmpty()) {
                    adapter.setMyData(fakeList)
                    binding.emptyList.visibility = View.VISIBLE
                } else {
                    adapter.setMyData(fullList)
                    binding.emptyList.visibility = View.GONE
                }
            } else {
                binding.emptyList.visibility = View.VISIBLE
                adapter.setMyData(fakeList)
                Toast.makeText(context, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })

        /*viewModel.responseLiveDelete.observe(this, androidx.lifecycle.Observer {
            if (it.get(0).status)
                viewModel.getPenalties(empId, currentMonth, currentyear)
            else
                Toast.makeText(baseActivity, it.get(0).message, Toast.LENGTH_SHORT).show()

        })
*/
        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.penalties_menu, menu)
        val item = menu?.findItem(R.id.dateTextView)

        val textView = item?.actionView as TextView
        textView.textSize = 20F
        textView.setTextColor(Color.parseColor("#ffffff"))
        textView.setPadding(10, 5, 10, 5)
        val outValue = TypedValue()
        requireContext().theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
        textView.setBackgroundResource(outValue.resourceId)

        val dateFormatYear: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        val dateFormatMonth: DateFormat = SimpleDateFormat("MM", Locale.US)
        val date = Date()
        textView.text = dateFormatMonth.format(date).toString() + " - " + dateFormatYear.format(date).toString()
        textView.setOnClickListener(View.OnClickListener {

            // Get Current Date
            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        currentMonth = (monthOfYear + 1).toString()
                        currentyear = year.toString()

                        viewModel.getAttendance(empModel.empId.toString(), currentMonth, currentyear)

                        textView.text = currentMonth + " - " + currentyear


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        })



        return
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee.dismiss()
        empModel = model!!
        viewModel.getAttendance(empModel.empId.toString(), currentMonth, currentyear)
        adapter.setdrData(

                EmployeeData_class(
                        empModel.empId!!,
                        empModel.empTitle,
                        empModel.empTitle,
                        empModel.empName,
                        "",
                        "0.0 day deduction",
                        0
                )
         , empModel)

        chooseEmployeeInterFace.chooseEmployee(model)
    }

    override fun changeDrData() {
        chooseEmployee = ChooseEmployee(baseActivity, this@AttendanceFragment, viewModel?.dataManager!!);
        chooseEmployee.setCanceledOnTouchOutside(true);
        val window = chooseEmployee.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        chooseEmployee.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseEmployee.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseEmployee.show();
    }

    override fun showInfo() {

        shodetailsofcolors()
    }

    fun shodetailsofcolors() {
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.about_color_layout, null)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()

        dialogView.findViewById<ImageView>(R.id.exit_icona).setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
        alertDialog.show()
    }

    override fun showDetails(rowIndex: Int) {

        if (rowIndex == -1) {
            dayDetailsDailog = DayDetailsDailog(baseActivity, dayDetailsList);
            dayDetailsDailog.setCanceledOnTouchOutside(true);
            val window = dayDetailsDailog.getWindow();
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dayDetailsDailog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            dayDetailsDailog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dayDetailsDailog.show();


        } else {

            val new_list = ArrayList<EMployeeDayDetails_class>()

            for (model in dayDetailsList) {
                if (model.dayROwIndex == rowIndex) {
                    new_list.add(model)
                }
            }

            if (!new_list.isNullOrEmpty()) {
                dayDetailsDailog = DayDetailsDailog(baseActivity, new_list);
                dayDetailsDailog.setCanceledOnTouchOutside(true);
                val window = dayDetailsDailog.getWindow();
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dayDetailsDailog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                dayDetailsDailog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dayDetailsDailog.show();
            }

        }


    }
}