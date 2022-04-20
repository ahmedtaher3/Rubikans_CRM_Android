package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.ActivityGetDoctorsBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.utils.CommonUtilities

class GetDoctorsFragment(private val listener:OnDoctorSelect) : BaseFragment<ActivityGetDoctorsBinding>() {
    lateinit var binding: ActivityGetDoctorsBinding
    var viewModel: RequestVoucherViewModel? = null
    private var adapter: GetDoctorsAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_get_doctors
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RequestVoucherViewModel::class.java]
        adapter = GetDoctorsAdapter(null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getDoctors("")
        binding.searchDoctors.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel!!.getDoctors(binding.searchDoctors.text.toString())
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
    }

    private fun handleObserver() {
        viewModel!!.errorMessage.observe(viewLifecycleOwner) { integer: Int ->
            if (integer == 1) {
                Toast.makeText(context, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.getDoctorsResponse.observe(viewLifecycleOwner) {
            when {
                it!!.code == 401 -> {
                    viewModel!!.dataManager.clear()
                    UserPreferenceHelper.clean()
                    Toast.makeText(context, "please login again", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
                    adapter = GetDoctorsAdapter(it.data)
                    binding.recyclerDoctors.adapter = adapter
                    adapter!!.setOnItemClickListener { _, dataItem ->

                        listener.setOnDoctorSelect(dataItem.id.toString(), dataItem.text)
                        baseActivity.onBackPressed()
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title =
                getString(R.string.doctors)

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity!!, false)
        } catch (e: Exception) {
        }
    }

    interface OnDoctorSelect {
        fun setOnDoctorSelect(id: String, name: String)
    }
}