package com.devartlab.ui.main.ui.employeeservices.approval

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentApprovalRequestsBinding
import com.devartlab.model.GoogleSheetUser
import com.devartlab.model.PenaltiesGoogle
import com.devartlab.model.WorkFromHomeModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketRequestTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "ApprovalRequestsFragmen"

class ApprovalRequestsFragment : BaseFragment<FragmentApprovalRequestsBinding>(),
    View.OnClickListener, ApprovalRequestsAdapter.OnApprovalItemClick,
    ApprovalWorkFromHomeAdapter.OnWorkFromHomeApprovalItemClick,
    ApprovalPenaltiesAdapter.OnPenaltiesApprovalItemClick, ApproveInterface {

    lateinit var binding: FragmentApprovalRequestsBinding
    lateinit var viewModel: ApproveRequestsViewModel
    lateinit var adapter: ApprovalRequestsAdapter
    lateinit var approvalWorkFromHomeAdapter: ApprovalWorkFromHomeAdapter
    lateinit var approvalPenaltiesAdapter: ApprovalPenaltiesAdapter


    val approveList = ArrayList<ApproveModel>()

    private lateinit var fullList: java.util.ArrayList<GoogleSheetUser>

    override fun getLayoutId(): Int {
        return R.layout.fragment_approval_requests
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ApproveRequestsViewModel::class.java)
        adapter = ApprovalRequestsAdapter(baseActivity, ArrayList(), this, this)
        approvalWorkFromHomeAdapter =
            ApprovalWorkFromHomeAdapter(baseActivity, ArrayList(), this, this)
        approvalPenaltiesAdapter = ApprovalPenaltiesAdapter(baseActivity, ArrayList(), this, this)
        fullList = ArrayList()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding




        binding.closeRequests.setOnClickListener {

            binding.recyclerView.visibility = View.GONE
            binding.closeRequests.visibility = View.GONE
            binding.openRequests.visibility = View.VISIBLE
        }
        binding.closePenalties.setOnClickListener {
            binding.recyclerViewPenalties.visibility = View.GONE
            binding.closePenalties.visibility = View.GONE
            binding.openPenalties.visibility = View.VISIBLE
        }
        binding.closeWork.setOnClickListener {

            binding.recyclerViewWorkFromHome.visibility = View.GONE
            binding.closeWork.visibility = View.GONE
            binding.openWork.visibility = View.VISIBLE
        }


        binding.openRequests.setOnClickListener {

            binding.recyclerView.visibility = View.VISIBLE
            binding.closeRequests.visibility = View.VISIBLE
            binding.openRequests.visibility = View.GONE
        }
        binding.openPenalties.setOnClickListener {
            binding.recyclerViewPenalties.visibility = View.VISIBLE
            binding.closePenalties.visibility = View.VISIBLE
            binding.openPenalties.visibility = View.GONE
        }
        binding.openWork.setOnClickListener {
            binding.recyclerViewWorkFromHome.visibility = View.VISIBLE
            binding.closeWork.visibility = View.VISIBLE
            binding.openWork.visibility = View.GONE
        }


        binding.approveAll.setOnClickListener {

            viewModel.approveAll(approveList)
        }

        setObservers()
        setRecyclerViews()
        ProgressLoading.show(baseActivity)
        viewModel.getAll("allPending", "")
    }

    private fun setRecyclerViews() {

        binding.recyclerView?.adapter = adapter
        binding.recyclerViewWorkFromHome?.adapter = approvalWorkFromHomeAdapter
        binding.recyclerViewPenalties?.adapter = approvalPenaltiesAdapter

    }

    private fun setObservers() {


        viewModel.PendingResponseLiveRequests.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                fullList = it.hrRequests!!
                adapter.setMyData(fullList)
                approvalWorkFromHomeAdapter.setMyData(it.workFromHomelist!!)
                approvalPenaltiesAdapter.setMyData(it.penaltiesGoogle!!)


            })


        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    try {
                        ProgressLoading.dismiss()
                    } catch (e: Exception) {
                    }

                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })

    }

    override fun onClick(p0: View?) {

        when (p0?.id) {


        }

    }

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)
    override fun setOnApprovalItemClick(status: String, model: GoogleSheetUser) {


        val dialogBuilder = AlertDialog.Builder(baseActivity)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.finish_visit, null)
        dialogBuilder.setView(dialogView)
        val noteEditText = dialogView.findViewById<View>(R.id.noteEditText) as EditText
        val addButton = dialogView.findViewById<View>(R.id.addButton) as Button
        val skipButton = dialogView.findViewById<View>(R.id.skipButton) as TextView

        val alertDialog = dialogBuilder.create()
        addButton.setOnClickListener {
            viewModel.approve(
                "HR Requests",
                "approve",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                status,
                noteEditText.text.toString(),
                simpleDateFormat?.format(System.currentTimeMillis()),
                model.code!!
            )
            alertDialog.dismiss()

        }
        skipButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Approval"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

    override fun setWorkFromHomeApprovalItemClick(status: String, model: WorkFromHomeModel) {


        val dialogBuilder = AlertDialog.Builder(baseActivity)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.finish_visit, null)
        dialogBuilder.setView(dialogView)
        val noteEditText = dialogView.findViewById<View>(R.id.noteEditText) as EditText
        val addButton = dialogView.findViewById<View>(R.id.addButton) as Button
        val skipButton = dialogView.findViewById<View>(R.id.skipButton) as TextView

        val alertDialog = dialogBuilder.create()
        addButton.setOnClickListener {
            viewModel.approve(
                "Work From Home",
                "approve",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                status,
                noteEditText.text.toString(),
                simpleDateFormat?.format(System.currentTimeMillis()),
                model.code!!
            )

            alertDialog.dismiss()

        }
        skipButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()


    }

    override fun setOnPenaltiesApprovalItemClick(status: String, model: PenaltiesGoogle) {
        val dialogBuilder = AlertDialog.Builder(baseActivity)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.finish_visit, null)
        dialogBuilder.setView(dialogView)
        val noteEditText = dialogView.findViewById<View>(R.id.noteEditText) as EditText
        val addButton = dialogView.findViewById<View>(R.id.addButton) as Button
        val skipButton = dialogView.findViewById<View>(R.id.skipButton) as TextView

        val alertDialog = dialogBuilder.create()
        addButton.setOnClickListener {
            viewModel.approve(
                "Penalties",
                "approve",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                status,
                noteEditText.text.toString(),
                simpleDateFormat?.format(System.currentTimeMillis()),
                model.code!!
            )

            alertDialog.dismiss()

        }
        skipButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun addApprove(model: ApproveModel) {
        approveList.add(model)
        Log.d(TAG, "addApprove: ${approveList.size}")
        if (approveList.isEmpty())
            binding.approveLayout.visibility = View.GONE
        else
            binding.approveLayout.visibility = View.VISIBLE
    }

    override fun removeApprove(model: ApproveModel) {
        approveList.remove(model)
        Log.d(TAG, "addApprove: ${approveList.size}")
        if (approveList.isEmpty())
            binding.approveLayout.visibility = View.GONE
        else
            binding.approveLayout.visibility = View.VISIBLE
    }

}