package com.devartlab.ui.main.ui.callmanagement.planListpermission

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.databinding.PlanPermissionItemBinding
import com.devartlab.model.PlanDayPermissionData
import com.devartlab.utils.CommonUtilities
import java.util.*


class PlanPermissionAdapter(private val context: Context, private var myData: ArrayList<PlanDayPermissionData>, private var onShowPlanSelect: OnShowPlanSelect) : RecyclerView.Adapter<PlanPermissionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PlanPermissionItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnShowPlanSelect
    {
        fun setOnShowPlanSelect(model : PlanDayPermissionData)
    }


    fun setMyData(myData: ArrayList<PlanDayPermissionData>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<PlanDayPermissionData> {
        return this.myData
    }

    fun addItem(model: PlanDayPermissionData) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.name?.text = model.day
        holder._binding?.desc?.text = model.date.take(10)

        holder._binding?.openDay?.isChecked = model.oPenDay

        holder._binding?.openDay?.setOnClickListener {
            model.oPenDay = holder._binding?.openDay?.isChecked
        }


        holder._binding?.expiryDate?.setOnClickListener {

            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(context, R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val DATE = year.toString() + "-" + CommonUtilities.checkTwoDigits((monthOfYear + 1).toString()) + "-" + CommonUtilities.checkTwoDigits(dayOfMonth.toString())
                        holder._binding?.expiryDate?.text = DATE
                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.datePicker.maxDate = CommonUtilities.convertDateToMillis(model.date.take(10))
            datePickerDialog.datePicker.minDate = Date().time - 100000
            datePickerDialog.show()


        }


        holder._binding?.showPlan?.setOnClickListener {

            onShowPlanSelect.setOnShowPlanSelect(model)

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: PlanPermissionItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        private var mDrawableBuilder: TextDrawable? = null

        var _binding: PlanPermissionItemBinding? = null

        init {
            this._binding = binding;


        }


    }


}