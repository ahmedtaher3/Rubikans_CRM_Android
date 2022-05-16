package com.devartlab.ui.main.ui.callmanagement.employee.temp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentTempReportBinding
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportViewModel
import com.devartlab.utils.ProgressLoading

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [TempReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TempReportFragment : BaseFragment<FragmentTempReportBinding>() {

    lateinit var binding: FragmentTempReportBinding
    lateinit var viewModel: EmployeeReportViewModel
    lateinit var adapter: TempReportAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_temp_report
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
        viewModel = ViewModelProviders.of(this).get(EmployeeReportViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        setupRecyclerView()
        setObservers()

        viewModel.getDailyReport(true, param1?.toInt()!! , param2?.toLong()!!, param3?.toInt()!!)

    }


    private fun setupRecyclerView() {
        adapter = TempReportAdapter(baseActivity, viewModel.dataManager!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseActivity)
        binding.recyclerView.adapter = adapter

    }

    private fun setObservers() {

        viewModel.responseLiveDay.observe(this, Observer { it ->


            if (it.isSuccesed) {

                it.data.employeeReport

                /*     if (it.data.employeeReport.isNullOrEmpty()) {
                    binding.emptyList.visibility = View.VISIBLE
                } else {
                    binding.emptyList.visibility = View.GONE
                }*/



                adapter.setMyData(it.data.employeeReport, it.data.startEndPoint[0], it.data.startEndPoint[1])


            }

        })


        /*viewModel.progress.observe(this, Observer { progress ->

            when (progress) {
                0 -> {

                    Single.timer(1, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : SingleObserver<Long?> {
                                override fun onSubscribe(d: Disposable) {}

                                override fun onError(e: Throwable) {}
                                override fun onSuccess(t: Long) {
                                    ProgressLoading.dismiss()
                                    //    (DATE!!, Shift)
                                    viewModel.getDayPlan(DATE!!, Shift)
                                }
                            })

                }
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })*/

        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TempReportFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
                TempReportFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                        putString(ARG_PARAM3, param3)
                    }
                }
    }

}