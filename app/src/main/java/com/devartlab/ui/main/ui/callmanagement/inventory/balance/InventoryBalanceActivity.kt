package com.devartlab.ui.main.ui.callmanagement.inventory.balance

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.retrofit.StoresTBl
import com.devartlab.databinding.ActivityInventoryBalanceLayoutBinding
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.trade.*
import com.devartlab.utils.ProgressLoading
import kotlinx.android.synthetic.main.activity_inventory_balance_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InventoryBalanceActivity : BaseActivity<ActivityInventoryBalanceLayoutBinding>() {

    lateinit var binding: ActivityInventoryBalanceLayoutBinding
    lateinit var viewModel: InventoryViewModel
    var storeName = ""
    var storePosetion = 0
    var storeIdd = ""
    var listNames = ArrayList<String>()
    var storesTBl = ArrayList<StoresTBl>()
    lateinit var da: ResponseModel
    lateinit var InventoryStoreList: ArrayList<String>


    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_balance_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        InventoryStoreList = ArrayList()

//        setContentView(R.layout.activity_inventory_balance_layout)

        getStoresList()

        val adapter1 = ArrayAdapter(this, R.layout.adapter_list_item, listNames)
        (binding.inventoryBalanceSpinner.editText as? AutoCompleteTextView)?.setAdapter(adapter1)
        (binding.inventoryBalanceSpinner.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->

            storeName = binding.inventoryBalanceSpinner.editText?.text.toString();
            storePosetion = i
            System.out.println(i.toString() + "iiiiiiiiiii")

            storeIdd = storesTBl[i].storID.toString()

            System.out.println(storeIdd + "iiiiiiiiiiiddddddd")

        }


        inventoryBalanceFab.setOnClickListener() {
            System.out.println(storeIdd + "storeIdd")
            val intent =
                Intent(this@InventoryBalanceActivity, InventoryBalanceDetailsLayout::class.java)
            intent.putExtra("inv_id", storeIdd)
            startActivity(intent)

        }

    }


    private fun getStoresList() {


        ProgressLoading.show(this)
        val retroInstance = RetrofitClient.getInstance().create(ApiServices::class.java)
        val call = retroInstance.getStoresDataFromAPI()
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                ProgressLoading.dismiss()
            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                ProgressLoading.dismiss()
                if (response.isSuccessful) {

                    System.out.println("yesssssss")
                    System.out.println(" APICALL" + response.body().toString())
                    da = response.body()!!
                    System.out.println(da?.data?.storesTBl?.get(0)?.storName)


                    storesTBl = ArrayList<StoresTBl>(da?.data?.storesTBl!!)
                    for (item in da?.data?.storesTBl!!) {
                        InventoryStoreList.add(item.storName!!)


                        val adapter = ArrayAdapter(
                            this@InventoryBalanceActivity,
                            R.layout.adapter_list_item,
                            InventoryStoreList
                        )
                        (binding.inventoryBalanceSpinner.editText as? AutoCompleteTextView)?.setAdapter(
                            adapter
                        )


                    }

                } else {

                    System.out.println("nooooooo")
                }
            }
        })
    }
}