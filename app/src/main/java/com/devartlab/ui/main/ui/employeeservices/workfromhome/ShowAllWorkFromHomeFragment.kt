package com.devartlab.ui.main.ui.employeeservices.workfromhome

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentAllWorkFromHomeBinding
import com.devartlab.databinding.FragmentWorkFromHomeBinding
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ShowAllWorkFromHomeFragment : BaseFragment<FragmentAllWorkFromHomeBinding>() {

    lateinit var binding: FragmentAllWorkFromHomeBinding
    lateinit var viewModel: WorkFromHomeViewModel
    lateinit var adapter: WorkFromHomeAdapter

    var started: Boolean = false
    var ended: Boolean = false
    var code: String = ""
    private var DATE: String? = null
    var fmt: SimpleDateFormat? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_all_work_from_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WorkFromHomeViewModel::class.java)
        adapter = WorkFromHomeAdapter(baseActivity , ArrayList())
        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding
        binding.recyclerView.adapter = adapter
        setObservers()

        viewModel.getAll()

    }

    private fun setObservers() {

        viewModel.responseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            adapter .setMyData(it.workFromHomelist!!)

        })


        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Previous Work From Home"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }
}