package com.devartlab.ui.main.ui.callmanagement.trade.selectProducts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.room.contract.ContractEntity
import kotlin.collections.ArrayList

class GeneralSelectedProductsAdapter(context: Context ) : RecyclerView.Adapter<GeneralSelectedProductsAdapter.MyViewHolder>() {
    private var myData: ArrayList<ContractEntity>
    private val context: Context
    fun getMyData(): ArrayList<ContractEntity> {
        return myData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.selected_items, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder.selectedName.text = model.itemEnName
        holder.selectedDelete.setOnClickListener {
            myData.removeAt(position)
            notifyDataSetChanged()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: ArrayList<ContractEntity>) {
        this.myData = myData
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

    fun addItem(planEntity: ContractEntity) {

            myData.add(planEntity)
            notifyDataSetChanged()

    }

    init {
        myData = ArrayList()
        this.context = context
    }
}