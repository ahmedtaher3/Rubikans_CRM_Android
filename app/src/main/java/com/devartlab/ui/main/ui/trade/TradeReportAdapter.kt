package com.devartlab.ui.main.ui.trade

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.Bill


class TradeReportAdapter(private val context: Context, private var myData: ArrayList<Bill>, private val onBillSelect: OnBillSelect) : RecyclerView.Adapter<TradeReportAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.trade_report_item, parent, false)
        return ViewHolder(view)
    }

    interface OnBillSelect{
        fun setOnBillSelect(model: Bill)
    }

    fun setMyData(myData: ArrayList<Bill>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<Bill> {
        return this.myData
    }

    fun addItem(model: Bill) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.name?.text = model.customerName
        holder.date?.text = model.date.toString()
        holder.total?.text = model.lastPrice.toString()

        holder.itemView.setOnClickListener {
            onBillSelect.setOnBillSelect(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var name: TextView? = null
        var date: TextView? = null
        var total: TextView? = null


        init {
            date = view.findViewById(R.id.date)
            name = view.findViewById(R.id.name)
            total = view.findViewById(R.id.total)


        }


    }


}