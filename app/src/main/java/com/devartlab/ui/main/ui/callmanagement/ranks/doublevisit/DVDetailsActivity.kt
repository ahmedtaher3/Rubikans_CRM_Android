package com.devartlab.ui.main.ui.callmanagement.ranks.doublevisit

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.ActivityDoubleVisitReportDetailsBinding
import com.devartlab.model.DoubleVisitReport
import com.devartlab.model.DoubleVisitReportDetails
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.utils.ProgressLoading

class DVDetailsActivity : BaseActivity<ActivityDoubleVisitReportDetailsBinding>(),
    DVDetailsAdapter.OnItemSelect {

    lateinit var binding: ActivityDoubleVisitReportDetailsBinding
    lateinit var viewModel: RanksViewModel
    lateinit var adapter: DVDetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        adapter = DVDetailsAdapter(this, ArrayList(), this)
        binding.recyclerView.adapter = adapter

        val model = intent.getParcelableExtra<DoubleVisitReport>("DoubleVisitReport")


        System.out.println(intent.getStringExtra("FromDate") + "\n" + intent.getStringExtra("ToDate") + "\n" + model?.employeeId.toString())
        viewModel.getDVDetails(
            intent.getStringExtra("FromDate")!!,
            intent.getStringExtra("ToDate")!!,
            model?.employeeId.toString()
        )


        binding.empName.text = model?.employeeEMpName
        binding.managerName.text = model?.manger



        if (model?.employeeImage != null) {
            Glide.with(this)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/" + model?.employeeImage)
                .placeholder(binding.empImage.drawable).into(binding.empImage)
        } else {
            Glide.with(this)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/DefaultEmpImage.jpg")
                .placeholder(binding.empImage.drawable).into(binding.empImage)
        }



        if (model?.managerImage != null) {
            Glide.with(this)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/" + model?.managerImage)
                .placeholder(binding.empImage.drawable).into(binding.managerImage)
        } else {
            Glide.with(this)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/DefaultEmpImage.jpg")
                .placeholder(binding.empImage.drawable).into(binding.managerImage)
        }






        setObservers()
    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, Observer {


            adapter.setMyData(it.data.doubleVisitReportDetails)

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
        return R.layout.activity_double_visit_report_details
    }

    override fun setOnItemSelect(model: DoubleVisitReportDetails) {


    }

}