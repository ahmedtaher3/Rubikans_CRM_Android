package com.devartlab.ui.main.ui.callmanagement.trade.selectProducts

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentOrderProductsBinding
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.ui.main.ui.callmanagement.trade.TradeReportsViewModel
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading


class GeneralSelectProductsFragment : BaseFragment<FragmentOrderProductsBinding>(), GeneralProductsAdapter.OnItemSelect {

    lateinit var binding: FragmentOrderProductsBinding
    lateinit var viewModel: TradeReportsViewModel
    lateinit var adapter: GeneralProductsAdapter
    lateinit var selectedAdapter: GeneralSelectedProductsAdapter

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
        adapter = GeneralProductsAdapter(baseActivity, ArrayList(), this)
        selectedAdapter = GeneralSelectedProductsAdapter(baseActivity)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding
        viewModel.getAllProducts()
        binding.order.setOnClickListener {
            replace_fragment(GeneralConfirmProductsFragment(), "ConfirmProductsFragment")
        }

        binding.recyclerView?.adapter = adapter

        binding?.recyclerViewSelected?.layoutManager = LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false)

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
        selectedAdapter.addItem(model)
    }


}