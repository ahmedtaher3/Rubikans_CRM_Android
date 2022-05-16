package com.devartlab.ui.main.ui.callmanagement.planListpermission

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.ActivityListPermissionBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.PlanDayPermissionData
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.utils.ProgressLoading
import java.util.ArrayList

class PlanPermissionActivity : BaseActivity<ActivityListPermissionBinding>(), PlanPermissionAdapter.OnShowPlanSelect, ChooseEmployeeInterFace {
    lateinit var binding: ActivityListPermissionBinding
    lateinit var adapter: PlanPermissionAdapter
    lateinit var viewModel: PermissionsViewModel
    var chooseEmployee: ChooseEmployee? = null
    var employeeModel: FilterDataEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(PermissionsViewModel::class.java)
        adapter = PlanPermissionAdapter(this, ArrayList() , this)
        binding.recyclerView.adapter = adapter


        employeeModel = intent.getParcelableExtra<FilterDataEntity>("MODEL")


        viewModel.getPlanPermissions(employeeModel?.empAccountId!!)

        binding.submit.setOnClickListener {
            viewModel.updatePlanPermissions(adapter.getMyData(), employeeModel?.empId!!)
        }

        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(this, this, viewModel.dataManager)
            chooseEmployee?.setCanceledOnTouchOutside(true)
            val window = chooseEmployee?.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee?.show()

        }
        setEmpData(employeeModel)
        setObservers()
    }

    private fun setEmpData(model: FilterDataEntity?) {


        if (model?.fileImage != null) {
            Glide.with(this)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(this)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }



        binding.empTitle.text = model?.empTitle
        binding.empName.text = model?.empName
        binding.empId.text = model?.empId.toString()
    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, Observer {
            adapter.setMyData(it.data.planDayPermissionData)

        })
        viewModel.progress.observe(this, Observer {


            when (it) {
                1 -> {
                    ProgressLoading.show(this)
                }
                0 -> {
                    ProgressLoading.dismiss()
                }
            }

        })
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_list_permission
    }

    override fun chooseEmployee(model: FilterDataEntity?) {

        employeeModel = model

        chooseEmployee?.dismiss()

        if (employeeModel?.fileImage != null) {
            Glide.with(this)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/" + employeeModel?.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(this)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }


        binding.empName.text = employeeModel?.empName
        binding.empId.text = employeeModel?.empId.toString()
        binding.empTitle.text = employeeModel?.empTitle


        viewModel.getPlanPermissions(employeeModel?.empAccountId!!)
    }

    override fun setOnShowPlanSelect(model: PlanDayPermissionData) {
       val showPlan = ShowPlan(this,  model.accountId, model.date.take(10))
        showPlan.setCanceledOnTouchOutside(true)
        val window = showPlan.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        showPlan.window?.setBackgroundDrawableResource(android.R.color.transparent)
        showPlan.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        showPlan.show()

    }
}