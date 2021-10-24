package com.devartlab.ui.main.ui.callmanagement.plan.addplan.single

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.room.plan.PlanEntity
import java.util.*


private const val TAG = "SelectedPlanAdapter"

class SelectedPlanAdapter(context: Context) :
    RecyclerView.Adapter<SelectedPlanAdapter.MyViewHolder>() {
    var myData: ArrayList<PlanEntity>
    val context: Context


    init {
        myData = ArrayList()
        this.context = context
    }


    fun getMyData(): List<PlanEntity> {
        return myData
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.selected_items, parent, false)
        return MyViewHolder(
            itemView
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = myData[position]
        holder.selectedName.text = model.customerName
        holder.selectedDelete.setOnClickListener {
            myData.removeAt(position)
            notifyDataSetChanged()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setData(data: ArrayList<PlanEntity>) {
        this.myData = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var selectedName: TextView
        var selectedDelete: ImageView

        init {
            selectedName = v.findViewById(R.id.selectedName)
            selectedDelete = v.findViewById(R.id.selectedDelete)
        }
    }

    fun addItem(planEntity: PlanEntity) {
        Log.d(TAG, "addItem: " + planEntity)
        myData.add(planEntity)
        notifyDataSetChanged()

    }




}