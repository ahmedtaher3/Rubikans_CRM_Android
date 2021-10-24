package com.devartlab.ui.main.ui.callmanagement.plan.addplan.office

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.room.plan.PlanEntity
import java.util.*
import kotlin.collections.ArrayList

class ExtraPlanAdapter(context: Context) :
    RecyclerView.Adapter<ExtraPlanAdapter.MyViewHolder>() {
    var myData: ArrayList<PlanEntity>
    val context: Context
    fun getData(): ArrayList<PlanEntity> {
        return myData
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.extra_item, parent, false)
        return MyViewHolder(
            itemView
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        holder.taskText.text = myData[position].taskText + myData[position].officeDescription
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setData(myData: ArrayList<PlanEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun addItem(model: PlanEntity) {
        myData.add(model)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var taskName: TextView
        var taskText: TextView

        init {
            taskName = view.findViewById(R.id.taskName)
            taskText = view.findViewById(R.id.taskText)
        }
    }

    init {
        myData = ArrayList()
        this.context = context
    }
}