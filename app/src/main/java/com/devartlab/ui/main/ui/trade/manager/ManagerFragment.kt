package com.devartlab.ui.main.ui.trade.manager

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.FragmentManagerBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.TradeDay
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.utils.ProgressLoading
import kotlin.collections.ArrayList


class ManagerFragment : BaseFragment<FragmentManagerBinding>(), ChooseEmployeeInterFace, CustomersAdapter.OnDaySelect {

    lateinit var binding: FragmentManagerBinding
    lateinit var viewModel: TradeViewModel
    lateinit var adapter: CustomersAdapter
    lateinit var chooseEmployee: ChooseEmployee
    var empModel: FilterDataEntity? = null

    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)
        adapter = CustomersAdapter(baseActivity, ArrayList(), this)
        selectEmp()

        if (empModel != null) {
            binding.empName.setText(empModel?.empName)
            binding.empTitle.setText(empModel?.empTitle)
            binding.empImage?.setImageResource(R.drawable.user_logo)
            if (!empModel?.fileImage.isNullOrEmpty()) {
                val decodedString: ByteArray = Base64.decode(empModel?.fileImage, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                binding.empImage?.setImageBitmap(decodedByte)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding = viewDataBinding

        binding.days.adapter = adapter


        binding.empImage.setOnClickListener {
            selectEmp()
        }

        setObservers()
    }


    private fun setObservers() {

        viewModel.progress.observe(this, androidx.lifecycle.Observer {
            when (it) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })

        viewModel.tradeDayLive.observe(this, androidx.lifecycle.Observer {

            adapter.setMyData(it.tradeReports!!)
        })


    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_manager
    }


    fun selectEmp() {

        chooseEmployee = ChooseEmployee(baseActivity, this@ManagerFragment, viewModel?.dataManager!!);
        chooseEmployee.setCanceledOnTouchOutside(true);
        val window = chooseEmployee.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        chooseEmployee.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseEmployee.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseEmployee.show();

    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee.dismiss()

        empModel = model
        binding.empName.setText(model?.empName)
        binding.empTitle.setText(model?.empTitle)
        binding.empImage?.setImageResource(R.drawable.user_logo)
        if (model?.fileImage != null) {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }

        viewModel.getEmpDays(model?.empId.toString())
    }

    override fun setOnDaySelect(model: TradeDay) {

        val intent = Intent(baseActivity, DayMapActivity::class.java)
        intent.putExtra("TradeDay", model)
        baseActivity.startActivity(intent)

    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {
        baseActivity.supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.main_container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }

}