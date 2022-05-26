package com.devartlab.ui.main.ui.employeeservices.leavework

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.LeaveWorkActivityBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*

class LeaveWorkActivity : BaseActivity<LeaveWorkActivityBinding>(), ChooseEmployeeInterFace {
    lateinit var binding: LeaveWorkActivityBinding
    lateinit var viewModel: LeaveWorkViewModel
    lateinit var chooseEmployee: ChooseEmployee


    private var textinsertleavetime: String? = null
    private var textinsertnote: String? = null
    var addedBy = "Employee"
    var empId = "0"
    var managerId = "0"
    var name = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(LeaveWorkViewModel::class.java)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Leave Work"

        empId = viewModel.dataManager.user.empId.toString()
        managerId = viewModel.dataManager.user.managerId.toString()
        name = viewModel.dataManager.user.nameAr

        binding.leavingTime.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = OnDateSetListener { view, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

                val simpleDateFormat =
                    SimpleDateFormat("yyyy-MM-dd")
                binding.leavingTime.setText(simpleDateFormat.format(calendar.time))

            }
            DatePickerDialog(
                this@LeaveWorkActivity,
                dateSetListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        })
        binding.submitRequest.setOnClickListener(View.OnClickListener {
            textinsertleavetime = binding.leavingTime.getText().toString()
            textinsertnote = binding.notes.getText().toString()
            if (TextUtils.isEmpty(textinsertleavetime) or TextUtils.isEmpty(
                    textinsertnote
                )
            ) {
                Toast.makeText(
                    this@LeaveWorkActivity,
                    "You must fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel!!.leaveWork(
                    empId,
                    name,
                    textinsertleavetime!!,
                    managerId,
                    textinsertnote!!,
                    addedBy
                )
            }
        })


        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(this, this, viewModel.dataManager)
            chooseEmployee.setCanceledOnTouchOutside(true)
            val window = chooseEmployee.window
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee.show()
        }

        setObservers()
        setEmpData()
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


    override fun getLayoutId(): Int {
        return R.layout.leave_work_activity
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee.dismiss()

        addedBy = "Manager"


        binding.empImage.setImageResource(R.drawable.user_logo);

        if (model?.fileImage != null) {
            Glide.with(this)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/" + model.fileImage)
                .placeholder(binding.empImage?.drawable)
                .into(binding.empImage!!)
        } else {
            Glide.with(this)
                .load(viewModel.dataManager.url + "ImageUpload/Employee/DefaultEmpImage.jpg")
                .placeholder(binding.empImage?.drawable)
                .into(binding.empImage!!)
        }

        binding.empName.text = model?.empName
        binding.empId.text = model?.empId.toString()
        binding.empTitle.text = model?.empTitle

        empId = model?.empId.toString()
        name = model?.empName.toString()
        managerId = viewModel.dataManager.user.empId.toString()

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