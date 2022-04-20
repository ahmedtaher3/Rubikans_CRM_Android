package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityOrderRequestVouvherBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest.VoucherRequestRequest
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper

class OrderRequestVouvherActivity : BaseActivity<ActivityOrderRequestVouvherBinding?>(),
    GetDoctorsFragment.OnDoctorSelect {
    lateinit var binding: ActivityOrderRequestVouvherBinding
    var viewModel: RequestVoucherViewModel? = null
    private var compaignVouchersID: Int = 0
    private var doctorsID: String? = null
    private var doctorsName: String? = null
    lateinit var request: VoucherRequestRequest


    override fun getLayoutId(): Int {
        return R.layout.activity_order_request_vouvher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

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
            replace_fragment(GetDoctorsFragment(this), "GetDoctorsFragment")
        }

        binding.edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {
                val text: String = binding.edCount.text.toString()
                try {
                    val num = text.toInt()
                    Log.i("", "$num is a number")
                    if (TextUtils.isEmpty(
                            binding.edCount.text.toString()
                        ) || binding.edCount.text.toString()
                            .toInt() <= 0 || binding.edCount.text
                            .toString() === "-"
                    ) {
                        binding.edCount.error = "please enter right number"
                    } else {
                        binding.tvNoVouchers.visibility = View.VISIBLE
                        binding.tvNoVouchers.text = " عدد الكوبونات " + num * 50 + " كوبون "
                    }
                } catch (e: NumberFormatException) {
                    Log.i("", "$text is not a number")
                    binding.edCount.error = "please enter right number"
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.btnAddProblem.setOnClickListener {
            when {
                compaignVouchersID == 0 -> {
                    binding.edVoucher.error = "please select voucher"
                }
                doctorsID.equals(null) -> {
                    binding.edSelectDoctors.error = "please select doctor"
                }
                TextUtils.isEmpty(binding.edCount.text.toString()) -> {
                    binding.edCount.error = "please enter count"
                }
                TextUtils.isEmpty(binding.edSelectDoctors.text.toString()) -> {
                    binding.edSelectDoctors.error = "please enter doctors"
                }
                else -> {
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
        viewModel!!.voucherRequestResponse.observe(this) {
            if (it!!.code == 401) {
                viewModel!!.dataManager.clear()
                UserPreferenceHelper.clean()
                Toast.makeText(this, "please login again", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RequestVoucherActivity::class.java)
                startActivity(intent)
            }
        }
        viewModel!!.compaignVouchersResponse.observe(this) {
            val countryBrandsPopUp = PopupMenu(this, binding.edVoucher)
            for (i in 0 until it!!.size) {
                countryBrandsPopUp.menu
                    .add(i, i, i, it[i].voucher_translates_title[0].title)
            }
            countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edVoucher.text = it[item.itemId].voucher_translates_title[0].title
                compaignVouchersID = it[item.itemId].id
                Log.e("popupCarBrands", "onMenuItemClick: $compaignVouchersID")
                return@OnMenuItemClickListener false
            })
            countryBrandsPopUp.show()
        }
    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {
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
                R.id.container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun setOnDoctorSelect(id: String, name: String) {
            doctorsID = id
            doctorsName = name
            binding.edSelectDoctors.text = doctorsName

    }
}