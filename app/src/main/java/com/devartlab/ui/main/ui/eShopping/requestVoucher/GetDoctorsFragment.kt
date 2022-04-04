package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.ActivityGetDoctorsBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.utils.CommonUtilities

class GetDoctorsFragment : BaseFragment<ActivityGetDoctorsBinding>(){
    lateinit var binding:ActivityGetDoctorsBinding
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
                Log.e("xxx", "error")
                Toast.makeText(context, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.getDoctorsResponse.observe(viewLifecycleOwner, Observer {
            if (it!!.data.isNullOrEmpty()||it.code==401) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "please login again", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            } else {
                //show data in recyclerView
                binding.progressBar.visibility = View.GONE
                adapter = GetDoctorsAdapter(it.data)
                binding.recyclerDoctors.adapter = adapter
                adapter!!.setOnItemClickListener(GetDoctorsAdapter.OnItemClickListener { _, dataItem ->
                    val intent = Intent(context, OrderRequestVouvherActivity::class.java)
                    intent.putExtra("_id", dataItem.id.toString())
                    intent.putExtra("_name", dataItem.text)
                    startActivity(intent)
                })
            }
        })
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.doctors)

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity!!, false)
        } catch (e: Exception) {
        }
    }
}