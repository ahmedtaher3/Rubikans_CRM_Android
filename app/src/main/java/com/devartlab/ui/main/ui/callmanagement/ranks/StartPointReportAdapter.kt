package com.devartlab.ui.main.ui.callmanagement.ranks

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.StartPointReportItemBinding
import com.devartlab.model.StartPointReport
import com.devartlab.utils.CommonUtilities


class StartPointReportAdapter(private val context: Context, private var myData: ArrayList<StartPointReport>, private val onItemSelect: OnItemSelect , private val dataManager: DataManager) : RecyclerView.Adapter<StartPointReportAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                StartPointReportItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnItemSelect {
        fun setOnItemSelect(model: StartPointReport)
    }

    fun setMyData(myData: ArrayList<StartPointReport>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<StartPointReport> {
        return this.myData
    }

    fun addItem(model: StartPointReport) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.empName?.text = model.empArName

        try {
            holder._binding?.managerName?.text = model.managerArName

        } catch (e: Exception) {
        }
        try {
            holder._binding?.actualStart?.text = model.planStartTime

        } catch (e: Exception) {
        }
        try {
            holder._binding?.startAt?.text = model.reportStartTime

        } catch (e: Exception) {
        }
        try {
            holder._binding?.endedAt?.text = model.reportEndTime

        } catch (e: Exception) {
        }
        try {
            holder._binding?.address?.text = model.address

        } catch (e: Exception) {
        }
        try {

        } catch (e: Exception) {
        }




        if (!model.planStartTime.isNullOrEmpty() && !model.reportStartTime.isNullOrEmpty()) {

            if (CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt() > 20)
            {
                holder._binding?.late?.text = CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt().toString() + " min"
                holder._binding?.late?.setTextColor(Color.parseColor("#ff4000"))
            }
            else if (CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt() >= 0)
            {
                holder._binding?.late?.text = CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt().toString()+ " min"
                holder._binding?.late?.setTextColor(Color.parseColor("#4CAF50"))
            }
            else if (CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt() < 0)
            {
                holder._binding?.late?.text = CommonUtilities.getDifferenceTime(model.planStartTime!!, model.reportStartTime!!).toInt().toString()+ " min"
                holder._binding?.late?.setTextColor(Color.parseColor("#4CAF50"))
            }

        }



        if (model.imagePath != null) {
            Glide.with(context)
                    .load(dataManager.url + "ImageUpload/Employee/" + model.imagePath)
                    .placeholder(holder._binding?.empImage?.drawable)
                    .into(holder._binding?.empImage!!)
        } else {
            Glide.with(context)
                    .load(dataManager.url + "ImageUpload/Employee/DefaultEmpImage.jpg")
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

    inner class ViewHolder(var binding: StartPointReportItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: StartPointReportItemBinding? = null

        init {
            this._binding = binding;


        }

    }


}