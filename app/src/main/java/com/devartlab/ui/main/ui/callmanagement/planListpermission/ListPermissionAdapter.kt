package com.devartlab.ui.main.ui.callmanagement.planListpermission

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.databinding.DoubleVisitDetailsItemBinding
import com.devartlab.databinding.ListPermissionItemBinding
import com.devartlab.model.DoubleVisitReportDetails
import com.devartlab.model.ListPermissionData
import com.devartlab.utils.CommonUtilities


class ListPermissionAdapter(private val context: Context, private var myData: ArrayList<ListPermissionData>) : RecyclerView.Adapter<ListPermissionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ListPermissionItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }


    fun setMyData(myData: ArrayList<ListPermissionData>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<ListPermissionData> {
        return this.myData
    }

    fun addItem(model: ListPermissionData) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.name?.text = model.listType
        holder._binding?.desc?.text = model.listTerriotry

        holder._binding?.edit?.isChecked = model.allowToEdit
        holder._binding?.add?.isChecked = model.allowToAddCustomer
        holder._binding?.delete?.isChecked = model.allowToDeleteCustomer

        holder._binding?.edit?.setOnClickListener {
            model.allowToEdit = holder._binding?.edit?.isChecked
        }

        holder._binding?.add?.setOnClickListener {
            model.allowToAddCustomer = holder._binding?.add?.isChecked
        }
        holder._binding?.delete?.setOnClickListener {
            model.allowToDeleteCustomer = holder._binding?.delete?.isChecked
        }



    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: ListPermissionItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        private var mDrawableBuilder: TextDrawable? = null

        var _binding: ListPermissionItemBinding? = null

        init {
            this._binding = binding;


        }


    }


}