package com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentConfirmProductsBinding
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.ui.main.ui.callmanagement.trade.selectProducts.GeneralProductsAdapter
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson


class ConfirmProductsFragment : BaseFragment<FragmentConfirmProductsBinding>(), GeneralProductsAdapter.OnItemSelect {

    lateinit var binding: FragmentConfirmProductsBinding
    lateinit var viewModel: TradeViewModel
    lateinit var adapter: ConfirmProductsAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_confirm_products
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
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)
        adapter = ConfirmProductsAdapter(baseActivity, ArrayList())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!
        val list = arguments?.getParcelableArrayList<ContractEntity>("PRODUCTS")!!
        binding.order.setOnClickListener {



            val json = Gson().toJson(adapter.getMyData())

            val returnIntent = Intent()
            returnIntent.putExtra("PRODUCTS",json )
            baseActivity.setResult(Activity.RESULT_OK, returnIntent)
            baseActivity.finish()


        }

        binding.recyclerView?.adapter = adapter

        val listt = list.distinctBy { it.id }
        adapter.setMyData(list as ArrayList<ContractEntity>)

        setObservers()


    }

    private fun setObservers() {

        viewModel.productLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            adapter.setMyData(it.data.contractList2)
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
        //   bundle.putParcelableArrayList("PRODUCTS", adapter.getMyData())
        //    bundle.putParcelable("CustomerTrade", arguments?.getParcelable("CustomerTrade"))

        fragment?.arguments = bundle
        baseActivity.supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.Container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }

    override fun setOnItemSelect(model: ContractEntity) {

    }


}