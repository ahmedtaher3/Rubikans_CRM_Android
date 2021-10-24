package com.devartlab.ui.main.ui.callmanagement.inventory.status

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityInventoryRequestsStatusBinding
import com.devartlab.model.Summary
import com.google.gson.Gson
import androidx.lifecycle.Observer
import com.devartlab.data.retrofit.INvnetory
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.utils.ProgressLoading
import java.lang.Exception
import java.util.ArrayList


private const val TAG = "InventoryRequestsStatus"

class InventoryRequestsStatusActivity : BaseActivity<ActivityInventoryRequestsStatusBinding>(),
    View.OnClickListener,
    InventoryRequestsAdapter.OnTypeSelect,
    InventoryRequestsAdapter.OnInventoryTypeClick {

    lateinit var binding: ActivityInventoryRequestsStatusBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var recyclerViewAdapter: InventoryRequestsAdapter
    var empId = "0"
    var accId = "0"
    private lateinit var lastLayout: RelativeLayout
    private lateinit var lastTextView: TextView
    var statuesList = java.util.ArrayList<INvnetory>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.status)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        lastLayout = binding.inventoryAllTab
        lastTextView = binding.allTabTextView


        var requestObject =
            ReportsFilterModel(

                _Option = 26,
                LoginUserAccountId = viewModel.dataManager.user.accId,
                EmployeeIdStr = viewModel.dataManager.user.empId.toString(),
                AccountIdStr = viewModel.dataManager.user.accId.toString(),
                PageSize = 100,
                PageNumber = 1,
                AllowToBrowesAllRecord = true,
                StoreIdStr = "TEEEEEST",
                TrxId = 1148
            )
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject

        viewModel.getInventoryStatues(appraisalBuildsSchema)




        setListeners()
        setRecycler()
        setObservers()

    }

    private fun setListeners() {
        binding.inventoryAllTab.setOnClickListener(this)
        binding.inventoryPendingTab.setOnClickListener(this)
        binding.inventoryApprovedTab.setOnClickListener(this)
    }

    private fun setRecycler() {
        recyclerViewAdapter =
            InventoryRequestsAdapter(
                this,
                ArrayList(),
                this,
                this
            )
        binding.recyclerInventoryStatues.adapter = recyclerViewAdapter
        binding.recyclerInventoryStatues.layoutManager = LinearLayoutManager(this)
    }


    private fun setObservers() {
        viewModel.progress.observe(this, Observer<Int> {

            when (it) {
                0 -> {

                    try {
                        ProgressLoading.dismiss()
                    } catch (e: Exception) {

                    }


                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })

        viewModel.recyclerListData.observe(this, Observer<ResponseModel> {
            ProgressLoading.dismiss()
            if (it != null) {
                //update the adapter
                if (it.isSuccesed!!) {
                    statuesList = it.data?.iNvnetoryAllrequest!!
                    recyclerViewAdapter.setMyData(it.data?.iNvnetoryAllrequest!!)

                } else
                    Log.d(TAG, "getData: " + it.rerurnMessage)
            } else {
//                Toast.makeText(this@InventoryLayout, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_requests_status
    }

    override fun onInventoryTypeClick(model: INvnetory) {
        System.out.println("Selecteeeeeeeeed")
        val intent = Intent(
            this@InventoryRequestsStatusActivity,
            InventoryStatusDetailsActivity::class.java
        )
        intent.putExtra("INvnetoryMasterDatum", model)
        intent.putExtra("INvnetoryStatues", model.approved)
        startActivity(intent)

    }

    override fun setOnTypeSelect(model: Summary) {
        System.out.println("Selecteeeeeeeeed")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.inventoryAllTab -> {

                binding.inventoryAllTab.setBackgroundResource(R.drawable.rounded_primary)
                binding.allTabTextView.setTextColor(Color.parseColor("#ffffff"))

                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.inventoryAllTab!!
                lastTextView = binding.allTabTextView!!

                recyclerViewAdapter.setMyData(statuesList as java.util.ArrayList<INvnetory>)


            }
            R.id.inventoryPendingTab -> {

                binding.inventoryPendingTab.setBackgroundResource(R.drawable.rounded_primary)
                binding.pendingTabTextView.setTextColor(Color.parseColor("#ffffff"))

                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.inventoryPendingTab!!
                lastTextView = binding.pendingTabTextView!!


                var newList = java.util.ArrayList<INvnetory>()

                if (!statuesList.isNullOrEmpty()) {
                    for (model in statuesList!!) {
                        if (model.approved == false)
                            newList.add(model)
                    }

                    recyclerViewAdapter.setMyData(newList as java.util.ArrayList<INvnetory>)

                }


            }
            R.id.inventoryApprovedTab -> {

                binding.inventoryApprovedTab.setBackgroundResource(R.drawable.rounded_primary)
                binding.approvedTabTextView.setTextColor(Color.parseColor("#ffffff"))

                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.inventoryApprovedTab!!
                lastTextView = binding.approvedTabTextView!!


                var newList = java.util.ArrayList<INvnetory>()
                if (!statuesList.isNullOrEmpty()) {

                    for (model in statuesList!!) {
                        if (model.approved == true)
                            newList.add(model)
                    }

                    recyclerViewAdapter.setMyData(newList as java.util.ArrayList<INvnetory>)

                }


            }
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



