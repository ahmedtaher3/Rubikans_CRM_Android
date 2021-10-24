package com.devartlab.ui.main.ui.employeeservices.penalties.google

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.PenaltiesGoogle
import java.util.*


class AllPenaltiesAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var myData: ArrayList<PenaltiesGoogle>
    private val context: Context


    init {
        myData = ArrayList()
        this.context = context

    }

    fun setMyData(myData: ArrayList<PenaltiesGoogle>) {
        this.myData = myData
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.penalties_google_item, parent, false)
        return MyViewHolder(itemView)


    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val model = myData[position]
        if (holder is MyViewHolder) {

            holder.empName.text = model.empName
            holder.penaltyType.text = model.type
            holder.penaltyReason.text = model.reason
            holder.notes.text = model.notes
            holder.date.text = model.date?.take(10)


            when (model.approve) {
                "PENDING" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))

                "APPROVED" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.green))

                "REFUSED" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.red))

            }

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }


    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var empName: TextView
        var empImage: ImageView
        var date: TextView
        var notes: TextView
        var penaltyReason: TextView
        var itemStatus: LinearLayout
        var penaltyType: TextView


        init {
            empName = v.findViewById(R.id.empName)
            empImage = v.findViewById(R.id.empImage)
            date = v.findViewById(R.id.date)
            notes = v.findViewById(R.id.notes)
            penaltyReason = v.findViewById(R.id.penaltyReason)
            penaltyType = v.findViewById(R.id.penaltyType)
            itemStatus = v.findViewById(R.id.itemStatus)


        }
    }

}