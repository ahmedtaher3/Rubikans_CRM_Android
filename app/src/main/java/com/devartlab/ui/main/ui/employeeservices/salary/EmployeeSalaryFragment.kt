package com.devartlab.ui.main.ui.employeeservices.salary

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Base64
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.FragmentEmployeeSalaryBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.AdModel
import com.devartlab.model.SalaryItemDetails
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.devartlab.utils.ProgressLoading
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider
import java.math.RoundingMode
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmployeeSalaryFragment : BaseFragment<FragmentEmployeeSalaryBinding>(), ChooseEmployeeInterFace, SalaryDetailsAdapter.SalaryDetailsInterface {

    private lateinit var viewModel: EmployeeSalaryViewModel
    private lateinit var binding: FragmentEmployeeSalaryBinding
    lateinit var mediaSource: SimpleMediaSource
    var id: String = "0"
    lateinit var empModel: FilterDataEntity
    var currentMonth: String = "0"
    var currentyear: String = "0"
    lateinit var employeeTextView: TextView
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var salaryDetailsAdapter: SalaryDetailsAdapter
    lateinit var list: ArrayList<SalaryItemDetails>

    lateinit var chooseEmployeeInterFace: ChooseEmployeeInterFace
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
        empModel = requireArguments().getParcelable<FilterDataEntity>("EMP_MODEL") !!

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(EmployeeSalaryViewModel::class.java)
        binding.name.setText(viewModel.dataManager?.user?.userName)
        binding.position.setText(viewModel.dataManager?.user?.title)


        binding.name.setText(empModel.empName)
        binding.position.setText(empModel.empTitle)
        binding.empImage.setImageResource(R.drawable.user_logo)
        if (!empModel.fileImage.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(empModel.fileImage, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage.setImageBitmap(decodedByte)
        }


        binding.empImage.setOnClickListener(View.OnClickListener {


            chooseEmployee = ChooseEmployee(baseActivity, this@EmployeeSalaryFragment, viewModel?.dataManager!!);
            chooseEmployee.setCanceledOnTouchOutside(true);
            val window = chooseEmployee.getWindow();
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            chooseEmployee.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            chooseEmployee.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            chooseEmployee.show();

        })
        var model = AdModel()
        for (m in viewModel.dataManager!!.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.SALARY) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)
            && model.slideImages!!.equals(null)) {
            binding.constrAds.setVisibility(View.GONE)
        }
        else if (model.resourceLink.equals(null)&&model.paragraph.equals(null)
            && model.slideImages!!.equals(null)) {
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

        val dateFormatYear: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        val dateFormatMonth: DateFormat = SimpleDateFormat("MM", Locale.US)
        val date = Date()
        currentMonth = (dateFormatMonth.format(date).toInt() - 1).toString()
        currentyear = dateFormatYear.format(date).toString()


        salaryDetailsAdapter = SalaryDetailsAdapter(baseActivity, viewModel.dataManager, this)
        binding.salaryDetails.layoutManager = LinearLayoutManager(baseActivity)
        binding.salaryDetails.adapter = salaryDetailsAdapter

        list = ArrayList()

        id = viewModel.dataManager?.user?.accId.toString()
        viewModel.getSalary(id, empModel.empId.toString(), currentMonth , currentyear)


        setObservers()

    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_employee_salary
    }


    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            if (it.isSuccesed) {

                var dues = 0.0
                var deductions = 0.0
                var net = 0.0

                for (model in it.data.salaryItem) {
                    if (model.totalValue > 1) {
                        dues += model.totalValue

                    } else {
                        deductions += model.totalValue
                    }
                }

                net = dues + deductions

                binding.salaryDues.setText(dues.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString())
                binding.salaryDeduction.setText(deductions.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString())
                binding.salaryNet.setText(net.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString())


                salaryDetailsAdapter.setMyData(it.data.salaryItem)
                list = it.data.salaryItemDetails
            } else {


                binding.salaryDues.setText("0")
                binding.salaryDeduction.setText("0")
                binding.salaryNet.setText("0")
                salaryDetailsAdapter.setMyData(java.util.ArrayList())

                Toast.makeText(context, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })



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
        textView.text = (dateFormatMonth.format(date).toString().toInt() - 1).toString() + " - " + dateFormatYear.format(date).toString()


        textView.setOnClickListener(View.OnClickListener {

            // Get Current Date
            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = currentMonth.toInt()-1
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        currentMonth = (monthOfYear + 1).toString()
                        currentyear = year.toString()

                        viewModel.getSalary(id, empModel.empId.toString(), currentMonth, currentyear)


                        textView.text = currentMonth + " - " + currentyear


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        })


        return
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        empModel = model!!
        chooseEmployee.dismiss()
        binding.name.setText(model?.empName)
        binding.empImage.setImageResource(R.drawable.user_logo)
        if (model?.fileImage != null) {
            Glide.with(baseActivity)
                    .load(ApiServices.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(baseActivity)
                    .load(ApiServices.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }
        binding.position.visibility = View.GONE
        viewModel.getSalary(id, empModel.empId.toString(), currentMonth, currentyear)
        chooseEmployeeInterFace.chooseEmployee(empModel)
    }

    override fun showDetails(id: Int, value: String) {

        var model = SalaryItemDetails()
        for (it in list) {
            if (id == it.duesDeductionId) {
                model = it
                break
            }
        }

        if (model.description != null) {

            if (!TextUtils.isEmpty(model.description)) {
                val dialogBuilder = AlertDialog.Builder(baseActivity)
                // ...Irrelevant code for customizing the buttons and title
                val inflater = this.layoutInflater
                val dialogView = inflater.inflate(R.layout.salary_details, null)
                dialogBuilder.setView(dialogView)
                val dismiss = dialogView.findViewById<View>(R.id.dismiss) as TextView
                val desc = dialogView.findViewById<View>(R.id.desc) as TextView
                val descValue = dialogView.findViewById<View>(R.id.descValue) as TextView
                val notes = dialogView.findViewById<View>(R.id.notes) as TextView
                val totalValue = dialogView.findViewById<View>(R.id.totalValue) as TextView


                val alertDialog = dialogBuilder.create()
                dismiss.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
                desc.setText(model.description)
                descValue.setText(model.valueDescription)
                notes.setText(model.notes)
                totalValue.setText(value)

                alertDialog.show()

            }
        }
    }


}
