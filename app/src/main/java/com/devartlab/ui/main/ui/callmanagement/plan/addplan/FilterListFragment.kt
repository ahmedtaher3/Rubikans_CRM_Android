package com.devartlab.ui.main.ui.callmanagement.plan.addplan

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
import com.devartlab.databinding.FragmentFilterBinding
import com.devartlab.ui.main.ui.callmanagement.list.list.AddPlanViewModel


/**
 * A simple [Fragment] subclass.
 */
class FilterListFragment(private val filterInterface: FilterInterface) : DialogFragment() {


    var binding: FragmentFilterBinding? = null
    lateinit var viewModel: AddPlanViewModel
    private var mRootView: View? = null


    lateinit var line: ArrayList<String>
    lateinit var lineIds: ArrayList<Int>

    lateinit var terriotry: ArrayList<String>
    lateinit var terriotryIds: ArrayList<Int>

    lateinit var brick: ArrayList<String>
    lateinit var brickIds: ArrayList<Int>

    lateinit var speciality: ArrayList<String>
    lateinit var specialityIds: ArrayList<Int>

    lateinit var specialityChild: ArrayList<String>
    lateinit var specialityIdsChild: ArrayList<Int>

    lateinit var classs: ArrayList<String>
    lateinit var classIds: ArrayList<Int>


    var brikFilterId: Int = 0
    var lineFilterId: Int = 0
    var terriotryFilterId: Int = 0
    var specialityFilterId: Int = 0
    var classFilterId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        line = ArrayList()
        lineIds = ArrayList()
        terriotry = ArrayList()
        terriotryIds = ArrayList()
        brick = ArrayList()
        brickIds = ArrayList()
        speciality = ArrayList()
        specialityChild = ArrayList()
        specialityIds = ArrayList()
        specialityIdsChild = ArrayList()
        classs = ArrayList()
        classIds = ArrayList()


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false)
        mRootView = binding?.getRoot()
        return mRootView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.setLifecycleOwner(this)
        binding?.executePendingBindings()
        viewModel = ViewModelProviders.of(this).get(AddPlanViewModel::class.java)


        viewModel.getFilterLines(viewModel?.dataManager?.user?.accId.toString(), "TblDefSalesLines", "", "0")
        viewModel.getFilterTerriotry(viewModel?.dataManager?.user?.accId.toString(), "TblAnalyDefSalesTerriotry", "0", "0")
        viewModel.getFilterBrik(viewModel?.dataManager?.user?.accId.toString(), "TblDefBrick", "", "0")
        viewModel.getFilterSpeciality(viewModel?.dataManager?.user?.accId.toString(), "TblDefCustomerType", "and IsMain=1", "0")
        viewModel.getFilterClass(viewModel?.dataManager?.user?.accId.toString(), "TblDefCustomerClass", "", "0")

        val list = ArrayList<String>()

        binding?.lineSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.terriotrySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.brickSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.specialitySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.specialitySpinnerChild?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.classSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))



        binding?.lineSpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    binding?.lineColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    binding?.lineSpinner?.setShowClearButton(true)
                } else {
                    binding?.lineColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    binding?.lineSpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding?.lineSpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->

            lineFilterId = lineIds[i]
            viewModel.getFilterTerriotry(viewModel?.dataManager?.user?.accId.toString(), "TblAnalyDefSalesTerriotry", lineFilterId.toString(), "0")

        })

        binding?.terriotrySpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    binding?.terriotryColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    binding?.terriotrySpinner?.setShowClearButton(true)
                } else {
                    binding?.terriotryColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    binding?.terriotrySpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding?.terriotrySpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->


            terriotryFilterId = terriotryIds[i]
            viewModel.getFilterBrik(viewModel?.dataManager?.user?.accId.toString(), "TblDefBrick", terriotryFilterId.toString(), "0")


        })


        binding?.brickSpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    binding?.brickColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    binding?.brickSpinner?.setShowClearButton(true)
                } else {
                    binding?.brickColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    binding?.brickSpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding?.brickSpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            brikFilterId = brickIds[i]
        })


        binding?.specialitySpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    binding?.specialityColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    binding?.specialitySpinner?.setShowClearButton(true)
                } else {
                    binding?.specialityColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    binding?.specialitySpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding?.specialitySpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            specialityFilterId = specialityIds[i]
            viewModel.getFilterSpecialityChild(viewModel?.dataManager?.user?.accId.toString(), "TblDefCustomerType", "and CusTypeParentId="+specialityFilterId.toString(), "0")

        })



        binding?.specialitySpinnerChild?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    binding?.specialityColorChild?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    binding?.specialitySpinnerChild?.setShowClearButton(true)
                } else {
                    binding?.specialityColorChild?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    binding?.specialitySpinnerChild?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding?.specialitySpinnerChild?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            specialityFilterId = specialityIdsChild[i]
        })

        binding?.classSpinner?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.length > 0) {
                    binding?.classColor?.setColorFilter(ContextCompat.getColor(activity!!, android.R.color.holo_green_light))
                    binding?.classSpinner?.setShowClearButton(true)
                } else {
                    binding?.classColor?.setColorFilter(ContextCompat.getColor(activity!!, R.color.gray_active_icon))
                    binding?.classSpinner?.setShowClearButton(false)

                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding?.classSpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            classFilterId = classIds[i]
        })


        binding?.applyFilter?.setOnClickListener(View.OnClickListener {


            filterInterface.applyFilter(brikFilterId.toString(), lineFilterId.toString(), terriotryFilterId.toString(), specialityFilterId.toString(), classFilterId.toString())
            dismiss()


        })

        setObservers()

        dialog?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun setObservers() {

        viewModel?.filterLinesResponseLive?.observe(this, Observer {


            line.clear()
            lineIds.clear()
            for (model in it) {
                line.add(model.fieldName!!)
                lineIds.add(model.fieldId!!)
            }
            binding?.lineSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, line))

            print(line.toString())

        })

        viewModel?.filterTerriotryResponseLive?.observe(this, Observer {


            terriotry.clear()
            terriotryIds.clear()
            for (model in it) {

                terriotry.add(model.fieldName!!)
                terriotryIds.add(model.fieldId!!)
            }

            binding?.terriotrySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, terriotry))


        })

        viewModel?.filterBrikResponseLive?.observe(this, Observer {

            brick.clear()
            brickIds.clear()

            for (model in it) {

                brick.add(model.fieldName!!)
                brickIds.add(model.fieldId!!)
            }


            binding?.brickSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, brick))


        })

        viewModel?.filterSpecialityResponseLive?.observe(this, Observer {


            speciality.clear()
            specialityIds.clear()
            for (model in it) {

                speciality.add(model.fieldName!!)
                specialityIds.add(model.fieldId!!)
            }

            binding?.specialitySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, speciality))


        })

        viewModel?.filterSpecialityResponseLiveChild?.observe(this, Observer {


            specialityChild.clear()
            specialityIdsChild.clear()
            for (model in it) {

                specialityChild.add(model.fieldName!!)
                specialityIdsChild.add(model.fieldId!!)
            }

            binding?.specialitySpinnerChild?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, specialityChild))


        })


        viewModel?.filterClassResponseLive?.observe(this, Observer {


            classs.clear()
            classIds.clear()
            for (model in it) {

                classs.add(model.fieldName!!)
                classIds.add(model.fieldId!!)
            }

            binding?.classSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, classs))


        })

    }


}
