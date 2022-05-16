package com.devartlab.ui.main.ui.market.details

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityMarketRequestDetailsBinding
import com.devartlab.model.RequestCostItem
import com.devartlab.ui.main.ui.market.MarketRequestViewModel
import com.devartlab.utils.ProgressLoading

class MarketRequestDetailsActivity : BaseActivity<ActivityMarketRequestDetailsBinding>() ,
    RequestCostAdapter.OnCostItemClick {


    lateinit var binding: ActivityMarketRequestDetailsBinding
    lateinit var viewModel: MarketRequestViewModel

    lateinit var requestApproveAdapter: RequestApproveAdapter
    lateinit var requestCostAdapter: RequestCostAdapter
    lateinit var requestCustomersAdapter: RequestCustomersAdapter
    lateinit var requestGainAdapter: RequestGainAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_market_request_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(MarketRequestViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        requestApproveAdapter = RequestApproveAdapter(this, ArrayList())
        requestCostAdapter = RequestCostAdapter(this, ArrayList() , this)
        requestCustomersAdapter = RequestCustomersAdapter(this, ArrayList())
        requestGainAdapter = RequestGainAdapter(this, ArrayList())
        setObservers()
        setRecyclerViews()

        viewModel.getRequestDetails(intent.getIntExtra("REQUEST_DOC_ID", 0), intent.getIntExtra("REQUEST_FLOW_ID", 0))
    }


    private fun setRecyclerViews() {

        binding.requestRequestApprovedBy?.layoutManager = LinearLayoutManager(this)
        binding.requestRequestApprovedBy?.adapter = requestApproveAdapter

        binding.requestRequestCostItems?.layoutManager = LinearLayoutManager(this)
        binding.requestRequestCostItems?.adapter = requestCostAdapter

        binding.requestRequestGainDetails?.layoutManager = LinearLayoutManager(this)
        binding.requestRequestGainDetails?.adapter = requestGainAdapter

        binding.requestRequestCustomer?.layoutManager = LinearLayoutManager(this)
        binding.requestRequestCustomer?.adapter = requestCustomersAdapter

    }

    private fun setObservers() {
        viewModel.responseLiveDetails.observe(this, androidx.lifecycle.Observer {


            if (it.isSuccesed) {


                requestApproveAdapter.setMyData(it.data.requestApprovedBy)
                requestCostAdapter.setMyData(it.data.requestCostItems)
                requestCustomersAdapter.setMyData(it.data.requestCustomer)
                requestGainAdapter.setMyData(it.data.requestGainDetails)


                binding.desc.text = it.data.requestPrincipalData[0].reqDescription
                binding.giftArea.text = it.data.requestPrincipalData[0].placeGift

                binding.totalCost.text = it.data.requestPrincipalData[0].totalCoast.toString()
                binding.totalGain.text = it.data.requestPrincipalData[0].totalPerPeriod.toString()
                binding.gainFrom.text = it.data.requestPrincipalData[0].incomePeriodFrom.toString().take(10)
                binding.gainTo.text = it.data.requestPrincipalData[0].incomePeriodTo.toString().take(10)


                binding.addedUser.text = it.data.requestPrincipalData[0].markReqApplicantName
                binding.area.text = it.data.requestPrincipalData[0].requestTerriotry
                binding.speciality.text = it.data.requestPrincipalData[0].requestSpeciality


                for (model in it.data.requestGainPharmcy) {

                    binding.pharmaciesReport.append(model.customerArName.replace("\r\n", "") + ",\n")

                }

                for (model in it.data.requestGainDetails) {

                    binding.gainReport.append(model.itemEnName?.replace("\r\n", "") + ",\n")

                }


                binding.requestDate.text = it.data.requestPrincipalData[0].markReqDate?.take(10)


                binding.requestType.text = intent.getStringExtra("REQUEST_TYPE_NAME")
                binding.requestCode.text = it.data.requestPrincipalData[0].markReqCode


            } else {

                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })


        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })

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

    override fun setOnCostItemClick(model: RequestCostItem) {
        val dialogBuilder = AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.cost_item_details, null)
        dialogBuilder.setView(dialogView)
        val dismiss = dialogView.findViewById<View>(R.id.dismiss) as TextView
        val message = dialogView.findViewById<View>(R.id.message) as TextView
        val title = dialogView.findViewById<View>(R.id.title) as TextView



        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dismiss.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
        message.setText(model.description)
        title.setText(model.coastItemArName)


        alertDialog.show()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}