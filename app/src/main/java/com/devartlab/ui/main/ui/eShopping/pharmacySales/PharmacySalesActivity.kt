package com.devartlab.ui.main.ui.eShopping.pharmacySales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.a4eshopping.pharmacySales.PharmacySalesViewModel
import com.devartlab.databinding.ActivityPharmacySalesBinding
import com.devartlab.ui.auth.login.LoginActivity

class PharmacySalesActivity : AppCompatActivity() {
    lateinit var binding: ActivityPharmacySalesBinding
    var viewModel: PharmacySalesViewModel? = null
    private var adapter: PharmaciesSalesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_pharmacy_sales
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.Pharmacy_sales)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = PharmaciesSalesAdapter(null)
        viewModel = ViewModelProvider(this)[PharmacySalesViewModel::class.java]
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getPharmacySales("")
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel!!.getPharmacySales(binding.edSearch.text.toString())
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.pharmacySalesResponse.observe(this) {
            when {
                it!!.code == 401 -> {
                    Toast.makeText(this, "please login again", Toast.LENGTH_SHORT)
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
                    adapter = PharmaciesSalesAdapter(it.data)
                    binding.recyclerPharmacySalary.adapter = adapter
                    adapter!!.setOnItemClickListener { _, dataItem ->
                        val intent = Intent(this, DetailsPharmacySalesActivity::class.java)
                        intent.putExtra("order_number", dataItem.order_number)
                        startActivity(intent)
                    }
                }
            }
        }
    }


    private fun refresh() {
        synchronized(this) {
            viewModel!!.getPharmacySales("")
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}