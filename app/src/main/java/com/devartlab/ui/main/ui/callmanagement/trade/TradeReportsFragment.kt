package com.devartlab.ui.main.ui.callmanagement.trade

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentTradeReportsBinding
import com.devartlab.model.CardModel
import com.devartlab.ui.main.ui.callmanagement.trade.customerinvoice.CustomerInvoiceReportFragment
import com.devartlab.ui.main.ui.callmanagement.trade.employeeinvoice.EmployeeInvoiceReportFragment
import com.devartlab.ui.main.ui.callmanagement.trade.offlineinvoice.OfflineReportFragment
import com.devartlab.utils.CommonUtilities


class TradeReportsFragment : BaseFragment<FragmentTradeReportsBinding>(), TradeReportsAdapter.OnHomeItemClick {
    lateinit var viewModel: TradeReportsViewModel
    lateinit var binding: FragmentTradeReportsBinding
    lateinit var adapter: TradeReportsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradeReportsViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding

        val list = ArrayList<CardModel>()
        list.add(CardModel(1, "Customer Invoice Report", R.drawable.report_general))
        list.add(CardModel(2, "Offline Invoice Report", R.drawable.report_general))

        adapter = TradeReportsAdapter(baseActivity, list, this)
        val layoutManager = GridLayoutManager(baseActivity, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.recycler?.layoutManager = layoutManager
        binding.recycler.adapter = adapter

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_trade_reports
    }

    override fun setOnHomeItemClick(model: CardModel) {

        when (model.id) {

           1 -> {


                replace_fragment(CustomerInvoiceReportFragment(), "CustomerInvoiceReportFragment")

            }
            2 -> {


                replace_fragment(OfflineReportFragment(), "OfflineReportFragment")

            }
        }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.trade_reports)
        } catch (e: Exception) {

        }

        try {
            CommonUtilities.sendMessage(baseActivity, true)
        } catch (e: Exception) {
        }
    }


}