package com.devartlab.ui.main.ui.market.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.RequestCostItem


class RequestCostAdapter(
    private val context: Context,
    private var myData: ArrayList<RequestCostItem>,
    private var onCostItemClick: OnCostItemClick

) : RecyclerView.Adapter<RequestCostAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.request_cost_item, parent, false)
        return ViewHolder(view)
    }

    interface OnCostItemClick {

        fun setOnCostItemClick(model: RequestCostItem)
    }

    fun setMyData(myData: ArrayList<RequestCostItem>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder.cost?.text = model.value.toString()
        holder.date?.text = model.exchangDate.toString().take(10)
        holder.requestDescription?.text = model.coastItemArName
        holder.requestQuantity?.text = model.qty.toString()
        holder.totalCost?.text = model.totalValue.toString()
        holder.recipient?.text = model.column1.toString()

        holder.itemView.setOnClickListener {

            onCostItemClick.setOnCostItemClick(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var cost: TextView? = null
        var recipient: TextView? = null
        var date: TextView? = null
        var requestDescription: TextView? = null
        var requestQuantity: TextView? = null
        var totalCost: TextView? = null


        init {

            cost = view.findViewById(R.id.cost)
            date = view.findViewById(R.id.date)
            recipient = view.findViewById(R.id.recipient)
            requestDescription = view.findViewById(R.id.requestDescription)
            requestQuantity = view.findViewById(R.id.requestQuantity)
            totalCost = view.findViewById(R.id.totalCost)


        }


    }


}