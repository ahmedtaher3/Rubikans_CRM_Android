package com.devartlab.ui.main.ui.callmanagement.inventory.loadcar

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityInventoryMovesDetailsLayoutBinding
import androidx.lifecycle.Observer
import com.devartlab.R
import com.devartlab.data.retrofit.*

import com.devartlab.model.Summary
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.ui.trade.InventoryLoadCarAdaptor
import com.devartlab.ui.trade.InventoryLoadCarDescriptionLayout
import com.devartlab.ui.trade.InventoryTrxWarehouseTransActionDetailsModel
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson

class InventoryLoadCarActivity : BaseActivity<ActivityInventoryMovesDetailsLayoutBinding>(),
    InventoryLoadCarAdaptor.OnTypeSelect, InventoryLoadCarAdaptor.OnInventoryTypeClick {

    lateinit var binding: ActivityInventoryMovesDetailsLayoutBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var recyclerViewAdapter: InventoryLoadCarAdaptor
    var empId = "0"
    var accId = "0"
    var sessionId = "0"
    lateinit var da: List<INvnetoryMasterDatum>

    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_moves_details_layout

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.load_car_requests)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        accId = viewModel.dataManager.user.accId.toString()
        empId = viewModel.dataManager.user.empId.toString()

        recyclerViewAdapter = InventoryLoadCarAdaptor(this, ArrayList(), this, this, sessionId)
        binding.recyclerInventoryMovesDetails.adapter = recyclerViewAdapter
        binding.recyclerInventoryMovesDetails.layoutManager = LinearLayoutManager(this)

        binding.save.setVisibility(View.VISIBLE);


        var requestObject =
            ReportsFilterModel(
                _Option = 19,
                LoginUserAccountId = viewModel.dataManager.user.accId,
                EmployeeIdStr = viewModel.dataManager.user.empId.toString(),
                AccountIdStr = viewModel.dataManager.user.accId.toString(),
                StoreIdStr = viewModel.dataManager.user.storeId.toString(),
                PageSize = 100,
                PageNumber = 1,
                AllowToBrowesAllRecord = false
            )
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
        viewModel.getInventoryMovesDetails(appraisalBuildsSchema)






        binding.save.setOnClickListener {
            var iteamList = java.util.ArrayList<InventoryTrxWarehouseTransActionDetailsModel>()

            for (itea in da) {
                if (itea.selected == true) {

                    var iteam = InventoryTrxWarehouseTransActionDetailsModel(

                        TrxId = itea.trxID?.toInt()

                    )
                    iteamList.add(iteam)

                }
            }
            val appraisalBuildsSchema = Gson().toJsonTree(iteamList).asJsonArray
            System.out.println("llllllll")
            System.out.println(appraisalBuildsSchema)
            System.out.println("llllllll")

            viewModel.approveInventoryRequest(iteamList)
        }

        setObserver()

    }


    override fun onInventoryTypeClick(model: INvnetoryMasterDatum) {
        System.out.println("clicked")

        val intent =
            Intent(this@InventoryLoadCarActivity, InventoryLoadCarDescriptionLayout::class.java)
        intent.putExtra("INvnetoryMasterDatum", model)
        intent.putExtra("TRX_TYPE_ID", sessionId)


        System.out.println(da[0].selected)

        startActivity(intent)


    }


    override fun setOnTypeSelect(model: Summary) {
        System.out.println("selecteeeeeeeeeeed")
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
            recyclerViewAdapter.setMyData(it.data?.iNvnetoryMasterData as ArrayList<INvnetoryMasterDatum>)
            da = it.data?.iNvnetoryMasterData!!
        })

        viewModel.responseLiveInsert.observe(this, Observer {


            Toast.makeText(
                this@InventoryLoadCarActivity,
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