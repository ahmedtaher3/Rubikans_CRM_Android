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
import com.devartlab.databinding.ActivityInventoryLoadCarDetailsBinding
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.utils.ProgressLoading
import kotlinx.android.synthetic.main.activity_inventory_moves_details_description_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "InventoryLoadCarDescrip"

class InventoryLoadCarDescriptionLayout :
    BaseActivity<ActivityInventoryLoadCarDetailsBinding>(),
    InventoryLoadCarDescriptionAdaptor.OnTypeSelect,
    InventoryLoadCarDescriptionAdaptor.OnInventoryTypeClick {

    lateinit var binding: ActivityInventoryLoadCarDetailsBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var recyclerViewAdapter: InventoryLoadCarDescriptionAdaptor
    lateinit var da: List<INvnetoryTrxDetail>
    var sessionId = "0"
    var empId = "0"
    var accId = "0"
    var model = INvnetoryMasterDatum()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)
        binding = viewDataBinding


        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.description)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        accId = viewModel.dataManager.user.accId.toString()
        empId = viewModel.dataManager.user.empId.toString()
        var totalValue = 0.0


        model = intent.getParcelableExtra("INvnetoryMasterDatum")!!
        sessionId = intent.getStringExtra("TRX_TYPE_ID")!!


        recyclerViewAdapter =
            InventoryLoadCarDescriptionAdaptor(this, ArrayList(), this, this, sessionId)
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
                AccountIdStr = viewModel.dataManager.user.accId.toString(),
                PageSize = 100,
                PageNumber = 1,
                TrxId = model?.trxID?.toInt(),
                AllowToBrowesAllRecord = true
            )
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
        viewModel.getInventoryMovesDetailsDescription(appraisalBuildsSchema)

        binding.save.setOnClickListener {


            var iteamList = java.util.ArrayList<InventoryTrxWarehouseTransActionDetailsModel>()

            for (i in da) {

                var value = i.price!!.toDouble() * i.qty!!.toDouble()
                totalValue += value

                var iteam = InventoryTrxWarehouseTransActionDetailsModel(

                    TrxDetId = 0,
                    TrxId = i.itemSerial!!.toInt(),
                    StoreID = i.storeID,
                    RowIndex = da.indexOf(i),
                    ItemId = i.itemID?.toInt(),
                    UnitId = i.unitID?.toInt(),
                    Qty = i.qty!!.toDouble(),
                    RefuesQty = null,
                    AllQty = null,
                    RefuesQtyPercent = null,
                    Price = i.price!!.toDouble(),
                    TotalValue = value,
                    Notes = i.notes,
                    CopyFromModuleId = null,
                    CopyFromTrxTypeId = null,
                    CopyFromTrxId = null,
                    CopyFromLineId = null,
                    RecivedQty = null,
                    GlAccountId = null,
                    PatchNumber = null,
                    PatchExpireDate = null,
                    CopyFromQty = null,
                    StoreAccountId = null,
                    IsBouns = null,
                    LandedCostUnitPrice = null,
                    LandedCostTotalValue = null,
                    BounsQty = null,
                    OriginalCopyfromId = null,
                    TOFromStoreId = null,
                    StoreStorageLocationId = null,
                    PatchProductionDate = null
                )
                iteamList.add(iteam)
            }


            var requestObject = InventoryTrxWarehouseTransActionModel(
                TrxId = 0,
                TrxSerial = 0.toString(),
                TrxTypeId = 0,
                SupplierId = null,
                CustomerId = null,
                Trxdate = "",
                ProductId = null,
                PatchSerial = null,
                StoreId = model?.toStoreId,
                ToStoreId = model?.storeId,
                TrxNote = "",
                TotalValue = totalValue,
                AddUserId = accId.toInt(),
                AddMAc = "Android",
                AddDateTime = null,
                ModifyUserId = accId.toInt(),
                ModifyMac = null,
                ModifyDatetime = null,
                ApprovedUserId = null,
                ApprovedEmpId = null,
                ApprovedMac = null,
                ApprovedDatetime = null,
                TransferIsRecived = null,
                QualityChekUserId = null,
                QualityChekMac = null,
                QualityChekDateTime = null,
                QualityChekEmpid = null,
                Approved = null,
                PurchaseOrderSerial = null,
                PurchaseOrderId = null,
                JournalId = null,
                ProductionStageIndex = null,
                AddEmpId = empId.toInt(),
                ContractId = null,
                IndirectCostHasBeenDistributed = null,
                TrxAutoSerial = null,
                QualityCheckPass = null,
                ApprovalNotes = null,
                ManualTrxSerial = null,
                INventoryDetails = iteamList
            )

            viewModel.inventoryInsertAndUpdate(requestObject)
        }


        setObserver()
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
                this@InventoryLoadCarDescriptionLayout,
                it.rerurnMessage.toString(),
                Toast.LENGTH_SHORT
            ).show()

            finish()

        })


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_load_car_details

    }


    override fun setOnTypeSelect(model: Summary) {
        System.out.println("Selecteeeeeeeeed")

    }


    override fun onInventoryTypeClick(model: INvnetoryTrxDetail) {
        var iddddd = recyclerViewAdapter.getItemId(5)

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

