package com.devartlab.ui.main.ui.callmanagement.ranks.doublevisit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.DoubleVisitReportItemBinding
import com.devartlab.model.DoubleVisitReport


class DVReportAdapter(private val context: Context, private var myData: ArrayList<DoubleVisitReport>, private val onItemSelect: OnItemSelect) : RecyclerView.Adapter<DVReportAdapter.ViewHolder>() {

    private var itemsCopy = java.util.ArrayList<DoubleVisitReport>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DoubleVisitReportItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnItemSelect {
        fun setOnItemSelect(model: DoubleVisitReport)
    }

    fun setMyData(myData: ArrayList<DoubleVisitReport>) {
        this.myData = myData
        this.itemsCopy.addAll(myData)
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<DoubleVisitReport> {
        return this.myData
    }

    fun addItem(model: DoubleVisitReport) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.empName?.text = model.employeeEMpName
        holder._binding?.teritory?.text = model.manger


        holder._binding?.amShiftPlan?.text = model.planAmShift.toString() + "/" + model.totalPlanAmShift.toString()
        holder._binding?.amShiftCover?.text = model.coverAmShift.toString() + "/" + model.totalCoverAmShift.toString()
        holder._binding?.amShiftPlanVisit?.text = model.planVisitAmShift.toString() + "/" + model.totalPlanVisitAmShift.toString()
        holder._binding?.amShiftVisitNum?.text = model.visitNumAmShift.toString() + "/" + model.totalVisitNumAmShift.toString()


        holder._binding?.amShiftPlanPercentage?.text = model.prcentagePlanAmShift.toString() + "%"
        holder._binding?.amShiftCoverPercentage?.text = model.prcentageCoverAmShift.toString() + "%"
        holder._binding?.amShiftPlanVisitPercentage?.text = model.prcentagePlanVisitAmShift.toString() + "%"
        holder._binding?.amShiftVisitNumPercentage?.text = model.prcentageVisitNumAmShift.toString() + "%"


        holder._binding?.pmShiftPlan?.text = model.planPmShift.toString() + "/" + model.totalPlanPmShift.toString()
        holder._binding?.pmShiftCover?.text = model.coverPmShift.toString() + "/" + model.totalCoverPmShift.toString()
        holder._binding?.pmShiftPlanVisit?.text = model.planVisitPmShift.toString() + "/" + model.totalPlanVisitPmShift.toString()
        holder._binding?.pmShiftVisitNum?.text = model.visitNumPmShift.toString() + "/" + model.totalVisitNumPmShift.toString()

        holder._binding?.pmShiftPlanPercentage?.text = model.prcentagePlanPmShift.toString() + "%"
        holder._binding?.pmShiftCoverPercentage?.text = model.prcentageCoverPmShift.toString() + "%"
        holder._binding?.pmShiftPlanVisitPercentage?.text = model.prcentagePlanVisitPmShift.toString() + "%"
        holder._binding?.pmShiftVisitNumPercentage?.text = model.prcentageVisitNumPmShift.toString() + "%"



        if (model.employeeImage != null)
        {
            Glide.with(context)
                    .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/" + model.employeeImage)
                    .placeholder(holder._binding?.empImage?.drawable)
                    .into(holder._binding?.empImage!!)
        }
        else

        {
            Glide.with(context)
                    .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(holder._binding?.empImage?.drawable)
                    .into(holder._binding?.empImage!!)
        }




        holder.itemView.setOnClickListener {
            onItemSelect.setOnItemSelect(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: DoubleVisitReportItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: DoubleVisitReportItemBinding? = null

        init {
            this._binding = binding;


        }

    }
    fun filter(text: String) {
        this.myData.clear()



        if (text.isEmpty()) {
            this.myData.addAll(itemsCopy)
        } else {


            for (model in itemsCopy) {
                //  || model.empArName!!.contains(text, ignoreCase = true) || model.empEnName!!.contains(text, ignoreCase = true)
                if (model.employeeEMpName!!.contains(text, ignoreCase = true)) {
                    myData.add(model)
                }

            }

        }
        notifyDataSetChanged()
    }

}