package com.devartlab.ui.main.ui.callmanagement.inventory.movements

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityInventoryInventoryBinding
import com.devartlab.model.Summary
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_inventory_inventory.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.Observer
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.retrofit.VanStoctaking
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.ui.trade.*


class StockingActivity : BaseActivity<ActivityInventoryInventoryBinding>(),
    InventoryInventoryAdapter.OnTypeSelect, InventoryInventoryAdapter.OnInventoryTypeClick {

    private lateinit var viewModell: RanksViewModel
    lateinit var binding: ActivityInventoryInventoryBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var recyclerViewAdapter: InventoryInventoryAdapter
    var empId = "0"
    var accId = "0"

    companion object {
        lateinit var da: List<VanStoctaking>
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = viewDataBinding

        viewModell = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        accId = viewModell.dataManager.user.accId.toString()
        empId = viewModell.dataManager.user.empId.toString()




        var requestObject =
            ReportsFilterModel(

                _Option = 21,
                LoginUserAccountId = viewModel.dataManager.user.accId,
                EmployeeIdStr = viewModel.dataManager.user.empId.toString(),
                AccountIdStr = viewModel.dataManager.user.accId.toString(),
                PageSize = 100,
                PageNumber = 1,
                StoreIdStr = intent.getStringExtra("STORE_ID" ),
                AllowToBrowesAllRecord = false
            )

        getData(requestObject)





        inventoryInventoryEditBtn.setOnClickListener {

            var iteamList = java.util.ArrayList<InventoryTrxWarehouseTransActionDetailsModel>()

            for (i in da) {

//                var value = i.price!!.toDouble()* i.qty!!.toDouble()
//                totalValue += value

                var iteam = InventoryTrxWarehouseTransActionDetailsModel(

                    TrxDetId = 0,
                    TrxId = 0,
                    StoreID = i.storeID,
                    RowIndex = da.indexOf(i),
                    ItemId = i.itemID?.toInt(),
                    UnitId = i.itemPrincipalUnitID?.toInt(),
                    Qty = i.qtyy!!.toDouble(),
                    RefuesQty = null,
                    AllQty = null,
                    RefuesQtyPercent = null,
                    Price = null,
                    TotalValue = null,
                    Notes = null,
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
                TrxTypeId = 101010,
                SupplierId = null,
                CustomerId = null,
                Trxdate = "TEEEEEST",
                ProductId = null,
                PatchSerial = null,
                StoreId = da[0].storeID,
                ToStoreId = null,
                TrxNote = "TEEEEEST",
                TotalValue = null,
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

            System.out.println("iteam sizeeeeeeeeeeeee")
            val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
            System.out.println(appraisalBuildsSchema)
            System.out.println(requestObject.toString())

            System.out.println("iteam sizeeeeeeeeeeeee")

            uploadOrder(requestObject)
            val intent =
                Intent(this@StockingActivity, InventoryInventoryDetailsActivity::class.java)
            startActivity(intent)
        }

    }


    private fun getData(requestObject: ReportsFilterModel) {
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
        binding = viewDataBinding
        recyclerViewAdapter = InventoryInventoryAdapter(this, ArrayList(), this, this)
        binding.recyclerInventoryInventory.adapter = recyclerViewAdapter
        //setContentView(R.layout.activity_main)
        binding.recyclerInventoryInventory.layoutManager = LinearLayoutManager(this)
        viewModel.getInventoryStocking(appraisalBuildsSchema)
        viewModel.recyclerListData.observe(this, Observer<ResponseModel> {
            progressbarInv.visibility = View.GONE
            if (it != null) {
                //update the adapter
                if (it.isSuccesed == true) {
                    recyclerViewAdapter.setMyData(it.data?.vanStoctaking as ArrayList<VanStoctaking>)
                    da = it.data?.vanStoctaking!!
                }


            } else {
//                Toast.makeText(this@InventoryLayout, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })

    }


    private fun uploadOrder(requestObject: InventoryTrxWarehouseTransActionModel) {
        progressbarInv.visibility = View.VISIBLE
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
        System.out.println(" InventoryTrxWarehouseTransActionModel " + appraisalBuildsSchema.toString())
        val retroInstance = RetrofitClient.getInstance().create(ApiServices::class.java)
        val call = retroInstance.inventoryInsertAndUbdate(appraisalBuildsSchema)
        call.enqueue(object : Callback<ResponseModel> {

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                System.out.println("failrrrrrrrrrrrr")
                System.out.println(t.message)
                System.out.println("failrrrrrrrrrrrr")
                System.out.println(appraisalBuildsSchema)
                System.out.println("failrrrrrrrrrrrr")

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    progressbarInv.visibility = View.GONE
                    System.out.println("yesssssss")
                    System.out.println(" APICALL" + response.body().toString())
                    System.out.println(" APICALL" + response.body()!!.isSuccesed)

                } else {
                    progressbarInv.visibility = View.GONE
                    System.out.println("nooooooo")
                }
            }

        })

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_inventory
    }

    override fun onInventoryTypeClick(model: VanStoctaking) {
        System.out.println("Selecteeeeeeeeed")
    }

    override fun setOnTypeSelect(model: Summary) {
        System.out.println("Selecteeeeeeeeed")
    }


}