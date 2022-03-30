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
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.databinding.ActivityOrderRequestVouvherBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest.VoucherRequestRequest

class OrderRequestVouvherActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderRequestVouvherBinding
    var viewModel: RequestVoucherViewModel? = null
    var compaignVouchersID: Int = 0
    var doctorsID: String? = null
    var doctorsName: String? = null
    lateinit var request: VoucherRequestRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_order_request_vouvher
        )
        if (intent.hasExtra("_id")) {
            doctorsID = intent.getStringExtra("_id")
        }
        if (intent.hasExtra("_name")) {
            doctorsName = intent.getStringExtra("_name")
            binding.edSelectDoctors.setText(doctorsName)
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.order_request_voucher)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this)[RequestVoucherViewModel::class.java]
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.edVoucher.setOnClickListener {
            viewModel!!.getCompaignVouchers()
        }

        binding.edSelectDoctors.setOnClickListener {
            val intent = Intent(this, GetDoctorsActivity::class.java)
            startActivityForResult(intent, 100)
        }

        binding.edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {
                val text: String = binding.edCount.getText().toString()
                try {
                    val num = text.toInt()
                    Log.i("", "$num is a number")
                    if (TextUtils.isEmpty(
                            binding.edCount.getText().toString()
                        ) || binding.edCount.getText().toString()
                            .toInt() <= 0 || binding.edCount.getText()
                            .toString() === "-"
                    ) {
                        binding.edCount.setError("please enter right number")
                    } else {
                        binding.tvNoVouchers.setVisibility(View.VISIBLE)
                        binding.tvNoVouchers.setText(" عدد الكوبونات " + num * 50 + " كوبون ")
                    }
                } catch (e: NumberFormatException) {
                    Log.i("", "$text is not a number")
                    binding.edCount.setError("please enter right number")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.btnAddProblem.setOnClickListener {
            if (compaignVouchersID == 0) {
                binding.edVoucher.setError("please select voucher")
            } else if (doctorsID.equals(null)) {
                binding.edSelectDoctors.setError("please select doctor")
            } else if (TextUtils.isEmpty(binding.edCount.getText().toString())) {
                binding.edCount.setError("please enter count")
            } else if (TextUtils.isEmpty(binding.edSelectDoctors.getText().toString())) {
                binding.edSelectDoctors.setError("please enter doctors")
            } else {
                request = VoucherRequestRequest(
                    compaignVouchersID,
                    binding.edCount.text.toString(),
                    doctorsID!!.toInt(),
                    doctorsName!!
                )
                viewModel!!.getVoucherRequest(request)
            }
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
        viewModel!!.voucherRequestResponse.observe(this, Observer {
            if (it!!.code == 401) {
                Toast.makeText(this, "please login again", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RequestVoucherActivity::class.java)
                startActivity(intent)
            }
        })
        viewModel!!.compaignVouchersResponse.observe(this, Observer {
                val countryBrandsPopUp = PopupMenu(this, binding.edVoucher)
                for (i in 0 until it!!.size) {
                    countryBrandsPopUp.getMenu()
                        .add(i, i, i, it.get(i).voucher_translates_title.get(0).title)
                }
                countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    binding.edVoucher.setText(
                        it.get(item.getItemId()).voucher_translates_title.get(
                            0
                        ).title
                    )
                    compaignVouchersID = it.get(item.getItemId()).id
                    Log.e("popupCarBrands", "onMenuItemClick: $compaignVouchersID")
                    return@OnMenuItemClickListener false
                })
                countryBrandsPopUp.show()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}