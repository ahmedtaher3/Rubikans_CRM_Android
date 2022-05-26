package com.devartlab.ui.main.ui.callmanagement.ranks

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.SvRankReportBinding
import com.devartlab.model.SVRank


class SVRankAdapter(private val context: Context, private var myData: ArrayList<SVRank>, private val onItemSelect: OnItemSelect , private val dataManager: DataManager) : RecyclerView.Adapter<SVRankAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                SvRankReportBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnItemSelect {
        fun setOnItemSelect(model: SVRank)
    }

    fun setMyData(myData: ArrayList<SVRank>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<SVRank> {
        return this.myData
    }

    fun addItem(model: SVRank) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.empName?.text = model.empNAme.toString()
        holder._binding?.teritory?.text = model.accDescription.toString()

        holder._binding?.doubleVisitShift?.text = model.doubleVActualshift.toString() + "/" + model.doubleVPlanshift.toString()
        holder._binding?.doubleVisitVisits?.text = model.doubleVActualVisits.toString() + "/" + model.doubleVPlanCustomer.toString()

        holder._binding?.singleVisitShift?.text = model.singleVActualshift.toString() + "/" + model.singleVPlanshift.toString()
        holder._binding?.singleVisitVisits?.text = model.singleVActualVisits.toString() + "/" + model.singleVPlanCustomer.toString()

        holder._binding?.otherVisitShift?.text = model.actualOtherActivitesSHift.toString() + "/" + model.otherActivitesSHift.toString()

        if (model.imagePath != null)
        {
            Glide.with(context)
                    .load(dataManager.url + "ImageUpload/Employee/" + model.imagePath)
                    .placeholder(holder._binding?.empImage?.drawable)
                    .into(holder._binding?.empImage!!)
        }
        else
        {
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

    inner class ViewHolder(var binding: SvRankReportBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: SvRankReportBinding? = null

        init {
            this._binding = binding;


        }

    }


}