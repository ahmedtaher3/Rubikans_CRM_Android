package com.devartlab.ui.main.ui.callmanagement.ranks.planandcover


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.CustomerAcher
import com.devartlab.model.CustomerTrade
import com.devartlab.model.StartPointReportTitle
import com.devartlab.model.TradeDay


class PlanAndCoverPlanDetails(private val context: Context, private var myData: ArrayList<StartPointReportTitle>, private val onTitleSelect: OnTitleSelect) : RecyclerView.Adapter<PlanAndCoverPlanDetails.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.customer_report_item, parent, false)
        return ViewHolder(view)
    }

    interface OnTitleSelect {
        fun setOnTitleSelect(model: StartPointReportTitle)
    }

    fun setMyData(myData: ArrayList<StartPointReportTitle>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<StartPointReportTitle> {
        return this.myData
    }

    fun addItem(model: StartPointReportTitle) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.name?.text = model.jobArName

        holder.itemView.setOnClickListener {
            onTitleSelect.setOnTitleSelect(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var name: TextView? = null


        init {

            name = view.findViewById(R.id.name)


        }


    }


}

