package com.devartlab.ui.main.ui.market.requests

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentMarketRequestsBinding
import com.devartlab.model.Detail
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.market.MarketRequestViewModel
import com.devartlab.ui.main.ui.market.details.MarketRequestDetailsActivity
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import kotlin.jvm.internal.Intrinsics


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketRequestTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarketRequestsFragment : BaseFragment<FragmentMarketRequestsBinding>(), ChooseEmployeeInterFace, MarketRequestsAdapter.OnRequestClick, View.OnClickListener {

    lateinit var binding: FragmentMarketRequestsBinding
    lateinit var viewModel: MarketRequestViewModel
    lateinit var adapter: MarketRequestsAdapter
    var alertDialog: AlertDialog? = null


    var accID = 0
    var accAddId = ""
    var mainAcc = true

    lateinit var chooseEmployee: ChooseEmployee


    override fun getLayoutId(): Int {
        return R.layout.fragment_market_requests
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MarketRequestViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!


        binding.cost.text = arguments?.getString("TOTAL_COST")!!
        binding.gain.text = arguments?.getString("TOTAL_GAIN")!!
        binding.requestsTypeName.text = arguments?.getString("REQUEST_TPE")!!
        accID = arguments?.getInt("ACC_ID")!!
        accAddId = arguments?.getString("ACC_ADD_ID")!!
        mainAcc = arguments?.getBoolean("MAIN_ACC")!!

        adapter = MarketRequestsAdapter(baseActivity, ArrayList(), this, mainAcc)

        if (!mainAcc) {
            binding.buttonsContainer.visibility = View.GONE
        }


        setObservers()
        setRecyclerViews()

        binding.approveRequests.setOnClickListener(this)
        binding.disApproveRequests.setOnClickListener(this)
        binding.cancelRequests.setOnClickListener(this)


        binding.textSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


                adapter.filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.empSearch.setOnClickListener(View.OnClickListener {

            chooseEmployee = ChooseEmployee(baseActivity, this@MarketRequestsFragment, viewModel.dataManager)
            chooseEmployee.setCanceledOnTouchOutside(true)
            val window = chooseEmployee.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee.show()
        })

        binding.closeSearch.setOnClickListener(View.OnClickListener {

            binding.serachLayout.visibility = View.VISIBLE
            binding.searchedText.visibility = View.GONE
            binding.closeSearch.visibility = View.GONE
            binding.searchedText.setText("")
            binding.textSearch.setText("")


        })

        viewModel.getData(arguments?.getInt("TYPE_INT")!!, accID, accAddId)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.approveRequests -> sendApproval(true, false, false, "Approve")

            R.id.disApproveRequests -> sendApproval(false, true, false, "Cancel")

            R.id.cancelRequests -> sendApproval(false, false, true, "Disapprove")

        }
    }


    private fun setRecyclerViews() {

        binding.recyclerView?.layoutManager = LinearLayoutManager(baseActivity)
        binding.recyclerView?.adapter = adapter

    }

    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            if (it.isSuccesed) {


                if (it.data.summary.isNullOrEmpty()) {

                } else {
                    adapter.setMyData(it.data.details)
                }

            } else {

                Toast.makeText(context, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })

        viewModel.responseLiveApprove.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            if (it.isSuccesed) {

                Toast.makeText(context, it.rerurnMessage, Toast.LENGTH_SHORT).show()

                if (alertDialog != null)
                    alertDialog?.dismiss()
                adapter.setMyData(ArrayList())
                viewModel.getData(arguments?.getInt("TYPE_INT")!!, accID, accAddId)


            } else {

                Toast.makeText(context, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


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

    override fun setOnclickClick(model: Detail) {

        var intent = Intent(baseActivity, MarketRequestDetailsActivity::class.java)
        intent.putExtra("REQUEST_DOC_ID", model.docId)
        intent.putExtra("REQUEST_FLOW_ID", model.docWorkFlowDetId)
        intent.putExtra("REQUEST_TYPE_NAME", model.requestType)
        baseActivity.startActivity(intent)

    }


    fun sendApproval(pApproved: Boolean, pDeny: Boolean, pArchiving: Boolean, actionStatus: String) {


        val dialogBuilder = AlertDialog.Builder(baseActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.confirm_approval, null)
        dialogBuilder.setView(dialogView)


        val noteEditText = dialogView.findViewById<EditText>(R.id.noteEditText)
        val requestsCount = dialogView.findViewById<TextView>(R.id.requestsCount)
        val requestsCost = dialogView.findViewById<TextView>(R.id.requestsCost)
        val dismissButton = dialogView.findViewById<TextView>(R.id.dismissButton)
        val sendButton = dialogView.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.sendButton)
        alertDialog = dialogBuilder.create()


        requestsCount.text = actionStatus + " " + getRequestsCont(adapter.getMyData()).toString()
        requestsCost.text = getRequestsCost(adapter.getMyData()).toString()




        sendButton.setOnClickListener(View.OnClickListener {

            viewModel.sendRequestsApproval(CommonUtilities.getRequestsIds(adapter.getMyData()), pApproved, pDeny, pArchiving, noteEditText.getText().toString(), viewModel.dataManager.user.accId, "android")

        })
        dismissButton.setOnClickListener(
                View.OnClickListener { alertDialog?.dismiss() }
        )

        alertDialog?.show()
    }

    fun getRequestsCont(list: List<Detail?>): Int {
        Intrinsics.checkParameterIsNotNull(list, "list")
        var i = 0
        for (model in list) {
            if (model?.selected!!) {
                i++
            }
        }
        return i
    }

    fun getRequestsCost(list: List<Detail?>): Double {
        Intrinsics.checkParameterIsNotNull(list, "list")
        var i = 0.0
        for (model in list) {
            if (model?.selected!!) {
                val totalCoast: Double = model.totalCoast!!
                Intrinsics.checkExpressionValueIsNotNull(totalCoast, "model.totalCoast")
                i += totalCoast
            }
        }
        return i
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        binding.serachLayout.visibility = View.GONE
        binding.searchedText.visibility = View.VISIBLE
        binding.closeSearch.visibility = View.VISIBLE
        binding.searchedText.setText(model?.empName)

        binding.textSearch.setText("")
        chooseEmployee.dismiss()
        adapter.filterEmp(model?.empId.toString())


    }


}