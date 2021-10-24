package com.devartlab.ui.main.ui.employeeservices.penalties.google

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentGetMyPenaltiesBinding
import com.devartlab.model.PenaltiesGoogle
import com.devartlab.ui.main.ui.employeeservices.penalties.PenaltiesViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import java.util.*


class GetMyPenaltiesFragment : BaseFragment<FragmentGetMyPenaltiesBinding>() {

    lateinit var binding: FragmentGetMyPenaltiesBinding
    lateinit var viewModel: PenaltiesViewModel
    lateinit var adapter: AllMyPenaltiesAdapter
    lateinit var fulList: ArrayList<PenaltiesGoogle>

    override fun getLayoutId(): Int {
        return R.layout.fragment_get_my_penalties
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PenaltiesViewModel::class.java)
        adapter = AllMyPenaltiesAdapter(baseActivity)
        fulList = ArrayList()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding



        binding.hrRequestsSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.getMyPenalties()
        })

        setRecyclerViews()
        viewModel.getMyPenalties()
        setObservers()


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Penalties"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

    private fun setRecyclerViews() {

        binding.recyclerView.adapter = adapter


    }


    private fun setObservers() {


        viewModel.responseLiveInsert.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            fulList = it.penaltiesGoogle!!
            adapter.setMyData(fulList)


            if (fulList.isNullOrEmpty()) {

                binding.emptyList.visibility = View.VISIBLE

            } else {
                binding.emptyList.visibility = View.GONE

            }
        })


        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()

                    if (binding.hrRequestsSwipeRefreshLayout.isRefreshing)
                        binding.hrRequestsSwipeRefreshLayout.isRefreshing = false
                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })

    }


}