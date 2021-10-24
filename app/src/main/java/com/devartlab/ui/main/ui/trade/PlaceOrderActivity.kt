package com.devartlab.ui.main.ui.trade

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.FragmentPlaceOrderBinding
import com.devartlab.model.CustomerTrade
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PlaceOrderActivity : BaseActivity<FragmentPlaceOrderBinding>(), CustomersAdapter.OnCustomerSelect, OnPlaceChoose {

    lateinit var binding: FragmentPlaceOrderBinding
    lateinit var viewModel: TradeViewModel
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var adapter: CustomersAdapter
    //  private val bxlPrinter: BixolonPrinter? = null


    var cityNamesList = ArrayList<String>()
    var cityIdsList = ArrayList<Int>()

    var areaNamesList = ArrayList<String>()
    var areaIdsList = ArrayList<Int>()
    var list = ArrayList<String>()


    override fun getLayoutId(): Int {
        return R.layout.fragment_place_order
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)

        adapter = CustomersAdapter(this, ArrayList(), this)
        binding.cutomers.layoutManager = LinearLayoutManager(this)
        binding.cutomers.adapter = adapter

        viewModel.getByEmpId()

        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Trade"



        binding.filter.setOnClickListener {


        /*    val choosePlaceDialog = ChoosePlaceDialog(this, this,viewModel.myAPI!!, this)
            choosePlaceDialog.setCanceledOnTouchOutside(true)
            val window = choosePlaceDialog.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            choosePlaceDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            choosePlaceDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            choosePlaceDialog.show()*/

        }





        setObservers()
    }

    private fun setObservers() {


        viewModel.customerLive.observe(this, Observer {

            adapter.setMyData(it.customers!!)
        })


        viewModel.progress.observe(this, Observer {

            when (it) {
                0 -> {
                    ProgressLoading.dismiss()
                }

                1 -> {
                    ProgressLoading.show(this@PlaceOrderActivity)
                }
            }
        })


    }

    fun replace_fragment(fragment: Fragment?, tag: String?, model: CustomerTrade) {

        val bundle = Bundle()
        bundle.putParcelable("CustomerTrade", model)
        fragment?.arguments = bundle

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left
                )
                .add(
                        R.id.Container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setOnCustomerSelect(model: CustomerTrade) {

        replace_fragment(OrderProductsFragment(), "OrderProductsFragment", model)

    }


    override fun setOnPlaceChoose(id: Int) {

        viewModel.getByAreaId(id.toString())
    }

}