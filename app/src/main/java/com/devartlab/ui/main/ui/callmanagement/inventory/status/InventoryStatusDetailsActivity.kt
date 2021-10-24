package com.devartlab.ui.main.ui.callmanagement.inventory.status

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.*
import com.devartlab.model.Summary
import com.devartlab.ui.trade.*
import com.google.gson.Gson

import com.devartlab.databinding.ActivityInventoryStatusDetailsBinding
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.utils.ProgressLoading

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.lang.Exception

class InventoryStatusDetailsActivity : BaseActivity<ActivityInventoryStatusDetailsBinding>(),
    InventoryMovesDestailsDescriptionAdaptor.OnTypeSelect,
    InventoryMovesDestailsDescriptionAdaptor.OnInventoryTypeClick {

    lateinit var binding: ActivityInventoryStatusDetailsBinding
    lateinit var viewModel: InventoryViewModel
    lateinit var adapter: InventoryStatusDetailsAdapter
    lateinit var da: List<INvnetoryTrxDetail>
    var sessionId = "0"
    var empId = "0"
    var accId = "0"
    var statues = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.description)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val model = intent.getParcelableExtra<INvnetory>("INvnetoryMasterDatum")
        statues = intent.getBooleanExtra("INvnetoryStatues", false)



        binding?.inventoryStatuesDescriptionTypeTxt?.text = model?.storName.toString()
        binding?.inventoryStatuesDescriptionSerialTxt?.text = model?.trxdate.toString()
        binding?.inventoryStatuesDescriptionDateTxt?.text = model?.trxTypeDescription.toString()


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

        setRecycler()
        setObservers()

    }

    private fun setRecycler() {
        adapter =
            InventoryStatusDetailsAdapter(
                this,
                ArrayList(),
                this,
                this,
                statues
            )
        binding.recyclerInventoryStatuesDescription.adapter = adapter
        binding.recyclerInventoryStatuesDescription.layoutManager = LinearLayoutManager(this)


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_status_details

    }


    override fun setOnTypeSelect(model: Summary) {
        System.out.println("Selecteeeeeeeeed")

    }


    override fun onInventoryTypeClick(model: INvnetoryTrxDetail) {


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

            if (it != null) {
                //update the adapter
                da = it.data?.iNvnetoryTrxDetails!!
                adapter.setMyData(it.data?.iNvnetoryTrxDetails as ArrayList<INvnetoryTrxDetail>)
            } else {
//                Toast.makeText(this@InventoryLayout, "Error in fetching data", Toast.LENGTH_LONG).show()
            }
        })

    }


    private fun uploadOrder(requestObject: InventoryTrxWarehouseTransActionModel) {
        ProgressLoading.show(this)
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
                ProgressLoading.dismiss()
                if (response.isSuccessful) {

                    System.out.println("yesssssss")
                    System.out.println(" APICALL" + response.body().toString())
                    System.out.println(" APICALL" + response.body()!!.isSuccesed)

                } else {
                    ProgressLoading.dismiss()
                    System.out.println("nooooooo")
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
}

