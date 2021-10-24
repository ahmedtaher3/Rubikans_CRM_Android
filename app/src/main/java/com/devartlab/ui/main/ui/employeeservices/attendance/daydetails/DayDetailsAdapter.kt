package com.devartlab.ui.main.ui.employeeservices.attendance.daydetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.EMployeeDayDetails_class
import java.util.*

class DayDetailsAdapter(context: Context) : RecyclerView.Adapter<DayDetailsAdapter.MyViewHolder>() {
    private var myData: ArrayList<EMployeeDayDetails_class>
    private val context: Context
    fun getMyData(): List<EMployeeDayDetails_class> {
        return myData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.day_details_item, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder.day_deduction_quantity.text = "Penalty Quantity : " + model.penaltyQty.toString() + " day"
        holder.day_deduction_reason.text = model.description
        holder.day_name.text = model.day.take(3)
        holder.day_number.text =  Character.toString(model.dayDate[8]) + Character.toString(model.dayDate[9])
        holder.day_date.text = model.dayDate.take(7)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun clearData() {
        myData.clear()
        notifyDataSetChanged()
    }

    fun setMyData(myData: ArrayList<EMployeeDayDetails_class>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var day_deduction_quantity: TextView
        var day_deduction_reason: TextView
        var day_number: TextView
        var day_name: TextView
        var day_date: TextView

        init {
            day_deduction_quantity = view.findViewById(R.id.day_deduction_quantity)
            day_deduction_reason = view.findViewById(R.id.day_deduction_reason)
            day_number = view.findViewById(R.id.day_number)
            day_name = view.findViewById(R.id.day_name)
            day_date = view.findViewById(R.id.day_date)
        }
    }

    init {
        myData = ArrayList()
        this.context = context
    }
}