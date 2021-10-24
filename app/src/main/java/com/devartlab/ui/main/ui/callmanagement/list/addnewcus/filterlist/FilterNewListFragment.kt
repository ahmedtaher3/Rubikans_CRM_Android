package com.devartlab.ui.main.ui.callmanagement.list.addnewcus.filterlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.databinding.FragmentFilterListBinding
import com.devartlab.ui.main.ui.callmanagement.list.list.AddPlanViewModel
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.FilterInterface


/**
 * A simple [Fragment] subclass.
 */
class FilterNewListFragment(private val filterInterface: FilterInterface  , private val assID :String) : DialogFragment() {


    var fragmentFilterListBinding: FragmentFilterListBinding? = null
    lateinit var viewModel: AddPlanViewModel
    private var mRootView: View? = null


    lateinit var brick: ArrayList<String>
    lateinit var brickIds: ArrayList<Int>

    lateinit var speciality: ArrayList<String>
    lateinit var specialityIds: ArrayList<Int>

    lateinit var classs: ArrayList<String>
    lateinit var classIds: ArrayList<Int>


    var brikFilterId: Int = 0
    var specialityFilterId: Int = 0
    var classFilterId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        brick = ArrayList()
        brickIds = ArrayList()
        speciality = ArrayList()
        specialityIds = ArrayList()
        classs = ArrayList()
        classIds = ArrayList()


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentFilterListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_list, container, false)
        mRootView = fragmentFilterListBinding?.getRoot()
        return mRootView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentFilterListBinding?.setLifecycleOwner(this)
        fragmentFilterListBinding?.executePendingBindings()
        viewModel = ViewModelProviders.of(this).get(AddPlanViewModel::class.java)

        viewModel.getFilterBrik(viewModel?.dataManager?.user?.accId.toString(),"TblDefBrick", assID, "0")
        viewModel.getFilterSpeciality(viewModel?.dataManager?.user?.accId.toString(),"TblDefCustomerType", "0", "0")
        viewModel.getFilterClass(viewModel?.dataManager?.user?.accId.toString(),"TblDefCustomerClass", "0", "0")

        val list = ArrayList<String>()

        fragmentFilterListBinding?.brickSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        fragmentFilterListBinding?.specialitySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        fragmentFilterListBinding?.classSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))



        fragmentFilterListBinding?.brickSpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    fragmentFilterListBinding?.brickColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    fragmentFilterListBinding?.brickSpinner?.setShowClearButton(true)
                } else {
                    fragmentFilterListBinding?.brickColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    fragmentFilterListBinding?.brickSpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        fragmentFilterListBinding?.brickSpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            brikFilterId = brickIds[i]
        })


        fragmentFilterListBinding?.specialitySpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    fragmentFilterListBinding?.specialityColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    fragmentFilterListBinding?.specialitySpinner?.setShowClearButton(true)
                } else {
                    fragmentFilterListBinding?.specialityColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    fragmentFilterListBinding?.specialitySpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        fragmentFilterListBinding?.specialitySpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            specialityFilterId = specialityIds[i]
        })


        fragmentFilterListBinding?.classSpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    fragmentFilterListBinding?.classColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    fragmentFilterListBinding?.classSpinner?.setShowClearButton(true)
                } else {
                    fragmentFilterListBinding?.classColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    fragmentFilterListBinding?.classSpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        fragmentFilterListBinding?.classSpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            classFilterId = classIds[i]
        })


        fragmentFilterListBinding?.applyFilter?.setOnClickListener(View.OnClickListener {


            filterInterface.applyFilter(brikFilterId.toString(), "0", "0", specialityFilterId.toString(), classFilterId.toString())
            dismiss()


        })

        setObservers()

        dialog?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun setObservers() {

        viewModel?.filterBrikResponseLive?.observe(this, Observer {

            brick.clear()
            brickIds.clear()

            for (model in it) {

                brick.add(model.empName!!)
                brickIds.add(model.empId!!)
            }


            fragmentFilterListBinding?.brickSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, brick))


        })

        viewModel?.filterSpecialityResponseLive?.observe(this, Observer {


            speciality.clear()
            specialityIds.clear()
            for (model in it) {

                speciality.add(model.empName!!)
                specialityIds.add(model.empId!!)
            }

            fragmentFilterListBinding?.specialitySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, speciality))


        })

        viewModel?.filterClassResponseLive?.observe(this, Observer {


            classs.clear()
            classIds.clear()
            for (model in it) {

                classs.add(model.empName!!)
                classIds.add(model.empId!!)
            }

            fragmentFilterListBinding?.classSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, classs))


        })

    }


}
