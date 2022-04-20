package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityRequestVoucherBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.myVoucherRequest.Data
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest.VoucherRequestRequest
import com.devartlab.ui.main.ui.eShopping.requestVoucher.showVouchers.ShowVouchersActivity
import java.util.*
import kotlin.collections.ArrayList

class RequestVoucherActivity : AppCompatActivity() {
    lateinit var binding: ActivityRequestVoucherBinding
    var viewModel: RequestVoucherViewModel? = null
    val list = ArrayList<Data>()
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
        viewModel = ViewModelProvider(this)[RequestVoucherViewModel::class.java]
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
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel!!.myVoucherRequestResponse.observe(this) {
            when {
                it!!.code == 401 -> {
                    Toast.makeText(this, "please login again", Toast.LENGTH_LONG)
                        .show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                it.data.isNullOrEmpty() -> {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    //show data in recyclerView
                    binding.progressBar.visibility = View.GONE
                    adapter = MyVoucherRequestAdapter(it.data)
                    list.addAll(it.data)
                    binding.recOrderRequestVoucher.adapter = adapter
                    adapter!!.setOnItemClickListener { _, dataItem ->
                        val intent = Intent(this, ShowVouchersActivity::class.java)
                        intent.putExtra("_id", dataItem.id.toString())
                        intent.putExtra("_name", dataItem.doctor_name)
                        startActivity(intent)
                    }
                }
            }
        }

        viewModel!!.voucherRequestResponse.observe(this) {
            if (it!!.code == 200) {
                Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show()
                viewModel!!.getMyVoucherRequest()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Data> = ArrayList()

        for (item in list) {
            if (item.doctor_name.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }
        adapter!!.filterData(filteredList)
    }


    private fun refresh() {
        synchronized(this) {
            viewModel!!.getMyVoucherRequest()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = View.VISIBLE
        }
    }
}