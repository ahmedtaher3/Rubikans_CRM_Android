package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityRequestVoucherBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.myVoucherRequest.Data
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest.VoucherRequestRequest
import com.devartlab.ui.main.ui.eShopping.requestVoucher.showVouchers.ShowVouchersActivity

class RequestVoucherActivity : AppCompatActivity() {
    lateinit var binding: ActivityRequestVoucherBinding
    var viewModel: RequestVoucherViewModel? = null
    var compaignVouchersID: Int = 0
    var doctorsID: Int = 0
    var doctorsName: String? = null
    val list = ArrayList<Data>()
    private val doctors: List<String> = java.util.ArrayList()
    private var adapterDoctors: ArrayAdapter<String>? = null
    private var adapter: MyVoucherRequestAdapter? = null
    lateinit var request: VoucherRequestRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_request_voucher
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.request_voucher)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(RequestVoucherViewModel::class.java)
        adapter = MyVoucherRequestAdapter(null)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getMyVoucherRequest()
        binding.btnOrderRequestVoucher.setOnClickListener {
            val intent = Intent(this, OrderRequestVouvherActivity::class.java)
            startActivity(intent)
        }
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
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel!!.myVoucherRequestResponse.observe(this, Observer {
            if (it!!.data.isNullOrEmpty()) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
                binding.progressBar.setVisibility(View.GONE)
            }else if(it.code==401){
                Toast.makeText(this, "please login again", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else{
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = MyVoucherRequestAdapter(it.data)
                list.addAll(it.data!!)
                binding.recOrderRequestVoucher.setAdapter(adapter)
                adapter!!.setOnItemClickListener(MyVoucherRequestAdapter.OnItemClickListener { pos, dataItem ->
                    val intent = Intent(this, ShowVouchersActivity::class.java)
                    intent.putExtra("_id", dataItem.id.toString())
                    intent.putExtra("_name", dataItem.doctor_name)
                    startActivity(intent)
                })
            }
        })

        viewModel!!.voucherRequestResponse.observe(this, Observer {
            if (it!!.code == 200) {
                Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show()
                viewModel!!.getMyVoucherRequest()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun filter(text: String) {
        val filteredList: ArrayList<Data> = ArrayList()

        for (item in list) {
            if (item.doctor_name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter!!.filterData(filteredList)
    }


    private fun refresh() {
        synchronized(this) {
            viewModel!!.getMyVoucherRequest()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }
}