package com.devartlab.ui.main.ui.callmanagement.inventory.status

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.INvnetory
import com.devartlab.model.Summary


class InventoryRequestsAdapter (private val context: Context, private var myData: java.util.ArrayList<INvnetory>, private var onInventoryTypeClick: OnInventoryTypeClick,
                                private val onCustomerSelect: OnTypeSelect
) : RecyclerView.Adapter<InventoryRequestsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.inventory_requests_statues_iteam, parent, false)
        return ViewHolder(view)
    }

    interface OnInventoryTypeClick {

        fun onInventoryTypeClick(model: INvnetory)
    }

    interface OnTypeSelect{
        fun setOnTypeSelect(model: Summary)
    }

    fun setMyData(myData: java.util.ArrayList<INvnetory>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): java.util.ArrayList<INvnetory> {
        return this.myData
    }

    fun addItem(model: INvnetory) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        holder.inventoryStatusMoveName?.text = model.trxTypeDescription
        holder.inventoryStatusStoreName?.text = model.storName
        holder.inventoryStatusDate?.text = model.trxdate
        when (model.approved) {
            false -> holder.inventoryStatusStatues?.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))

            true -> holder.inventoryStatusStatues?.setBackgroundColor(ContextCompat.getColor(context, R.color.green))

        }



        holder.itemView.setOnClickListener {
            onInventoryTypeClick.onInventoryTypeClick(model)
        }



    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var inventoryStatusMoveName: TextView? = null
        var inventoryStatusStoreName: TextView? = null
        var inventoryStatusDate: TextView? = null
        var inventoryStatusStatues: LinearLayout? = null


        init {
            inventoryStatusMoveName = view.findViewById(R.id.inventoryStatusMoveName)
            inventoryStatusStoreName = view.findViewById(R.id.inventoryStatusStoreName)
            inventoryStatusDate = view.findViewById(R.id.inventoryStatusDate)
            inventoryStatusStatues = view.findViewById(R.id.inventoryStatusStatues)


        }

    }

}











//
//
//
//
//
//
//(private val context: Context, private var myData: java.util.ArrayList<INvnetory>, private var onInventoryTypeClick:OnInventoryTypeClick,
//                                private val onCustomerSelect: OnTypeSelect) : RecyclerView.Adapter<InventoryRequestsAdapter.ViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view: View
//        view = LayoutInflater.from(context).inflate(R.layout.inventory_requests_statues_iteam, parent, false)
//        return ViewHolder(view)
//    }
//
//    interface OnInventoryTypeClick {
//
//        fun onInventoryTypeClick(model: INvnetory)
//    }
//
//    interface OnTypeSelect{
//        fun setOnTypeSelect(model: Summary)
//    }
//
//    fun setMyData(myData: java.util.ArrayList<INvnetory>) {
//        this.myData = myData
//        notifyDataSetChanged()
//    }
//
//    fun getMyData(): java.util.ArrayList<INvnetory> {
//        return this.myData
//    }
//
//    fun addItem(model: INvnetory) {
//        this.myData.add(model)
//        notifyDataSetChanged()
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val model = myData[position]
//
//
//
//        holder.inventoryStatusMoveName?.text = model.trxTypeDescription
//        holder.inventoryStatusStoreName?.text = model.storName
//        holder.inventoryStatusDate?.text = model.trxdate
//        when (model.approved) {
//            false -> holder.inventoryStatusStatues?.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
//
//            true -> holder.inventoryStatusStatues?.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
//
//        }
//
//        holder.itemView.setOnClickListener {
//
//
//            onInventoryTypeClick.onInventoryTypeClick(model)
//
//
//        }
//
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount(): Int {
//        return myData.size
//    }
//
//    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
//        // each data item is just a string in this case
//        var inventoryStatusMoveName: TextView? = null
//        var inventoryStatusStoreName: TextView? = null
//        var inventoryStatusDate: TextView? = null
//        var inventoryStatusStatues: LinearLayout? = null
//
//
//
//        init {
//            inventoryStatusMoveName = view.findViewById(R.id.inventoryStatusMoveName)
//            inventoryStatusStoreName = view.findViewById(R.id.inventoryStatusStoreName)
//            inventoryStatusDate = view.findViewById(R.id.inventoryStatusDate)
//            inventoryStatusStatues = view.findViewById(R.id.inventoryStatusStatues)
//
//        }
//
//
//    }
//
//
//}
//
