package com.devartlab.ui.main.ui.trade

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentTradeReportBinding
import com.devartlab.model.Bill
import com.devartlab.utils.ProgressLoading

class TradeReportFragment : BaseFragment<FragmentTradeReportBinding>(), TradeReportAdapter.OnBillSelect {


    lateinit var binding: FragmentTradeReportBinding
    lateinit var viewModel: TradeViewModel
    lateinit var adapter: TradeReportAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_trade_report
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)
        adapter = TradeReportAdapter(baseActivity, ArrayList(), this)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding
        binding.recyclerView.adapter = adapter

        viewModel.getBills()
        setObservers()
    }

    private fun setObservers() {

        viewModel.billLive.observe(viewLifecycleOwner, Observer {

            Toast.makeText(baseActivity,it.message, Toast.LENGTH_LONG).show()

            adapter.setMyData(it.bills!!)

        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {

            when (it) {
                0 -> {
                    ProgressLoading.dismiss()
                }

                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })


    }

    override fun setOnBillSelect(model: Bill) {


    }


}