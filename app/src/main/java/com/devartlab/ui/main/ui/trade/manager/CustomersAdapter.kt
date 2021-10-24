package com.devartlab.ui.main.ui.trade.manager

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.CustomerTrade
import com.devartlab.model.TradeDay


class CustomersAdapter(private val context: Context, private var myData: ArrayList<TradeDay>, private val onDaySelect: OnDaySelect) : RecyclerView.Adapter<CustomersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.customer_trade_item, parent, false)
        return ViewHolder(view)
    }

    interface OnDaySelect {
        fun setOnDaySelect(model: TradeDay)
    }

    fun setMyData(myData: ArrayList<TradeDay>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<TradeDay> {
        return this.myData
    }

    fun addItem(model: TradeDay) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



   /*     if (model.startDayLat == "1" || model.startDayLong == "1" ||
                model.startBreakLat == "1" || model.startBreakLong == "1" ||
                model.endBreakLat == "1" || model.endBreakLong == "1" ||
                model.endDayLat == "1" || model.endDayLong == "1"
        ) {


            // Fake visit

            holder.validation?.text = "Fake Visit"
            holder.validation?.setTextColor(Color.parseColor("#FF1100"))

        } else if (model.startDayLat == "" || model.startDayLong == "" ||
                model.startBreakLat == "" || model.startBreakLong == "" ||
                model.endBreakLat == "" || model.endBreakLong == "" ||
                model.endDayLat == "" || model.endDayLong == ""
        ) {


            // unComplete visit

            holder.validation?.text = "unComplete Visit"
            holder.validation?.setTextColor(Color.parseColor("#FF9800"))


        } else {


            // valid visit

            holder.validation?.text = "Valid Visit"
            holder.validation?.setTextColor(Color.parseColor("#4CAF50"))


        }


        holder.name?.text = model.startDayAt?.take(10)
        holder.credit?.text = model.customerName

        holder.itemView.setOnClickListener {
            onDaySelect.setOnDaySelect(model)
        }
*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var name: TextView? = null
        var validation: TextView? = null
        var credit: TextView? = null


        init {
            credit = view.findViewById(R.id.credit)
            name = view.findViewById(R.id.name)
            validation = view.findViewById(R.id.validation)


        }


    }


}