package com.devartlab.ui.main.ui.trade

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityNewCustomerBinding
import com.devartlab.utils.ProgressLoading

class NewCustomerActivity : BaseActivity<ActivityNewCustomerBinding>() {

    lateinit var binding: ActivityNewCustomerBinding
    lateinit var viewModel: TradeViewModel
    var governmentNamesList = ArrayList<String>()
    var cityNamesList = ArrayList<String>()
    var areaNamesList = ArrayList<String>()

    var governmentIDSList = ArrayList<Int>()
    var cityIDSList = ArrayList<Int>()
    var areaIDSList = ArrayList<Int>()

    var governmentID = 0
    var cityID = 0
    var areaID = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_new_customer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)

        binding.newGovernment.setOnClickListener { showAddDialog(1) }
        binding.newCity.setOnClickListener { showAddDialog(2)  }
        binding.newArea.setOnClickListener { showAddDialog(3)  }


        setUpSpinners()
        setObservers()


    }

    private fun setUpSpinners() {
        val list = ArrayList<String>()

        binding.governmentSpinner.setTitle("Select Government");
        binding.governmentSpinner.setPositiveButton("OK");
        binding.governmentSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list)
        binding.governmentSpinner.setSelection(0, false)
        binding.governmentSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                governmentID = governmentIDSList[position]

                viewModel.getCities("getAll", governmentID.toString(), "", "")
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        binding.citySpinner.setTitle("Select City");
        binding.citySpinner.setPositiveButton("OK");
        binding.citySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list)
        binding.governmentSpinner.setSelection(0, false)
        binding.citySpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                cityID = cityIDSList[position]
               // viewModel.getAreas("getAll", cityIDSList[position].toString(), "")
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })


        binding.areaSpinner.setTitle("Select Area");
        binding.areaSpinner.setPositiveButton("OK");
        binding.areaSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list)
        binding.governmentSpinner.setSelection(0, false)
        binding.areaSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                areaID = areaIDSList[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        viewModel.getGovernments()

    }

    private fun setObservers() {

        viewModel.progress.observe(this, Observer {
            when (it) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })

        viewModel.governmentsLive.observe(this, Observer {
            governmentNamesList.clear()
            governmentIDSList.clear()
            cityNamesList.clear()
            cityIDSList.clear()
            areaNamesList.clear()
            areaIDSList.clear()

            for (model in it.governments!!) {
                governmentNamesList.add(model.name)
                governmentIDSList.add(model.id)

            }

            for (model in it.cities!!) {
                cityNamesList.add(model.name)
                cityIDSList.add(model.id)

            }

            for (model in it.areas!!) {
                areaNamesList.add(model.name)
                areaIDSList.add(model.id)

            }

            binding.governmentSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, governmentNamesList)
            binding.citySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cityNamesList)
            binding.areaSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, areaNamesList)


        })

        viewModel.cityLive.observe(this, Observer {

            cityNamesList.clear()
            cityIDSList.clear()
            for (model in it.cities!!) {
                cityNamesList.add(model.name)
                cityIDSList.add(model.id)

            }

            binding.citySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cityNamesList)


        })

        viewModel.areaLive.observe(this, Observer {
            areaNamesList.clear()
            areaIDSList.clear()
            for (model in it.areas!!) {
                areaNamesList.add(model.name)
                areaIDSList.add(model.id)

            }

            binding.areaSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, areaNamesList)


        })
    }


    fun showAddDialog(flag: Int) {
       /* val addNewDialog = AddNewDialog(this,this, flag, governmentNamesList, governmentIDSList, cityNamesList, cityIDSList, viewModel.myAPI!!);
        addNewDialog.setCanceledOnTouchOutside(true);
        val window = addNewDialog.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        addNewDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        addNewDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        addNewDialog.show();*/
    }
}