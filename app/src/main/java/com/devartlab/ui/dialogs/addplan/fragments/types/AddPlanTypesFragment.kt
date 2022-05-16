package com.devartlab.ui.main.ui.callmanagement.list.fragments.types

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.TypesFragmentBinding
import com.devartlab.ui.dialogs.addplan.fragments.types.AddPlanTypesAdapter
import com.devartlab.utils.ProgressLoading

class AddPlanTypesFragment : BaseFragment<TypesFragmentBinding>() , AddPlanTypesAdapter.AddPlanGetList {

    private lateinit var binding: TypesFragmentBinding
    private lateinit var viewModel: AddPlanTypesViewModel
    private lateinit var adapter: AddPlanTypesAdapter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(AddPlanTypesViewModel::class.java)
        adapter = AddPlanTypesAdapter(baseActivity, this)

        binding.typesRecyclerView.layoutManager = GridLayoutManager(baseActivity, 4)
        binding.typesRecyclerView.adapter = adapter

        setObservers()


    }

    private fun setObservers() {
        viewModel!!.responseLive.observe(this, Observer { t ->
            adapter.setMyData(t)
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
        return R.layout.types_fragment
    }

    override fun get_list() {
         Toast.makeText(activity, "" , Toast.LENGTH_SHORT).show()
    }


}
