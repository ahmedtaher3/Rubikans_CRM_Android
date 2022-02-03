package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.AddLocationViewModel
import com.devartlab.databinding.ActivityRequestVoucherBinding
import com.devartlab.ui.main.ui.eShopping.ticket.model.addTicket.AddTicketRequest

class RequestVoucherActivity : AppCompatActivity() {
    lateinit var binding: ActivityRequestVoucherBinding
    var viewModel: RequestVoucherViewModel? = null
    var compaignVouchersID: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_request_voucher
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.request_voucher)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(RequestVoucherViewModel::class.java)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.btnOrderRequestVoucher.setOnClickListener {
            orderRequestVouvherDialog()
        }
    }

    private fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun orderRequestVouvherDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_order_request_voucher)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val BtnAddTicket = dialog.findViewById<Button>(R.id.btn_add_problem)
        val BtnCancel = dialog.findViewById<ImageView>(R.id.iv_cancel_dialog)
        val edSelectDoctors = dialog.findViewById<EditText>(R.id.ed_select_doctors)
        val edVoucher = dialog.findViewById<TextView>(R.id.ed_voucher)
        val edCount = dialog.findViewById<EditText>(R.id.ed_count)
        val tvNoVouchers = dialog.findViewById<TextView>(R.id.tv_no_vouchers)
        edVoucher.setOnClickListener {
            viewModel!!.getCompaignVouchers()
        }
        viewModel!!.compaignVouchersResponse.observe(this, Observer {

            val countryBrandsPopUp = PopupMenu(this, edVoucher)
            for (i in 0 until it!!.size) {
                countryBrandsPopUp.getMenu().add(i, i, i, it.get(i).voucher_translates_title.get(0).title)
            }
            countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                edVoucher.setText(it.get(item.getItemId()).voucher_translates_title.get(0).title)
                compaignVouchersID = it.get(item.getItemId()).id.toInt()
                Log.e("popupCarBrands", "onMenuItemClick: $compaignVouchersID")
                return@OnMenuItemClickListener false
            })
            countryBrandsPopUp.show()
        })
        BtnAddTicket.setOnClickListener {
//            if (TextUtils.isEmpty(TvSelectProblem.getText().toString())) {
//                TvSelectProblem.setError("please select problem")
//            } else if (TextUtils.isEmpty(EdTitleProblem.getText().toString())) {
//                EdTitleProblem.setError("please enter title")
//            } else if (TextUtils.isEmpty(EdMessageProblem.getText().toString())) {
//                EdMessageProblem.setError("please enter message")
//            } else {
//                request = AddTicketRequest(
//                    EdMessageProblem.text.toString(),
//                    EdOther.text.toString(),
//                    EdTitleProblem.text.toString(),
//                    TvSelectProblem.text.toString()
//                )
//                viewModel!!.addTicket(request)
//                dialog.dismiss()
//            }
        }
        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}