package com.devartlab.ui.trade

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.VanStoctaking
import com.devartlab.databinding.DoubleVisitReportItemBinding
import com.devartlab.databinding.InventoryInventoryIteamBinding
import com.devartlab.model.Summary

class InventoryInventoryAdapter(private val context: Context, private var myData: ArrayList<VanStoctaking>, private var onInventoryTypeClick: OnInventoryTypeClick,
                                private val onCustomerSelect: OnTypeSelect) : RecyclerView.Adapter<InventoryInventoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
                InventoryInventoryIteamBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )

    }

    interface OnInventoryTypeClick {

        fun onInventoryTypeClick(model: VanStoctaking)
    }

    interface OnTypeSelect {
        fun setOnTypeSelect(model: Summary)
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


        holder._binding?.inventoryMovesDetailsDescriptionItemItemNameTxt?.text = model.itemArName.toString()
        holder._binding?.coun?.setText(model.qtyy.toString())



        holder.itemView.setOnClickListener {

            onInventoryTypeClick.onInventoryTypeClick(model)

        }


        holder._binding?.increaseBt?.setOnClickListener(View.OnClickListener {

            myData[position].qtyy = (holder._binding?.coun?.text.toString().toLong() + 1)
            notifyDataSetChanged()


        })


        holder._binding?.decreaseBt?.setOnClickListener(View.OnClickListener {
            if (holder._binding?.coun?.text.toString() != "0")
                myData[position].qtyy = (holder._binding?.coun?.text.toString().toLong() - 1)
            notifyDataSetChanged()

        })


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }


    inner class ViewHolder(var binding: InventoryInventoryIteamBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: InventoryInventoryIteamBinding? = null

        init {
            this._binding = binding;

            this._binding?.coun?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    try {

                    } catch (e: Exception) {
                    }
                }

                override fun afterTextChanged(editable: Editable) {

                    try {
                        myData[adapterPosition].qtyy = editable.toString().toLong()
                    } catch (e: java.lang.Exception) {

                    }


                }
            })

        }


    }




}
