package com.devartlab.ui.main.ui.eShopping.pharmacySales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.a4eshopping.pharmacySales.PharmacySalesViewModel
import com.devartlab.databinding.ActivityDetailsPharmacySalesBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailsPharmacySalesActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsPharmacySalesBinding
    var viewModel: PharmacySalesViewModel? = null
    private var adapter: DetailsPharmaciesSalesAdapter? = null
    var order_number: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_details_pharmacy_sales)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.details_pharmacy_sales)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("order_number")) {
            order_number = intent.getStringExtra("order_number")
        }
        adapter = DetailsPharmaciesSalesAdapter(null)
        viewModel = ViewModelProvider(this).get(PharmacySalesViewModel::class.java)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getDetailsPharmacySales(order_number!!)
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.DetailsPharmacySalesResponse.observe(this, Observer {
            binding.progressBar.setVisibility(View.GONE)
            binding.edNoOrder.setText(order_number)
            convertDateTime(it!!.order.created_at, binding.edCreatedAt)
            binding.edCustomerName.setText(it!!.order.customer_name)
            binding.edCustomerEmail.setText(it!!.order.customer_email)
            binding.edCustomerPhone.setText(it!!.order.customer_phone)
            binding.edCustomerAddress.setText(it!!.order.customer_address + it!!.order.customer_city)
            binding.edShippingMethod.setText(it!!.order.shipping.name)
            binding.edPaymentStatus.setText(it!!.order.status)
            binding.edPayAmount.setText(it!!.order.pay_amount.toString() + it!!.order.currency_sign)
            binding.edMethod.setText(it!!.order.method)
            binding.edNoOperation.setText(it!!.order.id.toString())
            if (it!!.details == null) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = DetailsPharmaciesSalesAdapter(it.details)
                binding.recyclerDetailsPharmacySales.setAdapter(adapter)
            }
        })
    }

    fun convertDateTime(format: String?, date: TextView) {
        val input = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss")
        val output = SimpleDateFormat("dd/MM/yyyy")
        var d: Date? = null
        try {
            d = input.parse(format)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatted = output.format(d)
        date.text = formatted
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}