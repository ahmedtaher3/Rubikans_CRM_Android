package com.devartlab.ui.main.ui.callmanagement.ranks.planandcover

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.FragmentPlanAndCoverBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.PlanAndCoverCustomers
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.cycles.ChangeCycleAll
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.devartlab.utils.SortBottomSheet
import java.util.*
import kotlin.collections.ArrayList

class PlanAndCoverFragment : BaseFragment<FragmentPlanAndCoverBinding>(), PlanAndCoverAdapter.OnItemSelect, ChooseEmployeeInterFace {
    private lateinit var viewModel: RanksViewModel
    private lateinit var binding: FragmentPlanAndCoverBinding
    private lateinit var adapter: PlanAndCoverAdapter
    var chooseEmployee: ChooseEmployee? = null
    var changeCycle: ChangeCycleAll? = null
    var fromDate = ""
    var toDate = ""
    var cycleId = 0
    var accId = "0"
    var fullList: ArrayList<PlanAndCoverCustomers>? = null
    var flag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        adapter = PlanAndCoverAdapter(baseActivity, ArrayList(), this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_plan_and_cover
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        binding.recyclerView.adapter = adapter

        fromDate = viewModel.dataManager.newOldCycle.currentCycleFromDate?.take(10)!!
        toDate = viewModel.dataManager.newOldCycle.currentCycleToDate?.take(10)!!

        binding.from.text = fromDate
        binding.to.text = toDate


        cycleId = viewModel.dataManager.newOldCycle.currentCycleId
        accId = viewModel.dataManager.user.empId.toString()

        viewModel.getPlanAndCoverReport("1", accId, fromDate, toDate, cycleId)


        binding.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                adapter.filter(charSequence.toString())

            }

            override fun afterTextChanged(editable: Editable) {}
        })


        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
            chooseEmployee?.setCanceledOnTouchOutside(true)
            val window = chooseEmployee?.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee?.show()

        }


        binding.from.setOnClickListener {

            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        fromDate = year.toString() + "-" + CommonUtilities.checkTwoDigits((monthOfYear + 1).toString()) + "-" + CommonUtilities.checkTwoDigits(dayOfMonth.toString())
                        binding.from.text = fromDate
                        viewModel.getPlanAndCoverReport("1", accId, fromDate, toDate, cycleId)


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        }

        binding.to.setOnClickListener {

            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        toDate = year.toString() + "-" + CommonUtilities.checkTwoDigits((monthOfYear + 1).toString()) + "-" + CommonUtilities.checkTwoDigits(dayOfMonth.toString())
                        binding.to.text = toDate
                        viewModel.getPlanAndCoverReport("1", accId, fromDate, toDate, cycleId)


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        }

        binding.filter.setOnClickListener {

            val bottomDialogFragment =
                    SortBottomSheet(object : SortBottomSheet.DialogListener {
                        override fun applyFilter(isLatest: Int) {
                            flag = isLatest

                            when (flag) {
                                0 -> {
                                    adapter.setMyData(fullList!!)
                                }
                                1 -> {
                                    val list = ArrayList<PlanAndCoverCustomers>()

                                    for (m in fullList!!) {

                                        if (m.planCounter == 0) {
                                            list.add(m)
                                        }
                                    }
                                    adapter.setMyData(list!!)

                                }
                                2 -> {
                                    val list = ArrayList<PlanAndCoverCustomers>()

                                    for (m in fullList!!) {

                                        if (m.coverCounter == 0) {
                                            list.add(m)
                                        }
                                    }
                                    adapter.setMyData(list!!)
                                }
                            }
                        }

                    }, flag)

            bottomDialogFragment.show(
                    baseActivity.supportFragmentManager,
                    "bottomDialogFragment"
            )

        }

        setEmpData()
        setObservers()
    }

    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, Observer {

            fullList = it.data.planAndCoverCustomers
            adapter.setMyData(fullList!!)

        })


        viewModel.progress.observe(viewLifecycleOwner, Observer {


            when (it) {
                1 -> {
                    context?.let { it1 -> ProgressLoading.show(it1) }
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
            val decodedString: ByteArray = Base64.decode(viewModel.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }

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




        if (model?.fileImage != null)
        {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }
        else

        {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }




        binding.empName.text = model?.empName


        accId = model?.empId.toString()

        viewModel.getPlanAndCoverReport("1", accId, fromDate, toDate, cycleId)


    }


    override fun setOnItemSelect(model: PlanAndCoverCustomers) {


        val intent = Intent(baseActivity, PlanAndCoverDetailsActivity::class.java)

        intent.putExtra("CUSTOMER_ID", model.customerId)
        intent.putExtra("EMP_ID", accId)
        intent.putExtra("FROM_DATE", fromDate)
        intent.putExtra("TO_DATE", toDate)
        intent.putExtra("CYCLE_ID", cycleId)
        intent.putExtra("MODEL", model)

        startActivity(intent)


    }


}
