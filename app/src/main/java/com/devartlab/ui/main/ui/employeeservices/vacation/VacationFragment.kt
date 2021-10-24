package com.devartlab.ui.main.ui.employeeservices.vacation

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentVacationsBinding
import com.devartlab.model.EmployeeVacation
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.employeeservices.expenses.add.AddVacationDialog
import com.devartlab.utils.ChangeDoctorData
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.devartlab.utils.RecyclerTouchListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class VacationFragment : BaseFragment<FragmentVacationsBinding>(), ChooseEmployeeInterFace, ChangeDoctorData {

    private lateinit var viewModel: VacationViewModel
    private lateinit var binding: FragmentVacationsBinding
    private lateinit var adapter: VacationAdapter
    private var BUTTON_WIDTH: Int = 100
    lateinit var dialog: AddVacationDialog
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var fullList: ArrayList<EmployeeVacation>
    lateinit var fromText: String
    lateinit var fromTextMS: String
    lateinit var toText: String
    lateinit var toTextMS: String
    lateinit var empModel: FilterDataEntity
    var fmt: SimpleDateFormat? = null
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
        return R.layout.fragment_vacations
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VacationViewModel::class.java)
        fullList = ArrayList()
        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        toText = fmt?.format(CommonUtilities.currentToMillis).toString()
        fromText = fmt?.format(CommonUtilities.currentToMillis).toString()
        empModel = requireArguments().getParcelable<FilterDataEntity>("EMP_MODEL") !!

        toTextMS = CommonUtilities.currentToMillis.toString()
        fromTextMS = CommonUtilities.currentToMillis.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        binding = viewDataBinding



        binding.fromTextView.setText(fromText)
        binding.toTextView.setText(toText)

        binding.fromTextView.setOnClickListener(View.OnClickListener {


            // Get Current Date
            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        binding.fromTextView.setText(year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString())

                        val cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.YEAR, year);

                        fromTextMS = cal.timeInMillis.toString()


                        viewModel.getEmployeeVacation(empModel.empId.toString(), fromTextMS, toTextMS)

                    }, mYear, mMonth, mDay)

            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()

        })

        binding.toTextView.setOnClickListener(View.OnClickListener {


            // Get Current Date
            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(baseActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        binding.toTextView.setText(year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString())

                        val cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.YEAR, year);

                        toTextMS = cal.timeInMillis.toString()
                        viewModel.getEmployeeVacation(empModel.empId.toString(), fromTextMS, toTextMS)
                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()

        })






        viewModel.getEmployeeVacation(empModel.empId.toString(), fromTextMS, toTextMS)


        binding.addNew.setOnClickListener(View.OnClickListener {

            dialog = AddVacationDialog(baseActivity, viewModel.dataManager!!, EmployeeVacation(), false);
            dialog.setCanceledOnTouchOutside(true);
            val window = dialog.getWindow();
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.show();

            dialog.setOnDismissListener {

                viewModel.getEmployeeVacation(empModel.empId.toString(), fromTextMS, toTextMS)

            }
        })


        setupRecyclerView()
        setObservers()
    }

    private fun setupRecyclerView() {
        adapter = VacationAdapter(baseActivity, viewModel.dataManager!!, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseActivity)
        binding.recyclerView.adapter = adapter
        adapter.setdrData(empModel)
        fullList.clear()
        fullList.add(0, EmployeeVacation())
        adapter.setMyData(fullList)

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

                                    dialog = AddVacationDialog(baseActivity, viewModel.dataManager!!, fullList[position], true);
                                    dialog.setCanceledOnTouchOutside(true);
                                    val window = dialog.getWindow();
                                    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                    dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                                    dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                    dialog.show();

                                    dialog.setOnDismissListener {

                                        viewModel.getEmployeeVacation(empModel.empId.toString(), fromTextMS, toTextMS)

                                    }
                                } else {
                                    Toast.makeText(baseActivity, "you cant update this row", Toast.LENGTH_SHORT).show()
                                }


                            }

                            R.id.delete_task -> {

                                if (fullList[position].allowToUpdate) {
                                    val builder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
                                    builder.setTitle("Delete Vacation?")
                                    builder.setMessage("Are you sure?")
                                    builder.setPositiveButton("YES") { dialog, which ->
                                        viewModel.deleteVacation(fullList.get(position).vacId.toString())

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
                    binding?.addNew?.hide();
                } else {
                    binding?.addNew?.show();
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })


    }

    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it.isSuccesed) {

                fullList = it.data.employeeVacations
                fullList.add(0, EmployeeVacation())
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
                fullList.add(0, EmployeeVacation())
                adapter.setMyData(fullList)
                Toast.makeText(context, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })

        viewModel.responseLiveDelete.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            ProgressLoading.dismiss()
            if (it.isSuccesed) {

                viewModel.getEmployeeVacation(empModel.empId.toString(), fromTextMS, toTextMS)

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.vacation_menu, menu)
        val item = menu?.findItem(R.id.dateTextView)


        return
    }

    override fun chooseEmployee(model: FilterDataEntity?) {

        chooseEmployee.dismiss()
        empModel = model!!
        viewModel.getEmployeeVacation(empModel.empId.toString(), fromTextMS, toTextMS)
        chooseEmployeeInterFace.chooseEmployee(empModel)
        adapter.setdrData(empModel)
    }


    override fun changeDrData() {
        chooseEmployee = ChooseEmployee(baseActivity, this@VacationFragment, viewModel?.dataManager!!);
        chooseEmployee.setCanceledOnTouchOutside(true);
        val window = chooseEmployee.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        chooseEmployee.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseEmployee.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseEmployee.show();

    }

    override fun showInfo() {
        TODO("Not yet implemented")
    }
}
