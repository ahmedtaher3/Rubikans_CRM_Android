package com.devartlab.ui.main.ui.callmanagement.ranks.medicalriprank

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.ActivityMedicalReportBinding
import com.devartlab.model.MrReportDetails
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.utils.ProgressLoading
import org.eazegraph.lib.models.BarModel

class MedicalRepReportActivity : BaseActivity<ActivityMedicalReportBinding>() {

    lateinit var binding: ActivityMedicalReportBinding
    private lateinit var viewModel: RanksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Rank Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)

        binding.drName.text = intent.getStringExtra("NAME")

        if (intent.getStringExtra("IMAGE") != null) {
            Glide.with(this)
                    .load(viewModel.dataManager.url + "ImageUpload/Employee/" + intent.getStringExtra("IMAGE"))
                    .placeholder(binding?.empImage?.drawable)
                    .into(binding?.empImage!!)
        } else {
            Glide.with(this)
                    .load(viewModel.dataManager.url + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding?.empImage?.drawable)
                    .into(binding?.empImage!!)
        }


        viewModel.getMRRankReport(intent.getIntExtra("ID", 0).toString(), intent.getStringExtra("FROM_DATE")!!, intent.getStringExtra("TO_DATE")!!, 2, intent.getIntExtra("CYCLE_ID", 0), "1,11", "", intent.getIntExtra("MEDICAL_REP_ID", 0))

        setObservers()

    }

    private fun setObservers() {

        viewModel.responseLiveDetails.observe(this, Observer {


            if (!it.data.mrRanksReports.isNullOrEmpty()) {

                val model = it.data.mrRanksReports[0]


                System.out.println(model.toString())
                try {
                    binding.mBarChart.addBar(BarModel("Planned", model.tOtalPlanned.toFloat(), 0xFF123456.toInt()))
                    binding.mBarChart.addBar(BarModel("Actual", model.vistiedTotal.toFloat(), 0xFF123456.toInt()))
                    binding.mBarChart.addBar(BarModel("Confirmed", model.visitedconfirmed.toFloat(), 0xFF123456.toInt()))
                    binding.mBarChart.addBar(BarModel("Extra", model.vistiedExtra.toFloat(), 0xFF123456.toInt()))
                    binding.mBarChart.startAnimation()
                }
                catch (e:Exception)
                {

                }



                val list = ArrayList<MrReportDetails>()
                val unCover = ArrayList<MrReportDetails>()

                list.add(MrReportDetails("Plan / List", model.tOtalPlanned.toString() + " / " + model.totalList.toString()))
                unCover.add(MrReportDetails("UnCover / List", model.tOtalUnCoverd.toString() + " / " + model.totalList.toString()))

                binding.list.setProgress(((model.tOtalPlanned.toFloat() / model.totalList.toFloat()) * 100).toInt().toFloat()!!, true)
                binding.unCover.setProgress(((model.tOtalUnCoverd.toFloat() / model.totalList.toFloat()) * 100).toInt().toFloat()!!, true)


                for (m in it.data.mrRanksReports) {
                    list.add(MrReportDetails(model.listClassName, model.totalClass.toString()))
                }


                val adapter = MRReportDetailsAdapter(this, list)
                val adapterUnCover = MRReportDetailsAdapter(this, unCover)

                binding.listRecyclerView.adapter = adapter
                binding.listRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

                binding.unCoverRecyclerView.adapter = adapterUnCover
                binding.unCoverRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            } else {
                Toast.makeText(this@MedicalRepReportActivity, "No data found", Toast.LENGTH_SHORT).show()

            }


        })



        viewModel.progress.observe(this, Observer {
            when (it) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_medical_report
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