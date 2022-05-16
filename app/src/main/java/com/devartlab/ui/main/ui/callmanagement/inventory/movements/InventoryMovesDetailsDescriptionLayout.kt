package com.devartlab.ui.trade

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityInventoryMovesDetailsDescriptionLayoutBinding
import com.devartlab.model.Summary
import com.google.gson.Gson
import androidx.lifecycle.Observer
import com.devartlab.data.retrofit.*
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.utils.ProgressLoading
import kotlinx.android.synthetic.main.activity_inventory_moves_details_description_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "InventoryMovesDetailsDe"

class InventoryMovesDetailsDescriptionLayout :
    BaseActivity<ActivityInventoryMovesDetailsDescriptionLayoutBinding>(),
    InventoryMovesDestailsDescriptionAdaptor.OnTypeSelect,
    InventoryMovesDestailsDescriptionAdaptor.OnInventoryTypeClick {

    lateinit var binding: ActivityInventoryMovesDetailsDescriptionLayoutBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var recyclerViewAdapter: InventoryMovesDestailsDescriptionAdaptor
    lateinit var da: List<INvnetoryTrxDetail>

    var empId = "0"
    var accId = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)
        binding = viewDataBinding!!

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.description)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        accId = viewModel.dataManager.user.accId.toString()
        empId = viewModel.dataManager.user.empId.toString()
        var totalValue = 0.0
        val model = intent.getParcelableExtra<INvnetoryMasterDatum>("INvnetoryMasterDatum")


        recyclerViewAdapter = InventoryMovesDestailsDescriptionAdaptor(this, ArrayList(), this)
        binding.recyclerInventoryMovesDetailsDescription.adapter = recyclerViewAdapter
        binding.recyclerInventoryMovesDetailsDescription.layoutManager = LinearLayoutManager(this)



        binding?.serial?.text = model?.trxSerial.toString()
        binding?.storeName?.text = model?.storName.toString()
        binding?.date?.text = model?.trxdate.toString()
        binding?.moveName?.text = model?.trxTypeDescription.toString()


        var requestObject =
            ReportsFilterModel(
                _Option = 20,
                LoginUserAccountId = viewModel.dataManager.user.accId,
                EmployeeIdStr = viewModel.dataManager.user.empId.toString(),
                AccountIdStr = viewModel.dataManager.user.accId.toString(), PageSize = 100,
                PageNumber = 1,
                TrxId = model?.trxID?.toInt(),
                AllowToBrowesAllRecord = true
            )
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
        viewModel.getInventoryMovesDetailsDescription(appraisalBuildsSchema)



        setObserver()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_moves_details_description_layout

    }


    override fun setOnTypeSelect(model: Summary) {
        System.out.println("Selecteeeeeeeeed")

    }


    override fun onInventoryTypeClick(model: INvnetoryTrxDetail) {
        var id = recyclerViewAdapter.getItemId(5)




    }


    private fun setObserver() {

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

        viewModel.recyclerListData.observe(this, Observer<ResponseModel> {
            da = it.data?.iNvnetoryTrxDetails!!
            recyclerViewAdapter.setMyData(it.data?.iNvnetoryTrxDetails as ArrayList<INvnetoryTrxDetail>)

        })

        viewModel.responseLiveInsert.observe(this, Observer {


            Toast.makeText(
                this@InventoryMovesDetailsDescriptionLayout,
                it.rerurnMessage.toString(),
                Toast.LENGTH_SHORT
            ).show()

            finish()

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
}

