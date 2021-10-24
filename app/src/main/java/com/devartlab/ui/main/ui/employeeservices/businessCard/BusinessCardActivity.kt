package com.devartlab.ui.main.ui.employeeservices.businessCard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.BusinessCardActivityBinding
 import com.devartlab.ui.main.ui.employeeservices.leavework.LeaveWorkViewModel
import com.devartlab.utils.ProgressLoading

class BusinessCardActivity : BaseActivity<BusinessCardActivityBinding>() {


    lateinit var viewModel: BusinessCardViewModel
    lateinit var binding: BusinessCardActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(BusinessCardViewModel::class.java)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Business Card"

        binding.submitRequest!!.setOnClickListener {
            if (!checkenterdata()) {

                viewModel.businessCard(
                  binding.number.text.toString(),
                  binding.email.text.toString(),
                  binding.notes.text.toString()
                )

            } else {
                Toast.makeText(
                    this@BusinessCardActivity,
                    "You must fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        setEmpData()
        setObservers()
    }


    private fun checkenterdata(): Boolean {
        return  binding.number!!.text.toString().isEmpty() or binding.email!!.text.toString().isEmpty()
    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, androidx.lifecycle.Observer {


            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            finish()


        })


        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {
                    ProgressLoading.dismiss()
                }
                1 -> {
                    ProgressLoading.show(this)
                }
            }
        })


    }
    private fun setEmpData() {
        binding.empImage?.setImageResource(R.drawable.user_logo)
        if (!viewModel.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray =
                Base64.decode(viewModel.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }

        binding.empTitle.text = viewModel.dataManager.user.title
        binding.empName.text = viewModel.dataManager.user.nameAr
        binding.empId.text = viewModel.dataManager.user.empId.toString()
    }

    override fun getLayoutId(): Int {
        return R.layout.business_card_activity
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}