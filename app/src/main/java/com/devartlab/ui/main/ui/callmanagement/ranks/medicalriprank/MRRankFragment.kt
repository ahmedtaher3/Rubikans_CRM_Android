package com.devartlab.ui.main.ui.callmanagement.ranks.medicalriprank

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.MrRankFragmentBinding
import com.devartlab.model.Cycle
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.MRRank
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.cycles.ChangeCycleAll
import com.devartlab.ui.main.ui.cycles.ChangeCycleInterface
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.devartlab.utils.RankBottomSheet
import kotlin.collections.ArrayList


class MRRankFragment : BaseFragment<MrRankFragmentBinding>(), MRRankAdapter.OnItemSelect, ChooseEmployeeInterFace, ChangeCycleInterface {
    private lateinit var viewModel: RanksViewModel
    private lateinit var binding: MrRankFragmentBinding
    private lateinit var adapter: MRRankAdapter
    var chooseEmployee: ChooseEmployee? = null
    var changeCycle: ChangeCycleAll? = null
    var fromDate = ""
    var toDate = ""
    var cycleId = 0
    var accId = "0"
    var fullList: java.util.ArrayList<MRRank>? = null
    var SpecialityList: ArrayList<FilterDataEntity>? = null
    var pParentSpecialityIdStr = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        adapter = MRRankAdapter(baseActivity, ArrayList(), this)
    }

    override fun getLayoutId(): Int {
        return R.layout.mr_rank_fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
        binding.recyclerView.adapter = adapter

        fromDate = viewModel.dataManager.newOldCycle.currentCycleFromDate?.take(10)!!
        toDate = viewModel.dataManager.newOldCycle.currentCycleToDate?.take(10)!!
        cycleId = viewModel.dataManager.newOldCycle.currentCycleId
        accId = viewModel.dataManager.user.accId.toString()

        viewModel.getMRRank(accId, fromDate, toDate, pParentSpecialityIdStr , 2, cycleId, "1,11", "")
        viewModel.getFilterSpeciality(viewModel?.dataManager?.user?.accId.toString(), "TblDefCustomerType", "and IsMain=1", "0")



        binding.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                adapter.filter(charSequence.toString())

            }

            override fun afterTextChanged(editable: Editable) {}
        })

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

        binding.filter.setOnClickListener {

            if (!SpecialityList.isNullOrEmpty()) {

                val bottomDialogFragment =
                        RankBottomSheet(SpecialityList , object : RankBottomSheet.DialogListener {
                            override fun visits(isDescending: Boolean) {


                                if (isDescending) {
                                    val sortedList: List<MRRank> = fullList?.sortedByDescending { model: MRRank -> model.visitPercentage }!!
                                    adapter.setMyData(ArrayList(sortedList))
                                } else {
                                    val sortedList: List<MRRank> = fullList?.sortedBy { model: MRRank -> model.visitPercentage }!!
                                    adapter.setMyData(ArrayList(sortedList))
                                }

                            }

                            override fun list(isDescending: Boolean) {

                                if (isDescending) {
                                    val sortedList: List<MRRank> = fullList?.sortedByDescending { model: MRRank -> model.listPercentage }!!
                                    adapter.setMyData(ArrayList(sortedList))
                                } else {
                                    val sortedList: List<MRRank> = fullList?.sortedBy { model: MRRank -> model.listPercentage }!!
                                    adapter.setMyData(ArrayList(sortedList))
                                }
                            }

                            override fun unCover(isDescending: Boolean) {

                                if (isDescending) {
                                    val sortedList: List<MRRank> = fullList?.sortedByDescending { model: MRRank -> model.unCoverPercentage }!!
                                    adapter.setMyData(ArrayList(sortedList))
                                } else {
                                    val sortedList: List<MRRank> = fullList?.sortedBy { model: MRRank -> model.unCoverPercentage }!!
                                    adapter.setMyData(ArrayList(sortedList))
                                }
                            }

                            override fun specialist(id: String) {

                                pParentSpecialityIdStr = id
                                viewModel.getMRRank(accId, fromDate, toDate,pParentSpecialityIdStr , 2, cycleId, "1,11", "")

                            }


                        })




                bottomDialogFragment.show(
                        baseActivity.supportFragmentManager,
                        "bottomDialogFragment"
                )
            }


        }


        setEmpData()
        setObservers()
    }

    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, Observer {


            fullList = java.util.ArrayList<MRRank>()
            for (m in it.data.mrRanks) {
                if (!m.geteMpName().equals("Vacant"))
                    fullList?.add(m)
            }
            adapter.setMyData(fullList!!)

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




        viewModel?.filterSpecialityResponseLive?.observe(viewLifecycleOwner, Observer {

            SpecialityList = it
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
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Report"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }

    override fun setOnItemSelect(model: MRRank) {

        val intent = Intent(baseActivity, MedicalRepReportActivity::class.java)
        intent.putExtra("NAME", model.geteMpName())
        intent.putExtra("IMAGE", model.imagePath)
        intent.putExtra("ID", model.terrAccountId)
        intent.putExtra("FROM_DATE", fromDate)
        intent.putExtra("TO_DATE", toDate)
        intent.putExtra("CYCLE_ID", cycleId)
        intent.putExtra("MEDICAL_REP_ID", model.terrEmpId)
        startActivity(intent)
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee?.dismiss()
        binding.empImage.setImageResource(R.drawable.user_logo);
        if (model?.fileImage != null) {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }
        binding.empName.text = model?.empName
        binding.empId.text = model?.empId.toString()
        binding.empTitle.text = model?.empTitle

        accId = model?.empAccountId.toString()

        viewModel.getMRRank(accId, fromDate, toDate,pParentSpecialityIdStr , 2, cycleId, "1,11", "")


    }

    override fun changeCycle(cycle: Cycle?) {
        binding.cycles.setText(cycle?.cycleArName)

        fromDate = cycle?.fromDate?.take(10)!!
        toDate = cycle?.toDate?.take(10)!!
        cycleId = cycle?.cycleId


        viewModel.getMRRank(accId, fromDate, toDate,pParentSpecialityIdStr , 2, cycleId, "1,11", "")
    }


}
