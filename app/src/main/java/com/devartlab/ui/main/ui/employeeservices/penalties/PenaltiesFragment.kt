package com.devartlab.ui.main.ui.employeeservices.penalties

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentPenaltiesBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.Penalties
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.employeeservices.expenses.add.AddPenaltiesDialog
import com.devartlab.utils.ChangeDoctorData
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.RecyclerTouchListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class PenaltiesFragment : BaseFragment<FragmentPenaltiesBinding>(), ChooseEmployeeInterFace, ChangeDoctorData {

    private lateinit var viewModel: PenaltiesViewModel
    private lateinit var binding: FragmentPenaltiesBinding
    private lateinit var adapter: PenaltiesAdapter
    private var BUTTON_WIDTH: Int = 100
    lateinit var dialog: AddPenaltiesDialog
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var fullList: ArrayList<Penalties>
    lateinit var currentMonth: String
    lateinit var currentyear: String
    lateinit var empModel: FilterDataEntity
    lateinit var dateMilliS: String
    lateinit var chooseEmployeeInterFace: ChooseEmployeeInterFace
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            chooseEmployeeInterFace = context as ChooseEmployeeInterFace
        } catch (castException: ClassCastException) {
            /** The activity does not implement the listener.  */
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_penalties
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateMilliS = CommonUtilities.currentToMillis.toString()
        fullList = ArrayList()
        viewModel = ViewModelProviders.of(this).get(PenaltiesViewModel::class.java)
        empModel = requireArguments().getParcelable<FilterDataEntity>("EMP_MODEL") !!

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        binding = viewDataBinding!!


        setupRecyclerView()
    //    setObservers()


        val dateFormatYear: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        val dateFormatMonth: DateFormat = SimpleDateFormat("MM", Locale.US)
        val date = Date()
        currentMonth = dateFormatMonth.format(date).toString()
        currentyear = dateFormatYear.format(date).toString()

        viewModel.getPenalties(empModel.empId.toString(), currentMonth, currentyear)

        binding.addNew.hide()
        binding.addNew.setOnClickListener(View.OnClickListener {
            dialog = AddPenaltiesDialog(baseActivity, viewModel.dataManager!!, dateMilliS, empModel.empId.toString(), Penalties(), false)
            dialog.setCanceledOnTouchOutside(true)
            val window = dialog.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            dialog.show()

            dialog.setOnDismissListener {

                viewModel.getPenalties(empModel.empId.toString(), currentMonth, currentyear)

            }
        })


    }

    private fun setupRecyclerView() {
       // adapter = PenaltiesAdapter(baseActivity, viewModel.dataManager!!, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseActivity)
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = adapter
      //  adapter.setdrData(empModel)
        fullList.clear()
        fullList.add(0, Penalties())
      //  adapter.setMyData(fullList)
        val touchListener = RecyclerTouchListener(baseActivity, binding.recyclerView)
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

                                if (fullList[position].allowToUpdate) {
                                    dialog = AddPenaltiesDialog(baseActivity, viewModel.dataManager!!, dateMilliS, empModel.empId.toString(), fullList[position], true)
                                    dialog.setCanceledOnTouchOutside(true)
                                    val window = dialog.window
                                    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                                    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                                    dialog.show()

                                    dialog.setOnDismissListener {

                                        viewModel.getPenalties(empModel.empId.toString(), currentMonth, currentyear)

                                    }
                                } else {
                                    Toast.makeText(baseActivity, "you cant update this row", Toast.LENGTH_SHORT).show()
                                }


                            }

                            R.id.delete_task -> {

                                if (fullList[position].allowToUpdate) {
                                    val builder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
                                    builder.setTitle("Delete Expense?")
                                    builder.setMessage("Are you sure?")
                                    builder.setPositiveButton("YES") { dialog, which ->
                                        viewModel.deletePenalty(fullList.get(position).penaltyId.toString())
                                        dialog.dismiss()
                                    }
                                    builder.setNegativeButton("NO") { dialog, which ->

                                        dialog.dismiss()
                                    }
                                    val alert = builder.create()
                                    alert.show()
                                } else {
                                    Toast.makeText(baseActivity, "you cant update this row", Toast.LENGTH_SHORT).show()
                                }


                            }
                        }
                    }
                })
        binding.recyclerView.addOnItemTouchListener(touchListener)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.addNew.hide()
                } else {
                    binding.addNew.show()
                }
            }

        })

    }

 /*   private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it.isSuccesed) {

                fullList = it.data.penalties
                fullList.add(0, Penalties())
                if (fullList.isNullOrEmpty()) {
                    adapter.setMyData(fullList)
                    binding.emptyList.visibility = View.VISIBLE
                } else {
                    adapter.setMyData(fullList)
                    binding.emptyList.visibility = View.GONE
                }
            } else {

                binding.emptyList.visibility = View.VISIBLE
                fullList.clear()
                fullList.add(0, Penalties())
                adapter.setMyData(fullList)
                Toast.makeText(context, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.responseLiveDelete.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            ProgressLoading.dismiss()
            if (it.isSuccesed) {

                viewModel.getPenalties(empModel.empId.toString(), currentMonth, currentyear)

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

    }*/


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.penalties_menu, menu)
        val item = menu.findItem(R.id.dateTextView)

        val textView = item?.actionView as TextView
        textView.textSize = 20F
        textView.setTextColor(Color.parseColor("#ffffff"))
        textView.setPadding(10, 5, 10, 5)
        val outValue = TypedValue()
        requireContext().theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
        textView.setBackgroundResource(outValue.resourceId)

        val dateFormatYear: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        val dateFormatMonth: DateFormat = SimpleDateFormat("MM", Locale.US)
        val date = Date()
        textView.text = dateFormatMonth.format(date).toString() + " - " + dateFormatYear.format(date).toString()
        textView.setOnClickListener(View.OnClickListener {

            // Get Current Date
            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->


                        val cal = Calendar.getInstance()
                        cal[Calendar.DAY_OF_MONTH] = dayOfMonth
                        cal[Calendar.MONTH] = monthOfYear
                        cal[Calendar.YEAR] = year
                        System.out.println(" timeInMillis : " + cal.timeInMillis.toString())
                        dateMilliS = cal.timeInMillis.toString()




                        currentMonth = (monthOfYear + 1).toString()
                        currentyear = year.toString()

                        viewModel.getPenalties(empModel.empId.toString(), currentMonth, currentyear)


                        textView.text = currentMonth + " - " + currentyear


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        })




        return
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        binding.addNew.show()
        chooseEmployee.dismiss()
        empModel = model!!
      //  adapter.setdrData(empModel)
        viewModel.getPenalties(empModel.empId.toString(), currentMonth, currentyear)
        chooseEmployeeInterFace.chooseEmployee(empModel)
    }

    override fun changeDrData() {

        chooseEmployee = ChooseEmployee(baseActivity, this@PenaltiesFragment, viewModel.dataManager!!)
        chooseEmployee.setCanceledOnTouchOutside(true)
        val window = chooseEmployee.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
        chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        chooseEmployee.show()
    }

    override fun showInfo() {
    }
}
