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
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentPlanAndCoverBinding
import com.devartlab.databinding.MrRankFragmentBinding
import com.devartlab.databinding.PlanAndCoverDetailsBinding
import com.devartlab.model.*
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.cycles.ChangeCycleAll
import com.devartlab.ui.main.ui.cycles.ChangeCycleInterface
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.devartlab.utils.RankBottomSheet
import com.devartlab.utils.SortBottomSheet
import java.util.*
import kotlin.collections.ArrayList

class PlanAndCoverDetailsActivity : BaseActivity<PlanAndCoverDetailsBinding>(), View.OnClickListener {
    lateinit var binding: PlanAndCoverDetailsBinding
    private lateinit var viewModel: RanksViewModel
    private lateinit var planAdapter: PlanAndCoverPlanDetailsAdapter
    private lateinit var reportAdapter: PlanAndCoverReportDetailsAdapter
    private lateinit var budgetAdapter: PlanAndCoverBudgetDetailsAdapter
    var cusId = 0
    var model: PlanAndCoverCustomers? = null

    var mDrawableBuilder: TextDrawable? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Customer Report"

        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)


        model = intent.getParcelableExtra("MODEL")


        planAdapter = PlanAndCoverPlanDetailsAdapter(this, ArrayList())
        reportAdapter = PlanAndCoverReportDetailsAdapter(this, ArrayList())
        budgetAdapter = PlanAndCoverBudgetDetailsAdapter(this, ArrayList())

        binding.recyclerViewPlan.adapter = planAdapter
        binding.recyclerViewReport.adapter = reportAdapter
        binding.recyclerViewBudget.adapter = budgetAdapter

        cusId = intent.getIntExtra("CUSTOMER_ID", 0)
        val accId = intent.getStringExtra("EMP_ID")
        val fromDate = intent.getStringExtra("FROM_DATE")
        val toDate = intent.getStringExtra("TO_DATE")
        val cycleId = intent.getIntExtra("CYCLE_ID", 0)

        viewModel.getPlanAndCoverReport("1", accId!!, fromDate!!, toDate!!, cycleId)


        setData()
        setListeners()
        setObservers()

    }

    private fun setData() {

        setName(model?.customerEnName , CommonUtilities.randomColor)
        binding.name.text = model?.customerEnName
        binding.address.text = model?.salTerriotryEnName
        binding.brick.text = model?.brickEnName
        binding.degree.text = model?.cusClassEnName
        binding.specialist.text = model?.cusTypeEnName
    }

    private fun setListeners() {

        binding.planLayout.setOnClickListener(this)
        binding.reportLayout.setOnClickListener(this)
        binding.budgetLayout.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.plan_and_cover_details
    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, Observer {


            val plan = ArrayList<CustomerPlanDetilas>()
            val report = ArrayList<CustomerReportDetilas>()
            val budget = ArrayList<CustomeBudgetDetilas>()

            for (m in it.data.customerPlanDetilas) {
                if (m.customerId == cusId) {
                    plan.add(m)
                }
            }

            for (m in it.data.customerReportDetilas) {
                if (m.customerId == cusId) {
                    report.add(m)
                }
            }

            for (m in it.data.customeBudgetDetilas) {
                if (m.customerId == cusId) {
                    budget.add(m)
                }
            }

            planAdapter.setMyData(plan)
            reportAdapter.setMyData(report)
            budgetAdapter.setMyData(budget)

        })


        viewModel.progress.observe(this, Observer {


            when (it) {
                1 -> ProgressLoading.show(this)

                0 -> {
                    ProgressLoading.dismiss()
                }
            }

        })
    }

    override fun onClick(p0: View) {

        when (p0.id) {


            R.id.planLayout -> {

                binding.planLayout.background = ContextCompat.getDrawable(this, R.drawable.blue_background)
                binding.reportLayout.background = ContextCompat.getDrawable(this, R.drawable.white_background)
                binding.budgetLayout.background = ContextCompat.getDrawable(this, R.drawable.white_background)

                binding.startedTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.lateTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                binding.notTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))


                binding.recyclerViewPlan.visibility = View.VISIBLE
                binding.recyclerViewBudget.visibility = View.GONE
                binding.recyclerViewReport.visibility = View.GONE
            }

            R.id.reportLayout -> {


                binding.reportLayout.background = ContextCompat.getDrawable(this, R.drawable.blue_background)
                binding.planLayout.background = ContextCompat.getDrawable(this, R.drawable.white_background)
                binding.budgetLayout.background = ContextCompat.getDrawable(this, R.drawable.white_background)

                binding.lateTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.startedTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                binding.notTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

                binding.recyclerViewPlan.visibility = View.GONE
                binding.recyclerViewBudget.visibility = View.GONE
                binding.recyclerViewReport.visibility = View.VISIBLE
            }

            R.id.budgetLayout -> {


                binding.budgetLayout.background = ContextCompat.getDrawable(this, R.drawable.blue_background)
                binding.reportLayout.background = ContextCompat.getDrawable(this, R.drawable.white_background)
                binding.planLayout.background = ContextCompat.getDrawable(this, R.drawable.white_background)

                binding.notTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.startedTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                binding.lateTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

                binding.recyclerViewPlan.visibility = View.GONE
                binding.recyclerViewBudget.visibility = View.VISIBLE
                binding.recyclerViewReport.visibility = View.GONE
            }


        }

    }

    fun setName(title: String?, color: Int) {
        var letter = "A"
        if (title != null && !title.isEmpty()) {
            letter = title.substring(0, 1)
        }
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color)
        binding.image?.setImageDrawable(mDrawableBuilder)
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
