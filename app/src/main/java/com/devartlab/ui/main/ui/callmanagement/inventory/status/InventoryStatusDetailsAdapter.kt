package com.devartlab.ui.main.ui.callmanagement.inventory.status

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.data.retrofit.INvnetoryTrxDetail
import com.devartlab.databinding.InventoryStatusDescItemBinding
import com.devartlab.model.Summary

class InventoryStatusDetailsAdapter(private val context: Context, private var myData: ArrayList<INvnetoryTrxDetail>, private var onInventoryTypeClick: InventoryStatusDetailsActivity,
                                    private val onCustomerSelect: InventoryStatusDetailsActivity
                                    , private val statues: Boolean) : RecyclerView.Adapter<InventoryStatusDetailsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        return MyViewHolder(
            InventoryStatusDescItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )


    }

    interface OnInventoryTypeClick {

        fun onInventoryTypeClick(model: INvnetoryTrxDetail)
    }

    interface OnTypeSelect {
        fun setOnTypeSelect(model: Summary)
    }

    fun setMyData(myData: ArrayList<INvnetoryTrxDetail>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<INvnetoryTrxDetail> {
        return this.myData
    }

    fun addItem(model: INvnetoryTrxDetail) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]



        holder._binding?.name?.text = model.itemArName.toString()
//        holder.inventoryMovesDetailsDescriptionItemQtyTxt?.text = model.qty.toString()
        holder._binding?.desc?.text = model.unitArName.toString()
        holder._binding?.count?.setText(model.qty.toString())

        holder.itemView.setOnClickListener {

            onInventoryTypeClick.onInventoryTypeClick(model)

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }


    inner class MyViewHolder(var binding: InventoryStatusDescItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var _binding: InventoryStatusDescItemBinding? = null

        init {
            this._binding = binding;

        }


    }
}