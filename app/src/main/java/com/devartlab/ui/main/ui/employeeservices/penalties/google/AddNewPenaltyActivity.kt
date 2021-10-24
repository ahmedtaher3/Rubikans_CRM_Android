package com.devartlab.ui.main.ui.employeeservices.penalties.google

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.ActivityAddNewPenaltyBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.employeeservices.penalties.PenaltiesViewModel
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNewPenaltyActivity : BaseActivity<ActivityAddNewPenaltyBinding>(), ChooseEmployeeInterFace {

    lateinit var binding: ActivityAddNewPenaltyBinding
    lateinit var viewModel: PenaltiesViewModel
    lateinit var simpleDateFormat: SimpleDateFormat

    var type = ""
    var reason = ""
    var empId = 0
    var empName = ""


    lateinit var PenaltyTypes: ArrayList<String>
    lateinit var PenaltyTypesIdes: ArrayList<Int>
    lateinit var ReasonTypes: ArrayList<String>
    lateinit var ReasonTypesIdes: ArrayList<Int>
    lateinit var chooseEmployee: ChooseEmployee

    override fun getLayoutId(): Int {
        return R.layout.activity_add_new_penalty
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        PenaltyTypes = ArrayList()
        PenaltyTypesIdes = ArrayList()
        ReasonTypes = ArrayList()
        ReasonTypesIdes = ArrayList()

        viewModel = ViewModelProviders.of(this).get(PenaltiesViewModel::class.java)
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' // 'hh:mm a", Locale.US)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Add New Penalty"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(this, this@AddNewPenaltyActivity, viewModel.dataManager!!)
            chooseEmployee.setCanceledOnTouchOutside(true)
            val window = chooseEmployee.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee.show()


        }

        binding.submitRequest.setOnClickListener {
            if (viewModel.dataManager?.user?.managerId == 0)
                Toast.makeText(this, "his task cannot be completed, please contact Technical Support", Toast.LENGTH_SHORT).show()
            else
                viewModel.insert(empId.toString(), empName, reason, type, binding.notes.text.toString())
        }

        setObservers()


        binding.spinnerReason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                reason = ReasonTypes[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }


        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                type = PenaltyTypes[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }




        viewModel.getPenaltiesReason()

    }

    private fun setObservers() {

        viewModel.responseLivePenaltiesType.observe(this, androidx.lifecycle.Observer {

            if (it.isSuccesed) {

                for (model in it.data.penaltiesType) {
                    PenaltyTypes.add(model.penaltyArName)
                    PenaltyTypesIdes.add(model.penaltyTypeId)
                }

                binding.spinnerType.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PenaltyTypes)

            } else {
                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.responseLivePenaltiesReason.observe(this, androidx.lifecycle.Observer {

            if (it.isSuccesed) {
                for (model in it.data.penaltiesReason) {
                    ReasonTypes.add(model.penReasonArName)
                    ReasonTypesIdes.add(model.penReasonId)
                }

                binding.spinnerReason.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ReasonTypes)

            } else {
                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                2 -> {

                    ProgressLoading.dismiss()
                    finish()
                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })

    }

    override fun chooseEmployee(model: FilterDataEntity) {
        chooseEmployee.dismiss()

        empId = model.empId!!
        empName = model.empName!!


        binding.empName.text = model.empName
        binding.empTitle.text = model.empTitle
        binding.empImage.setImageResource(R.drawable.user_logo)
        if (model?.fileImage != null) {
            Glide.with(this)
                    .load(ApiServices.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(this)
                    .load(ApiServices.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
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
}