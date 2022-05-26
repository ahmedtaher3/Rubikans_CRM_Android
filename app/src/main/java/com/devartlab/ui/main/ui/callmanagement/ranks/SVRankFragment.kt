package com.devartlab.ui.main.ui.callmanagement.ranks

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.databinding.SvRankFragmentBinding
import com.devartlab.model.Cycle
import com.devartlab.model.SVRank
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.cycles.ChangeCycleAll
import com.devartlab.ui.main.ui.cycles.ChangeCycleInterface
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading


class SVRankFragment : BaseFragment<SvRankFragmentBinding>(), SVRankAdapter.OnItemSelect, ChooseEmployeeInterFace, ChangeCycleInterface {
    private lateinit var viewModel: RanksViewModel
    private lateinit var binding: SvRankFragmentBinding
    private lateinit var adapter: SVRankAdapter
    var chooseEmployee: ChooseEmployee? = null
    var changeCycle: ChangeCycleAll? = null
    var fromDate = ""
    var toDate = ""
    var cycleId = 0
    var accId = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        adapter = SVRankAdapter(baseActivity, ArrayList(), this , viewModel.dataManager)
    }

    override fun getLayoutId(): Int {
        return R.layout.sv_rank_fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
        fromDate = viewModel.dataManager.newOldCycle.currentCycleFromDate?.take(10)!!
        toDate = viewModel.dataManager.newOldCycle.currentCycleToDate?.take(10)!!
        cycleId = viewModel.dataManager.newOldCycle.currentCycleId
        accId = viewModel.dataManager.user.accId.toString()


        binding.recyclerView.adapter = adapter

        viewModel.getSVRank(accId, cycleId)



        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
            chooseEmployee?.setCanceledOnTouchOutside(true)
            val window = chooseEmployee?.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee?.show()

        }
        binding.cycles.setOnClickListener {

            changeCycle = ChangeCycleAll(baseActivity, this, viewModel.dataManager, viewModel.dataManager.user.accId!!)
            changeCycle?.setCanceledOnTouchOutside(true)
            val window = changeCycle?.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            changeCycle?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            changeCycle?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            changeCycle?.show()

        }




        setEmpData()
        setObservers()

    }

    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, Observer {


            adapter.setMyData(it.data.svRanks)

        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {


            when (it) {
                1 -> {
                    context?.let { it1 -> ProgressLoading.show(it1) }
                }
                0 -> {
                    ProgressLoading.dismiss()
                }
            }

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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Supervision Report"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }


    override fun setOnItemSelect(model: SVRank) {
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee?.dismiss()
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

        accId = model?.empAccountId.toString()

        viewModel.getSVRank(accId, cycleId)

    }

    override fun changeCycle(cycle: Cycle?) {
        binding.cycles.setText(cycle?.cycleArName)

        fromDate = cycle?.fromDate?.take(10)!!
        toDate = cycle?.toDate?.take(10)!!
        cycleId = cycle?.cycleId


        viewModel.getSVRank(accId, cycleId)
    }


}
