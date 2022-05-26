package com.devartlab.ui.main.ui.employeeservices.hrrequest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.FragmentEmployeeRequestsBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.GoogleSheetUser
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.employeeservices.hrrequest.filter.FilterRequetsDialog
import com.devartlab.ui.main.ui.employeeservices.hrrequest.filter.OnFilterSelect
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.devartlab.utils.RecyclerTouchListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketRequestTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeRequestsFragment : BaseFragment<FragmentEmployeeRequestsBinding>(), View.OnClickListener, OnFilterSelect, ChooseEmployeeInterFace {

    lateinit var binding: FragmentEmployeeRequestsBinding
    lateinit var viewModel: EmployeeRequestsViewModel
    lateinit var adapter: EmployeeRequestsAdapter
    private lateinit var lastLayout: RelativeLayout
    private lateinit var lastTextView: TextView
    private lateinit var fullList: java.util.ArrayList<GoogleSheetUser>
    private lateinit var filterList: java.util.ArrayList<GoogleSheetUser>
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var filterRequetsDialog: FilterRequetsDialog
    lateinit var touchListener: RecyclerTouchListener


    override fun getLayoutId(): Int {
        return R.layout.fragment_employee_requests
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Requests"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EmployeeRequestsViewModel::class.java)
        adapter = EmployeeRequestsAdapter(baseActivity, ArrayList())
        fullList = ArrayList()
        filterList = ArrayList()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!
        setHasOptionsMenu(true);
        lastLayout = binding.allTab!!
        lastTextView = binding.allTabTextView!!

        setListeners()
        setEmpData()
        setObservers()
        setRecyclerViews()
        CommonUtilities.initializeSSLContext(baseActivity)
        viewModel.getRequests("getAll", "", viewModel.dataManager.user.empId.toString(), "", "", "", "", "", "", "", "", "", "")


    }

    private fun setListeners() {
        binding.allTab?.setOnClickListener(this)
        binding.approvedTab?.setOnClickListener(this)
        binding.pendingTab?.setOnClickListener(this)
        binding.refusedTab?.setOnClickListener(this)
        binding.eznText?.setOnClickListener(this)
        binding.mamoriaText?.setOnClickListener(this)
        binding.extraTimeText?.setOnClickListener(this)
        binding.agazaText?.setOnClickListener(this)
        binding.empImage.setOnClickListener(this)
        binding.requestsFilter.setOnClickListener(this)
        binding.closeFilter.setOnClickListener(this)



        binding.hrRequestsSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            adapter.setMyData(ArrayList())

            binding.allTab?.setBackgroundResource(R.drawable.rounded_primary)
            binding.allTabTextView?.setTextColor(Color.parseColor("#ffffff"))
            lastLayout.setBackgroundResource(R.drawable.rounded_grey)
            lastTextView.setTextColor(Color.parseColor("#353535"))
            lastLayout = binding.allTab!!
            lastTextView = binding.allTabTextView!!


            viewModel.getRequests("getAll", "", viewModel.dataManager.user.empId.toString(), "", "", "", "", "", "", "", "", "", "")
        })
    }

    private fun setEmpData() {
        binding.empImage?.setImageResource(R.drawable.user_logo)
        if (!viewModel.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(viewModel.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }

        binding.empTitle.text = viewModel.dataManager.user.title
        binding.empName.text = viewModel.dataManager.user.nameAr
        binding.empId.text = viewModel.dataManager.user.empId.toString()
    }

    private fun setRecyclerViews() {

        binding.recyclerView?.adapter = adapter


        touchListener = RecyclerTouchListener(baseActivity, binding.recyclerView)
        touchListener.setClickable(object : RecyclerTouchListener.OnRowClickListener {
            override fun onRowClicked(position: Int) {
            }

            override fun onIndependentViewClicked(independentViewID: Int, position: Int) {}
        })
                .setSwipeOptionViews(R.id.delete_task, R.id.edit_task)
                .setSwipeable(R.id.rowFG, R.id.rowBG, object : RecyclerTouchListener.OnSwipeOptionsClickListener {
                    override fun onSwipeOptionClicked(viewID: Int, position: Int) {
                        when (viewID) {
                            R.id.edit_task -> {

                            }

                            R.id.delete_task -> {

                                if (!filterList[position].status.equals("APPROVED") && !filterList[position].status.equals("REFUSED")) {
                                    viewModel.getRequests("delete", "", viewModel.dataManager.user.empId.toString(), "", "", "", "", "", "", "", "", "", filterList[position].code!!)
                                }
                            }
                        }
                    }
                })
        binding.recyclerView.addOnItemTouchListener(touchListener)


    }

    private fun setObservers() {


        viewModel.responseLiveRequests.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            fullList = it.user!!
            filterList = it.user!!
            adapter.setMyData(fullList)


            if (fullList.isNullOrEmpty()) {

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

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.allTab -> {

                if (lastLayout == binding.allTab!!)
                    return

                binding.allTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.allTabTextView?.setTextColor(Color.parseColor("#ffffff"))

                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))

                lastLayout = binding.allTab!!
                lastTextView = binding.allTabTextView!!

                adapter.setMyData(filterList)
            }
            R.id.approvedTab -> {

                if (lastLayout == binding.approvedTab!!)
                    return
                binding.approvedTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.approvedTabTextView?.setTextColor(Color.parseColor("#ffffff"))


                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.approvedTab!!
                lastTextView = binding.approvedTabTextView!!


                var newList = ArrayList<GoogleSheetUser>()
                for (model in filterList) {
                    if (model.status == "APPROVED")
                        newList.add(model)
                }
                adapter.setMyData(newList)


            }
            R.id.pendingTab -> {

                if (lastLayout == binding.pendingTab!!)
                    return

                binding.pendingTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.pendingTabTextView?.setTextColor(Color.parseColor("#ffffff"))


                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.pendingTab!!
                lastTextView = binding.pendingTabTextView!!

                var newList = ArrayList<GoogleSheetUser>()
                for (model in filterList) {
                    if (model.status == "PENDING")
                        newList.add(model)
                }
                adapter.setMyData(newList)

            }
            R.id.refusedTab -> {
                if (lastLayout == binding.refusedTab!!)
                    return

                binding.refusedTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.refusedTabTextView?.setTextColor(Color.parseColor("#ffffff"))


                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.refusedTab!!
                lastTextView = binding.refusedTabTextView!!

                var newList = ArrayList<GoogleSheetUser>()
                for (model in filterList) {
                    if (model.status == "REFUSED")
                        newList.add(model)
                }
                adapter.setMyData(newList)
            }

            R.id.eznText -> {

                val intent = Intent(baseActivity, AddNewRequest::class.java)
                intent.putExtra("REQUEST_TYPE", RequestType.أذن.toString())
                startActivityForResult(intent, 1)
                binding.floatingActionsMenu.collapse()
            }
            R.id.mamoriaText -> {
                val intent = Intent(baseActivity, AddNewRequest::class.java)
                intent.putExtra("REQUEST_TYPE", RequestType.مأمورية.toString())
                startActivityForResult(intent, 1)
                binding.floatingActionsMenu.collapse()
            }
            R.id.extraTimeText -> {
                val intent = Intent(baseActivity, AddNewRequest::class.java)
                intent.putExtra("REQUEST_TYPE", RequestType.وقت_إضافى.toString())
                startActivityForResult(intent, 1)
                binding.floatingActionsMenu.collapse()
            }
            R.id.agazaText -> {
                val intent = Intent(baseActivity, AddNewRequest::class.java)
                intent.putExtra("REQUEST_TYPE", RequestType.أجازة.toString())
                startActivityForResult(intent, 1)
                binding.floatingActionsMenu.collapse()
            }

            R.id.empImage -> {

                chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
                chooseEmployee.setCanceledOnTouchOutside(true)
                val window = chooseEmployee.window
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
                chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                chooseEmployee.show()
            }
            R.id.requestsFilter -> {

                Toast.makeText(baseActivity, fullList.size.toString(), Toast.LENGTH_SHORT).show()



                filterRequetsDialog = FilterRequetsDialog(baseActivity, this)
                filterRequetsDialog.setCanceledOnTouchOutside(true)
                val window = filterRequetsDialog.window
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                filterRequetsDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                filterRequetsDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                filterRequetsDialog.show()
            }
            R.id.closeFilter -> {

                binding.requestsFilter.clearColorFilter()
                binding.closeFilter.visibility = View.GONE
                binding.filterLayout?.setBackgroundResource(0)
                adapter.setMyData(fullList)

                filterList = fullList

                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))
                binding.allTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.allTabTextView?.setTextColor(Color.parseColor("#ffffff"))
                lastLayout = binding.allTab!!
                lastTextView = binding.allTabTextView!!

            }

        }

    }

    private fun replace_fragment(fragment: Fragment, s: String) {

        baseActivity.supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
        )
                .add(
                        R.id.Container,
                        fragment
                )
                .addToBackStack("s")
                .commit()
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee.dismiss()
        binding.recyclerView.removeOnItemTouchListener(touchListener)
        binding.empImage.setImageResource(R.drawable.user_logo);

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

        binding.empName.text = model?.empName
        binding.empId.text = model?.empId.toString()
        binding.empTitle.text = model?.empTitle
        binding.floatingActionsMenu.visibility = View.GONE
        viewModel.getRequests("getAll", "", model?.empId.toString(), "", "", "", "", "", "", "", "", "", "")
    }

    override fun setOnFilterSelect(typeName: String, fromMS: Long, toMS: Long) {

        filterRequetsDialog.dismiss()
        binding.requestsFilter.setColorFilter(ContextCompat.getColor(baseActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        binding.closeFilter.visibility = View.VISIBLE
        binding.filterLayout?.setBackgroundResource(R.drawable.rounded_primary_alpha)


        var newFromMs = fromMS
        var newToMS = toMS


        if (newFromMs.equals(0)) {
            newFromMs = CommonUtilities.currentToMillis
        }

        if (newToMS.equals(0)) {
            newToMS = CommonUtilities.currentToMillis
        }


        var newList = java.util.ArrayList<GoogleSheetUser>()
        for (model in fullList) {


            System.out.println("Filter LOGS  " + typeName + "    " + model.typeRequest)

            if (!typeName.isNullOrEmpty()) {
                if (model.typeRequest?.contains(typeName)!!)
                    newList.add(model)

            }

            if (fromMS.equals(0) && toMS.equals(0)) {

            } else {
                if (CommonUtilities.convertFullDateToMillis(model.requestStartIn) < newFromMs && CommonUtilities.convertFullDateToMillis(model.requestEndIn) < newToMS)
                    newList.add(model)
            }


        }
        filterList = newList
        adapter.setMyData(filterList)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.getRequests("getAll", "", viewModel.dataManager.user.empId.toString(), "", "", "", "", "", "", "", "", "", "")
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }


    }


}