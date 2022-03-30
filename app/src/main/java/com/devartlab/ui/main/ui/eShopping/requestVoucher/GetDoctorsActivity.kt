package com.devartlab.ui.main.ui.eShopping.requestVoucher

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
import com.devartlab.databinding.ActivityGetDoctorsBinding
import com.devartlab.ui.auth.login.LoginActivity

class GetDoctorsActivity : AppCompatActivity() {
    lateinit var binding:ActivityGetDoctorsBinding
    var viewModel: RequestVoucherViewModel? = null
    private var adapter: GetDoctorsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_get_doctors
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.doctors)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this)[RequestVoucherViewModel::class.java]
        adapter = GetDoctorsAdapter(null)
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
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.getDoctorsResponse.observe(this, Observer {
            if (it!!.data.isNullOrEmpty()||it.code==401) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
                binding.progressBar.setVisibility(View.GONE)
                Toast.makeText(this, "please login again", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = GetDoctorsAdapter(it.data)
                binding.recyclerDoctors.setAdapter(adapter)
                adapter!!.setOnItemClickListener(GetDoctorsAdapter.OnItemClickListener { pos, dataItem ->
                    val intent = Intent(this, OrderRequestVouvherActivity::class.java)
                    intent.putExtra("_id", dataItem.id.toString())
                    intent.putExtra("_name", dataItem.text)
                    startActivity(intent)
                })
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}