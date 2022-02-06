package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityRequestVoucherBinding
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.myVoucherRequest.Data
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest.VoucherRequestRequest
import com.devartlab.ui.main.ui.eShopping.requestVoucher.showVouchers.ShowVouchersActivity
import java.lang.NumberFormatException

class RequestVoucherActivity : AppCompatActivity() {
    lateinit var binding: ActivityRequestVoucherBinding
    var viewModel: RequestVoucherViewModel? = null
    var compaignVouchersID: Int = 0
    var doctorsID: Int = 0
    val list = ArrayList<Data>()
    private var adapter: MyVoucherRequestAdapter? = null
    lateinit var request: VoucherRequestRequest
    var doctorsName: String? = null
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
            orderRequestVouvherDialog()
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
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel!!.myVoucherRequestResponse.observe(this, Observer {
            if (it!!.data == null) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = MyVoucherRequestAdapter(it.data)
                list.addAll(it.data)
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

        edSelectDoctors.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                viewModel!!.getDoctors(edSelectDoctors.text.toString())
            }
        })


        viewModel!!.getDoctorsResponse.observe(this, Observer {
            if (it!!.data == null) {
                Toast.makeText(this, "not Authorized", Toast.LENGTH_SHORT).show()
            } else {
                val countryBrandsPopUp = PopupMenu(this, edSelectDoctors)
                for (i in 0 until it!!.data.size) {
                    countryBrandsPopUp.getMenu()
                        .add(i, i, i, it.data.get(i).id.toString() + it.data.get(i).text)
                }
                countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    edSelectDoctors.setText(it.data.get(item.getItemId()).text)
                    doctorsID = it.data.get(item.getItemId()).id
                    doctorsName = it.data.get(item.getItemId()).text
                    return@OnMenuItemClickListener false;
                })
                countryBrandsPopUp.show()
            }
        })
        edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                val text: String = edCount.getText().toString()
                try {
                    val num = text.toInt()
                    Log.i("", "$num is a number")
                    if (TextUtils.isEmpty(
                            edCount.getText().toString()
                        ) || edCount.getText().toString()
                            .toInt() <= 0 || edCount.getText()
                            .toString() === "-"
                    ) {
                        edCount.setError("please enter right number")
                    } else {
                        tvNoVouchers.setVisibility(View.VISIBLE)
                        tvNoVouchers.setText(" عدد" + num * 50 + "الكوبونات كوبون ")
                    }
                } catch (e: NumberFormatException) {
                    Log.i("", "$text is not a number")
                    edCount.setError("please enter right number")
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        viewModel!!.compaignVouchersResponse.observe(this, Observer {

            val countryBrandsPopUp = PopupMenu(this, edVoucher)
            for (i in 0 until it!!.size) {
                countryBrandsPopUp.getMenu()
                    .add(i, i, i, it.get(i).voucher_translates_title.get(0).title)
            }
            countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                edVoucher.setText(it.get(item.getItemId()).voucher_translates_title.get(0).title)
                compaignVouchersID = it.get(item.getItemId()).id
                Log.e("popupCarBrands", "onMenuItemClick: $compaignVouchersID")
                return@OnMenuItemClickListener false
            })
            countryBrandsPopUp.show()
        })
        BtnAddTicket.setOnClickListener {
            if (compaignVouchersID == 0) {
                edVoucher.setError("please select voucher")
            } else if (doctorsID == 0) {
                edSelectDoctors.setError("please select doctor")
            } else if (TextUtils.isEmpty(edCount.getText().toString())) {
                edCount.setError("please enter count")
            } else if (TextUtils.isEmpty(edSelectDoctors.getText().toString())) {
                edSelectDoctors.setError("please enter doctors")
            } else {
                request = VoucherRequestRequest(
                    compaignVouchersID,
                    edCount.text.toString(),
                    doctorsID,
                    doctorsName!!
                )
                viewModel!!.getVoucherRequest(request)
                dialog.dismiss()
            }
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
    private fun filter(text: String) {
        val filteredList: ArrayList<Data> = ArrayList()

        for (item in list) {
            if (item.doctor_name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
                Log.e("xxx",item.toString())
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