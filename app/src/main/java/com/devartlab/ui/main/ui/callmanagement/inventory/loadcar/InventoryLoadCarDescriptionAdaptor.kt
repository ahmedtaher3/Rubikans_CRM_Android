package com.devartlab.ui.trade

import android.content.Context
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.INvnetoryTrxDetail
import com.devartlab.model.Summary
import com.getbase.floatingactionbutton.FloatingActionButton

class InventoryLoadCarDescriptionAdaptor (private val context: Context, private var myData: ArrayList<INvnetoryTrxDetail>, private var onInventoryTypeClick:OnInventoryTypeClick,
                                                private val onCustomerSelect: OnTypeSelect,
                                                private val st:String) : RecyclerView.Adapter<InventoryLoadCarDescriptionAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.inventory_load_car_description_iteam, parent, false)
        return ViewHolder(view)
    }

    interface OnInventoryTypeClick {

        fun onInventoryTypeClick(model: INvnetoryTrxDetail)
    }

    interface OnTypeSelect{
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder.inventoryMovesDetailsDescriptionItemItemNameTxt?.text = model.itemArName.toString()
        holder.inventoryMovesDetailsDescriptionItemStoreNameTxt?.text = model.storName.toString()
//        holder.inventoryMovesDetailsDescriptionItemQtyTxt?.text = model.qty.toString()
        holder.inventoryMovesDetailsDescriptionItemUnitTxt?.text = model.unitArName.toString()
        holder.coun?.setText(model.qty.toString())

//        if (st != "32")(
//                holder.inventoryDescriptionCountLay?.setVisibility(View.GONE)
//                )
//
//        if (st == "32")(
//                holder.inventoryMovesDetailsDescriptionItemQtyTxt?.setVisibility(View.GONE)
//                )

        holder.itemView.setOnClickListener {

            onInventoryTypeClick.onInventoryTypeClick(model)

        }


        holder.increaseBt?.setOnClickListener(View.OnClickListener {

            myData[position].qty = (holder.coun?.text.toString().toLong() + 1)
            notifyDataSetChanged()


        })


        holder.decreaseBt?.setOnClickListener(View.OnClickListener {
            if (holder.coun?.text.toString() != "0")
                myData[position].qty = (holder.coun?.text.toString().toLong() - 1)
            notifyDataSetChanged()

        })


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var inventoryMovesDetailsDescriptionItemItemNameTxt: TextView? = null
        var inventoryMovesDetailsDescriptionItemStoreNameTxt: TextView? = null
        //        var inventoryMovesDetailsDescriptionItemQtyTxt: TextView? = null
        var inventoryMovesDetailsDescriptionItemUnitTxt: TextView? = null
        var inventoryDescriptionCountLay: LinearLayout? = null
        var increaseBt: com.google.android.material.floatingactionbutton.FloatingActionButton? = null
        var decreaseBt: com.google.android.material.floatingactionbutton.FloatingActionButton? = null
        var coun: EditText? = null

        init {
            inventoryMovesDetailsDescriptionItemItemNameTxt = view.findViewById(R.id.inventoryMovesDetailsDescriptionItemItemNameTxt)
            inventoryMovesDetailsDescriptionItemStoreNameTxt = view.findViewById(R.id.inventoryMovesDetailsDescriptionItemStoreNameTxt)
//            inventoryMovesDetailsDescriptionItemQtyTxt = view.findViewById(R.id.inventoryMovesDetailsDescriptionItemQtyTxt)
            inventoryMovesDetailsDescriptionItemUnitTxt = view.findViewById(R.id.inventoryMovesDetailsDescriptionItemUnitTxt)
            inventoryDescriptionCountLay = view.findViewById(R.id.inventoryDescriptionCountLay)
            increaseBt = view.findViewById(R.id.increaseBt)
            decreaseBt = view.findViewById(R.id.decreaseBt)
            coun = view.findViewById(R.id.coun)


            coun?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    try {

                    } catch (e: Exception) {
                    }
                }

                override fun afterTextChanged(editable: Editable) {

                    try {
                        myData[adapterPosition].qty = editable.toString().toLong()
                    } catch (e: java.lang.Exception) {

                    }


                }
            })


        }

    }

}