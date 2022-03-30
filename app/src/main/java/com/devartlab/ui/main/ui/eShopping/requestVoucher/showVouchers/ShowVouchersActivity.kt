package com.devartlab.ui.main.ui.eShopping.requestVoucher.showVouchers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityShowVouchersBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.requestVoucher.RequestVoucherViewModel
import com.devartlab.ui.main.ui.eShopping.requestVoucher.ShowVouchersAdapter
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.deliverVoucher.DeliverVoucherRequest
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.getVoucher.Data

class ShowVouchersActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowVouchersBinding
    var viewModel: RequestVoucherViewModel? = null
    var _id: String? = null
    var _name: String? = null
    val list = ArrayList<Data>()
    lateinit var request: DeliverVoucherRequest
    private var adapter: ShowVouchersAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_show_vouchers)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.show_voucher)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("_id")) {
            _id = intent.getStringExtra("_id")
        }
        if (intent.hasExtra("_name")) {
            _name = intent.getStringExtra("_name")
        }
        viewModel = ViewModelProvider(this).get(RequestVoucherViewModel::class.java)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getVouchers(_id!!)
        binding.tvLabelDrName.setText(_name)
        binding.tvPeopleSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    private fun handleObserver() {
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel!!.deliverVoucherResponse.observe(this, Observer {
            if(it!!.code==401) {
                Toast.makeText(this, "please login again", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                viewModel!!.getVouchers(_id!!)
                Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel!!.getVoucherResponse.observe(this, Observer {
            if (it!!.data.isNullOrEmpty()) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
                binding.progressBar.setVisibility(View.GONE)
            } else if(it.code==401){
                Toast.makeText(this, "please login again", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = ShowVouchersAdapter(it.data)
                list.addAll(it.data)
                binding.recOrderRequestVoucher.setAdapter(adapter)
                adapter!!.setOnItemClickListener(ShowVouchersAdapter.OnItemClickListener { pos, dataItem ->
                    request = DeliverVoucherRequest(dataItem.id)
                    viewModel!!.getDeliverVoucher(request)
                })
            }
        })
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Data> = ArrayList()

        for (item in list) {
            val filter:String= (item.from+item.to).toString()
            if (filter.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
                Log.e("xxx", item.toString())
            }
        }
        adapter!!.filterData(filteredList)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getVouchers(_id!!)
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }
}