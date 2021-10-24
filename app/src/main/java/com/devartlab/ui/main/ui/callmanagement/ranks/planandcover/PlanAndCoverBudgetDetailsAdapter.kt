package com.devartlab.ui.main.ui.callmanagement.ranks.planandcover


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.databinding.PlanAndCoverDetailsBudgetItemBinding
import com.devartlab.databinding.PlanAndCoverDetailsItemBinding
import com.devartlab.model.CustomeBudgetDetilas


class PlanAndCoverBudgetDetailsAdapter(private val context: Context, private var myData: ArrayList<CustomeBudgetDetilas>) : RecyclerView.Adapter<PlanAndCoverBudgetDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                 PlanAndCoverDetailsBudgetItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }


    fun setMyData(myData: ArrayList<CustomeBudgetDetilas>) {
        this.myData = myData
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        holder.binding?.requestType?.text = model.reqTypeDescription
        holder.binding?.reqDescription?.text = model.reqDescription
        holder.binding?.requestDate?.text = model.markReqExecutDate?.take(10)
        holder.binding?.requestId?.text = model.markReqCode
        holder.binding?.totalCost?.text = model.totalCost.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }


    inner class ViewHolder(var _binding: PlanAndCoverDetailsBudgetItemBinding) :
            RecyclerView.ViewHolder(_binding.root) {

        var binding: PlanAndCoverDetailsBudgetItemBinding? = null

        init {
            this.binding = _binding;


        }

    }


}

