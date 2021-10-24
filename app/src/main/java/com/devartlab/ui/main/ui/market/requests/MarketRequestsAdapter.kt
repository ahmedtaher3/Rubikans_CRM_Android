package com.devartlab.ui.main.ui.market.requests

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.Detail


class MarketRequestsAdapter(private val context: Context, private var myData: ArrayList<Detail>, private var onRequestClick: OnRequestClick, private var mainAcc: Boolean) : RecyclerView.Adapter<MarketRequestsAdapter.ViewHolder>() {

    private var itemsCopy = java.util.ArrayList<Detail>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.market_requests_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<Detail>) {
        this.myData = myData
        this.itemsCopy.addAll(myData)
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<Detail> {
        return this.myData
    }

    interface OnRequestClick {
        fun setOnclickClick(model: Detail)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        if (!mainAcc) {
            holder.requestCheckBox?.visibility = View.GONE
        }

        holder.requestDate?.text = model.requestCode.toString()
        holder.date?.text = model.executeDate.toString().take(10)
        holder.requestDescription?.text = model.reqDescription.toString()
        holder.requestMonthlyGain?.text = model.getmOnthlyGain().toString()
        holder.requestTotalCost?.text = model.totalCoast.toString()
        holder.requestTotalGain?.text = model.totalGain.toString()
        holder.requestTypename?.text = model.requestType

        holder.requestCheckBox?.isChecked = model.selected

        holder.requestCheckBox?.setOnClickListener(View.OnClickListener {

            model.selected = holder.requestCheckBox?.isChecked

        })

        holder.itemView?.setOnClickListener {
            onRequestClick.setOnclickClick(model)

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var requestCheckBox: CheckBox? = null
        var requestDate: TextView? = null
        var requestDescription: TextView? = null
        var requestMonthlyGain: TextView? = null
        var requestTotalCost: TextView? = null
        var requestTotalGain: TextView? = null
        var requestTypename: TextView? = null
        var date: TextView? = null


        init {

            date = view.findViewById(R.id.date)
            requestCheckBox = view.findViewById(R.id.requestCheckBox)
            requestDate = view.findViewById(R.id.requestDate)
            requestDescription = view.findViewById(R.id.requestDescription)
            requestMonthlyGain = view.findViewById(R.id.requestMonthlyGain)
            requestTotalCost = view.findViewById(R.id.requestTotalCost)
            requestTotalGain = view.findViewById(R.id.requestTotalGain)
            requestTypename = view.findViewById(R.id.requestTypename)


        }
    }

    fun filter(text: String) {
        this.myData.clear()



        if (text.isEmpty()) {
            this.myData.addAll(itemsCopy)
        } else {


            for (model in itemsCopy) {
                //  || model.empArName!!.contains(text, ignoreCase = true) || model.empEnName!!.contains(text, ignoreCase = true)
                if (model.requestCode!!.contains(text, ignoreCase = true)) {
                    myData.add(model)
                }

            }

        }
        notifyDataSetChanged()
    }

    fun filterEmp(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

        this.myData.clear()



        if (text.isEmpty()) {

            this.myData.addAll(itemsCopy)
        } else {


            for (model in itemsCopy) {

                if (model.reqApplicantEmpId.toString().contains(text, ignoreCase = true)) {

                    myData.add(model)
                }

            }

        }
        notifyDataSetChanged()
    }
}