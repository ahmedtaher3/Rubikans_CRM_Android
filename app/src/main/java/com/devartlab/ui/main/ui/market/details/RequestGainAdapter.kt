package com.devartlab.ui.main.ui.market.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.RequestGainDetail


class RequestGainAdapter(private val context: Context, private var myData: ArrayList<RequestGainDetail> ) : RecyclerView.Adapter<RequestGainAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.request_gain_item, parent, false)
        return ViewHolder(view)
    }

    interface OnRequestTypeClick {

        fun onRequestClick(typeId: Int)
    }

    fun setMyData(myData: ArrayList<RequestGainDetail>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder.itemQuantity?.text = model.qty.toString()
        holder.prodPharmacyPrice?.text = model.prodPharmacyPrice.toString()
        holder.prodXfactor?.text = model.prodXfactor.toString()
        holder.requestItemName?.text = model.itemEnName.toString()
        holder.totalProdPharmacyPrice?.text = model.totalPharmacyPrice.toString()
        holder.totalProdXfactor?.text = model.totalXfactor.toString()


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var itemQuantity: TextView? = null
        var prodPharmacyPrice: TextView? = null
        var prodXfactor: TextView? = null
        var requestItemName: TextView? = null
        var totalProdPharmacyPrice: TextView? = null
        var totalProdXfactor: TextView? = null

        init {

            itemQuantity = view.findViewById(R.id.itemQuantity)
            prodPharmacyPrice = view.findViewById(R.id.prodPharmacyPrice)
            prodXfactor = view.findViewById(R.id.prodXfactor)
            requestItemName = view.findViewById(R.id.requestItemName)
            totalProdPharmacyPrice = view.findViewById(R.id.totalProdPharmacyPrice)
            totalProdXfactor = view.findViewById(R.id.totalProdXfactor)


        }

    }


}