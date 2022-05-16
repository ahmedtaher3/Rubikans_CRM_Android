package com.devartlab.ui.main.ui.employeeservices.hrrequest

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityAddNewRequestBinding
import com.devartlab.utils.ProgressLoading
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddNewRequest : BaseActivity<ActivityAddNewRequestBinding>() {

    lateinit var binding: ActivityAddNewRequestBinding
    lateinit var viewModel: EmployeeRequestsViewModel
    lateinit var simpleDateFormat: SimpleDateFormat

    var requestType = ""
    var vacationType = "إعتيادي"


    override fun getLayoutId(): Int {
        return R.layout.activity_add_new_request
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(EmployeeRequestsViewModel::class.java)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' // 'hh:mm a", Locale.US)
        requestType = intent.getStringExtra("REQUEST_TYPE")!!
        setObservers()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = requestType
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.spinnerTypeofVaction.setVisibility(View.INVISIBLE)


        if (requestType == RequestType.أجازة.toString()) {
            binding.spinnerTypeofVaction.setVisibility(View.VISIBLE)
        } else {
            binding.spinnerTypeofVaction.setVisibility(View.INVISIBLE)
        }


        binding.fromDateEdittext.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = OnDateSetListener { view, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val timeSetListener = OnTimeSetListener { view, hourOfDay, minute ->
                    calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                    calendar[Calendar.MINUTE] = minute
                    binding.fromDateEdittext.setText(simpleDateFormat.format(calendar.time))
                }
                TimePickerDialog(this@AddNewRequest, timeSetListener, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false).show()
            }
            DatePickerDialog(this@AddNewRequest, dateSetListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        })

        binding.toDateEdittext.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = OnDateSetListener { view, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val timeSetListener = OnTimeSetListener { view, hourOfDay, minute ->
                    calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                    calendar[Calendar.MINUTE] = minute
                    binding.toDateEdittext.setText(simpleDateFormat.format(calendar.time))
                }
                TimePickerDialog(this@AddNewRequest, timeSetListener, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false).show()
            }
            DatePickerDialog(this@AddNewRequest, dateSetListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        })




        DefinespinnerofVacations()
        addrequest()

        if (!isconnected()) {
            Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show()
        }


    }


    private fun addrequest() {
        binding.submitRequest.setOnClickListener(View.OnClickListener {


            if (TextUtils.isEmpty(binding.fromDateEdittext.getText().toString()) or TextUtils.isEmpty(binding.toDateEdittext.getText().toString())) {
                Toast.makeText(this@AddNewRequest, "You must fill all fields", Toast.LENGTH_SHORT).show()
            } else {

                if (requestType == RequestType.أجازة.toString())
                    requestType = requestType + " " + vacationType

                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = Date()
                val Timestamp = dateFormat.format(date)

                if (viewModel.dataManager.user.managerId == 0)
                    Toast.makeText(this, "his task cannot be completed, please contact Technical Support", Toast.LENGTH_SHORT).show()
                else
                    viewModel.getRequests("insert", Timestamp, viewModel.dataManager.user.empId.toString(), viewModel.dataManager.user.nameAr, requestType, binding.fromDateEdittext.getText().toString(), binding.toDateEdittext.getText().toString(), binding.notesEdittext.text.toString(), viewModel.dataManager.user.managerId.toString(), "PENDING", "", "", "")

            }
        })
    }


    private fun DefinespinnerofVacations() {
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.vacationname, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTypeofVaction.setAdapter(adapter)
        binding.spinnerTypeofVaction.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                vacationType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }

    fun isconnected(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun back(view: View?) {
        finish()
    }

    private fun setObservers() {


        viewModel.responseLive.observe(this, androidx.lifecycle.Observer {


            val returnIntent = Intent()
            setResult(Activity.RESULT_OK, returnIntent)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}