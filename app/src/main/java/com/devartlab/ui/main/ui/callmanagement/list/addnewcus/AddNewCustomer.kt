package com.devartlab.ui.main.ui.callmanagement.list.addnewcus

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.databinding.ActivityAddNewCustomerBinding
import com.devartlab.model.CustomerList
import com.devartlab.ui.main.ui.callmanagement.list.addnewcus.filterlist.FilterNewListFragment
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.FilterInterface
import com.devartlab.utils.ProgressLoading

class AddNewCustomer : BaseActivity<ActivityAddNewCustomerBinding>(), AddNewListAdapter.AddToList , FilterInterface {

    lateinit var binding: ActivityAddNewCustomerBinding
    lateinit var listTypesEntity: ListTypesEntity
    lateinit var viewModel: AddNewListViewModel
    lateinit var adapter: AddNewListAdapter
    lateinit var selectedAdapter: SelectedNewListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(AddNewListViewModel::class.java)
        adapter = AddNewListAdapter(this, this, viewModel.dataManager)
        selectedAdapter = SelectedNewListAdapter(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add new customer"

        listTypesEntity = intent?.getSerializableExtra("LIST_TYPE_MODEL") as ListTypesEntity
        viewModel.getData(listTypesEntity.assigntId.toString(), listTypesEntity.listTypeId.toString(), "0", "0", "0", "0", "0")

        binding.CustomersList.layoutManager = LinearLayoutManager(this)
        binding.CustomersList.adapter = adapter
        binding.selectedPlan.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.selectedPlan.adapter = selectedAdapter


        binding?.editTextSearch?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (TextUtils.isEmpty(s)) {
                    viewModel.getDataWithoutLoading(listTypesEntity.assigntId.toString(), listTypesEntity.listTypeId.toString(), "0", "0", "0", "0", "0")

                } else {
                    viewModel.getDataWithoutLoading(listTypesEntity.assigntId.toString(), listTypesEntity.listTypeId.toString(), "0", "0", "0", "0", s.toString())

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // TODO Auto-generated method stub
            }
        })



        binding.addList.setOnClickListener(View.OnClickListener {
            for (model in selectedAdapter?.myData!!) {

                viewModel.insert(model ,listTypesEntity )


            }
            finish()
        })

        setObservers()
    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, Observer {
            if (it.isNullOrEmpty())
            {
                binding.emptyList.visibility = View.VISIBLE
            }
            else
            {
                binding.emptyList.visibility = View.GONE
            }
            adapter.setMyData(it)

        })


        viewModel.progress.observe(this, Observer { progress ->

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
        return R.layout.activity_add_new_customer
    }

    override fun addToList(customerList: CustomerList?) {
        selectedAdapter.addItem(customerList)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_new, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {

                val fm: FragmentManager = getSupportFragmentManager()
                val filterListFragment: FilterNewListFragment = FilterNewListFragment(this , listTypesEntity.assigntId.toString())
                filterListFragment.show(fm, "fragment_edit_name")
                true

            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun applyFilter(brik: String?, line: String?, territory: String?, speciality: String?, classs: String?) {
        viewModel.getData(listTypesEntity.assigntId.toString(), listTypesEntity.listTypeId.toString(), brik!!, speciality!!, classs!!, "0", "0")
    }


}
