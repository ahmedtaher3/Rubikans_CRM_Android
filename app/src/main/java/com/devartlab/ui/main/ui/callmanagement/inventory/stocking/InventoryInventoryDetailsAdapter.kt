package com.devartlab.ui.trade

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.VanStoctaking
import com.devartlab.databinding.InventoryDetailsItemBinding

class InventoryInventoryDetailsAdapter(
    private val context: Context,
    private var myData: ArrayList<VanStoctaking>
) : RecyclerView.Adapter<InventoryInventoryDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            InventoryDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ))
    }


    fun setMyData(myData: ArrayList<VanStoctaking>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<VanStoctaking> {
        return this.myData
    }

    fun addItem(model: VanStoctaking) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        var Count = model.qtyy - model.qty!!

        if (Count > 0 || Count < 0) {
            holder._binding?.count?.setTextColor(Color.parseColor("#FF0000"))
        }

        holder._binding?.name?.text = model.itemArName.toString()
        holder._binding?.unit?.text = model.unitArName.toString()
        holder._binding?.count?.text = (model.qtyy - model.qty!!).toString()


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: InventoryDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var _binding: InventoryDetailsItemBinding? = null

        init {
            this._binding = binding;



        }


    }

}

