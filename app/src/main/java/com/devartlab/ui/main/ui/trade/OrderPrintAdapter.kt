package com.devartlab.ui.main.ui.trade

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.room.contract.ContractEntity


class OrderPrintAdapter(private val context: Context, private var myData: ArrayList<ContractEntity>) : RecyclerView.Adapter<OrderPrintAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.order_print_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<ContractEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<ContractEntity> {
        return this.myData
    }

    fun addItem(model: ContractEntity) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.name?.text = model.itemArName
        holder.price?.text = model.price.toString()
        holder.unit?.text = model.count.toString()
        holder.amount?.text = (model.count!! * model.price!!).toString()


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var name: TextView? = null
        var unit: TextView? = null
        var price: TextView? = null
        var amount: TextView? = null


        init {
            unit = view.findViewById(R.id.unit)
            name = view.findViewById(R.id.name)
            price = view.findViewById(R.id.price)
            amount = view.findViewById(R.id.amount)


        }


    }


}