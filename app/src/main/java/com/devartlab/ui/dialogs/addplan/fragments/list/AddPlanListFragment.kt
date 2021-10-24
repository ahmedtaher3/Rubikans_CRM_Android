package com.devartlab.ui.main.ui.callmanagement.list.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.AddPlanListFragmentBinding
import com.devartlab.model.AddPlanList
import com.devartlab.ui.dialogs.addplan.fragments.list.AddPlanListAdapter
import com.devartlab.utils.ProgressLoading

class AddPlanListFragment : BaseFragment<AddPlanListFragmentBinding>() {


    private lateinit var viewModel: AddPlanListkViewModel
    private lateinit var binding: AddPlanListFragmentBinding
    private lateinit var adapter: AddPlanListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddPlanListkViewModel::class.java)
        binding = viewDataBinding
        adapter = AddPlanListAdapter(baseActivity)

        binding.listRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        binding.listRecyclerView.adapter = adapter

        viewModel.getCustomerList(getArguments()?.getString("TYPE_ID")!!)
        binding.addToPlan.setOnClickListener(View.OnClickListener { Toast.makeText(context , adapter.myDataChecked[0].customerList.customerEnName , Toast.LENGTH_SHORT).show() })
        setObservers()
    }

    private fun setObservers() {
        viewModel!!.responseLive.observe(this, Observer { t ->


            var list: ArrayList<AddPlanList> = ArrayList()
            for (model in t) {
                var x  = AddPlanList(false, model)
                list.add(x)
            }
            adapter.setMyData(list)
        })


        viewModel?.progress?.observe(this, Observer { progress ->

            when (progress) {
                0 -> {
                    ProgressLoading.dismiss()
                }
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })


    }

    override fun getLayoutId(): Int {
        return R.layout.add_plan_list_fragment
    }

}
