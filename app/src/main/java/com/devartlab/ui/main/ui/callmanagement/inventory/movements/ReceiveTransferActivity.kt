package com.devartlab.ui.trade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityInventoryMovesDetailsLayoutBinding
import androidx.lifecycle.Observer
import com.devartlab.R
import com.devartlab.data.retrofit.*
import com.devartlab.databinding.ActivityInventoryTransferBinding

import com.devartlab.model.Summary
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "InventoryMovesDetailsLa"

class ReceiveTransferActivity : BaseActivity<ActivityInventoryTransferBinding>(),
    InventoryMovesDetailsAdaptor.OnTypeSelect, InventoryMovesDetailsAdaptor.OnInventoryTypeClick {

    lateinit var binding: ActivityInventoryTransferBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var recyclerViewAdapter: InventoryMovesDetailsAdaptor

    var sessionId = "0"
    lateinit var da: List<INvnetoryMasterDatum>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.description)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        recyclerViewAdapter = InventoryMovesDetailsAdaptor(this, ArrayList(), this, this, sessionId)
        binding.recyclerInventoryMovesDetails.adapter = recyclerViewAdapter
        binding.recyclerInventoryMovesDetails.layoutManager = LinearLayoutManager(this)



        try {
            sessionId = intent.getStringExtra("TRX_TYPE_ID")!!

        } catch (e: Exception) {
        }


        var requestObject =
            ReportsFilterModel(
                _Option = 27,
                LoginUserAccountId = viewModel.dataManager.user.accId,
                EmployeeIdStr = viewModel.dataManager.user.empId.toString(),
                AccountIdStr = viewModel.dataManager.user.accId.toString(),
                PageSize = 100,
                PageNumber = 1,
                ToStoreIdStr = viewModel.dataManager.user.storeId.toString(),
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
            viewModel.approveInventoryRequest(iteamList)
        }
        setObserver()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_transfer

    }

    override fun onInventoryTypeClick(model: INvnetoryMasterDatum) {
        System.out.println("clicked")

        val intent = Intent(
            this@ReceiveTransferActivity,
            InventoryTransferDetailsActivity::class.java
        )
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
                this@ReceiveTransferActivity,
                it.rerurnMessage.toString(),
                Toast.LENGTH_SHORT
            ).show()

            finish()

        })


    }




}