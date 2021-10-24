package com.devartlab.ui.main.ui.callmanagement.ranks.planandcover


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.databinding.PlanAndCoverDetailsItemBinding
import com.devartlab.model.CustomerReportDetilas
import com.devartlab.model.StartPointReportTitle


class PlanAndCoverReportDetailsAdapter(private val context: Context, private var myData: ArrayList<CustomerReportDetilas>) : RecyclerView.Adapter<PlanAndCoverReportDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PlanAndCoverDetailsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }


    fun setMyData(myData: ArrayList<CustomerReportDetilas>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<CustomerReportDetilas> {
        return this.myData
    }

    fun addItem(model: CustomerReportDetilas) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        holder.binding?.day?.text = model.day
        holder.binding?.date?.text = model.date?.take(10)
        holder.binding?.shift?.text = model.shift

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var _binding: PlanAndCoverDetailsItemBinding) :
            RecyclerView.ViewHolder(_binding.root) {

        var binding: PlanAndCoverDetailsItemBinding? = null

        init {
            this.binding = _binding;


        }

    }


}

