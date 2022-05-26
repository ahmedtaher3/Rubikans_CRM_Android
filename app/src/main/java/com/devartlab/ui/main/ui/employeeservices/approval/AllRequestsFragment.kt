package com.devartlab.ui.main.ui.employeeservices.approval

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.FragmentAllRequestsBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.GoogleSheetUser
import com.devartlab.model.PenaltiesGoogle
import com.devartlab.model.WorkFromHomeModel
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList



class AllRequestsFragment : BaseFragment<FragmentAllRequestsBinding>(), ChooseEmployeeInterFace, View.OnClickListener, ApprovalRequestsAdapter.OnApprovalItemClick, ApprovalWorkFromHomeAdapter.OnWorkFromHomeApprovalItemClick, ApprovalPenaltiesAdapter.OnPenaltiesApprovalItemClick , ApproveInterface {



    lateinit var chooseEmployee: ChooseEmployee
    lateinit var binding: FragmentAllRequestsBinding
    lateinit var viewModel: ApproveRequestsViewModel
    lateinit var adapter: ApprovalRequestsAdapter
    lateinit var approvalWorkFromHomeAdapter: ApprovalWorkFromHomeAdapter
    lateinit var approvalPenaltiesAdapter: ApprovalPenaltiesAdapter

    private lateinit var fullList: java.util.ArrayList<GoogleSheetUser>

    override fun getLayoutId(): Int {
        return R.layout.fragment_all_requests
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ApproveRequestsViewModel::class.java)
        adapter = ApprovalRequestsAdapter(baseActivity, ArrayList(), this,this)
        approvalWorkFromHomeAdapter = ApprovalWorkFromHomeAdapter(baseActivity, ArrayList(), this,this)
        approvalPenaltiesAdapter = ApprovalPenaltiesAdapter(baseActivity, ArrayList(), this,this)
        fullList = ArrayList()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!


        val model = arguments?.getParcelable<FilterDataEntity?>("EMP_MODEL")
        binding.empName.text = model?.empName
        binding.empTitle.text = model?.empTitle




        binding.approveRequestsSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            ProgressLoading.show(baseActivity)
            viewModel.getAll("all", model?.empId.toString()!!)
        })


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


        binding.empImage.setOnClickListener {
            chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
            chooseEmployee.setCanceledOnTouchOutside(true)
            val window = chooseEmployee.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee.show()


        }


        setObservers()
        setRecyclerViews()
        ProgressLoading.show(baseActivity)

        viewModel.getAll("all", model?.empId.toString()!!)
    }

    private fun setRecyclerViews() {

        binding.recyclerView?.adapter = adapter
        binding.recyclerViewWorkFromHome?.adapter = approvalWorkFromHomeAdapter
        binding.recyclerViewPenalties?.adapter = approvalPenaltiesAdapter

    }

    private fun setObservers() {


        viewModel.PendingResponseLiveRequests.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            fullList = it.hrRequests!!
            adapter.setMyData(fullList)
            approvalWorkFromHomeAdapter.setMyData(it.workFromHomelist!!)
            approvalPenaltiesAdapter.setMyData(it.penaltiesGoogle!!)


        })


        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                    if (binding.approveRequestsSwipeRefreshLayout.isRefreshing)
                        binding.approveRequestsSwipeRefreshLayout.isRefreshing = false
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

    override fun setOnApprovalItemClick(status: String, model: GoogleSheetUser) {}

    override fun setWorkFromHomeApprovalItemClick(status: String, model: WorkFromHomeModel) {


    }

    override fun setOnPenaltiesApprovalItemClick(status: String, model: PenaltiesGoogle) {

    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee.dismiss()
        binding.empName.text = model?.empName
        binding.empTitle.text = model?.empTitle

        binding.empImage!!.setImageResource(R.drawable.user_logo)
        if (model?.fileImage != null) {
            Glide.with(baseActivity)
                    .load(viewModel.dataManager.url + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(baseActivity)
                    .load(viewModel.dataManager.url + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }


        ProgressLoading.show(baseActivity)
        viewModel.getAll("all", model?.empId.toString()!!)
    }

    override fun addApprove(model: ApproveModel) {
        TODO("Not yet implemented")
    }

    override fun removeApprove(model: ApproveModel) {
        TODO("Not yet implemented")
    }

}