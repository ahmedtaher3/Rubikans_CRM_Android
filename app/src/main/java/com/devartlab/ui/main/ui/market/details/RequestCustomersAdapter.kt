package com.devartlab.ui.main.ui.market.details

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.model.RequestCustomer
import com.devartlab.utils.CommonUtilities


class RequestCustomersAdapter(private val context: Context, private var myData: ArrayList<RequestCustomer> ) : RecyclerView.Adapter<RequestCustomersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.request_customer_item, parent, false)
        return ViewHolder(view)
    }

    interface OnRequestTypeClick {

        fun onRequestClick(typeId: Int)
    }

    fun setMyData(myData: ArrayList<RequestCustomer>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder.customerClass?.text = model.cusClassEnName.toString()
        holder.customerName?.text = model.customerEnName.toString()
        holder.specialist?.text = model.cusTypeEnName
        holder.totalGain?.text = model.totalGain.toString()
        holder.setName(model.customerEnName.toString(), CommonUtilities.randomColor)


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var customerClass: TextView? = null
        var customerName: TextView? = null
        var specialist: TextView? = null
        var totalGain: TextView? = null

        var customerImage: ImageView? = null

        var mDrawableBuilder: TextDrawable? = null

        init {

            customerClass = view.findViewById(R.id.customerClass)
            customerImage = view.findViewById(R.id.customerImage)
            customerName = view.findViewById(R.id.customerName)
            specialist = view.findViewById(R.id.specialist)
            totalGain = view.findViewById(R.id.totalGain)


        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color)





            customerImage?.setImageDrawable(mDrawableBuilder)
        }
    }


}