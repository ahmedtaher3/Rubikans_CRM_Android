package com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.contract.ContractEntity
 import com.devartlab.data.room.myballance.MyBallanceEntity
import com.devartlab.databinding.FragmentOrderProductsBinding
import com.devartlab.model.DevartLabReportsFilterDTO
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.devartlab.ui.main.ui.callmanagement.trade.TradeReportsViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading

private const val TAG = "SelectProductsFragment"

class SelectProductsFragment : BaseFragment<FragmentOrderProductsBinding>(),
    ProductsAdapter.OnItemSelect {

    lateinit var binding: FragmentOrderProductsBinding
    lateinit var viewModel: TradeReportsViewModel
    lateinit var adapter: ProductsAdapter
    lateinit var selectedAdapter: SelectedProductsAdapter

    var list = ArrayList<MyBallanceEntity>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_order_products
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Requests"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradeReportsViewModel::class.java)
        adapter = ProductsAdapter(baseActivity, ArrayList(), this)
        selectedAdapter = SelectedProductsAdapter(baseActivity)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding

        val filterModel = DevartLabReportsFilterDTO(
            9,
            1000,
            1,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null,
            null,
            null,
            null,
            false,
            null,
            arguments?.getInt("CUSTOMER_ID", 0),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )


        viewModel.getProducts(filterModel)


        var requestObject =
            ReportsFilterModel(

                _Option = 21,
                LoginUserAccountId = viewModel.dataManager?.user?.accId,
                EmployeeIdStr = viewModel.dataManager?.user?.empId.toString(),
                AccountIdStr = viewModel.dataManager?.user?.accId.toString(), PageSize = 100,
                PageNumber = 1,
                AllowToBrowesAllRecord = true,
                StoreIdStr = viewModel.dataManager?.user?.storeId.toString()
            )


        viewModel.getMyProducts(requestObject)


        binding.order.setOnClickListener {
            replace_fragment(ConfirmProductsFragment(), "ConfirmProductsFragment")
        }

        binding.recyclerView?.adapter = adapter

        binding?.recyclerViewSelected?.layoutManager =
            LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerViewSelected?.adapter = selectedAdapter


        setObservers()


    }

    private fun setObservers() {

        viewModel.productLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setMyData(it)
        })

        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()

                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })


        viewModel.myProductLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            list = it

        })


    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {

        val bundle = Bundle()
        bundle.putParcelableArrayList("PRODUCTS", selectedAdapter.getMyData())
        //    bundle.putParcelable("CustomerTrade", arguments?.getParcelable("CustomerTrade"))

        fragment?.arguments = bundle
        baseActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }

    override fun setOnItemSelect(model: ContractEntity) {

        if (list.isNotEmpty()) {


            for (m in list) {
                if (m.itemId == model.itemId) {

                    Log.d(TAG, m.toString())
                    model.maxCount = m.qty
                    selectedAdapter.addItem(model)
                    try {
                        binding?.recyclerViewSelected?.scrollToPosition(selectedAdapter?.getMyData()?.size!! - 1);

                    }
                    catch (e: Exception) {
                    }


                    return
                }
            }

            Log.d(TAG, "setOnItemSelect: Not found")


        } else {
            selectedAdapter.addItem(model)
            try {
                binding?.recyclerViewSelected?.scrollToPosition(selectedAdapter?.getMyData()?.size!! - 1);
            } catch (e: Exception) {
            }
        }

    }


}