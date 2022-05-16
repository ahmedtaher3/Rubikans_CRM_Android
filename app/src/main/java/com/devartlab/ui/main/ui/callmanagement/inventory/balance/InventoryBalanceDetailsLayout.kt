package com.devartlab.ui.main.ui.callmanagement.inventory.balance

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.VanStoctaking
import com.devartlab.databinding.ActivityInventoryBalanceDetailsLayoutBinding
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.ui.trade.*
import com.google.gson.Gson


private const val TAG = "InventoryBalanceDetails"
class InventoryBalanceDetailsLayout : BaseActivity<ActivityInventoryBalanceDetailsLayoutBinding>(){

    private lateinit var viewModell: RanksViewModel
    lateinit var binding: ActivityInventoryBalanceDetailsLayoutBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var recyclerViewAdapter: InventoryBalanceAdaptor
    var empId = "0"
    var accId = "0"


    companion object{
        lateinit var da: List<VanStoctaking>
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModell = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        accId = viewModell.dataManager.user.accId.toString()
        empId = viewModell.dataManager.user.empId.toString()
        var storeId= intent.getStringExtra("inv_id")

        System.out.println(accId)
        System.out.println(empId)
        System.out.println("TEEEEEST")



        var requestObject =
            ReportsFilterModel(

                _Option = 21,
                LoginUserAccountId = viewModel.dataManager.user.accId,
                EmployeeIdStr = viewModel.dataManager.user.empId.toString(),
                AccountIdStr = viewModel.dataManager.user.accId.toString(),
                PageSize = 100,
                PageNumber = 1,
                AllowToBrowesAllRecord = true,
                StoreIdStr = storeId

            )

        getData(requestObject)


    }


    private fun getData(requestObject: ReportsFilterModel) {
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
        Log.d(TAG, "getData: " + appraisalBuildsSchema)
        binding = viewDataBinding!!
        recyclerViewAdapter =
            InventoryBalanceAdaptor(
                this,
                ArrayList()
            )
        binding.recyclerInventoryBalanceDetails.adapter = recyclerViewAdapter
        //setContentView(R.layout.activity_main)
        binding.recyclerInventoryBalanceDetails.layoutManager = LinearLayoutManager(this)
        viewModel.getInventoryStocking(appraisalBuildsSchema)
        viewModel.recyclerListData.observe(this, Observer<ResponseModel> {
//            progressbarInv.visibility = View.GONE
            if (it != null) {


                //update the adapter
                if(it.isSuccesed==true){
                    recyclerViewAdapter.setMyData(it.data?.vanStoctaking as ArrayList<VanStoctaking>)
                    da = it.data?.vanStoctaking!!
                }


            } else {
//                Toast.makeText(this@InventoryLayout, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })

    }





    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_balance_details_layout
    }




}