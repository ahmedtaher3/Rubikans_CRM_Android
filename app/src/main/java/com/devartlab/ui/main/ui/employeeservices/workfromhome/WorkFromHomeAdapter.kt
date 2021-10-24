package com.devartlab.ui.main.ui.employeeservices.workfromhome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.WorkFromHomeModel
import com.devartlab.utils.CommonUtilities
import java.text.SimpleDateFormat
import java.util.*


class WorkFromHomeAdapter(private val context: Context, private var myData: ArrayList<WorkFromHomeModel>) : RecyclerView.Adapter<WorkFromHomeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.work_from_home_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<WorkFromHomeModel>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<WorkFromHomeModel> {
        return this.myData
    }

    fun addItem(model: WorkFromHomeModel) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.Day?.text = CommonUtilities.getDayName(CommonUtilities.convertDateToMillis(model.date?.take(10))).take(3)
        holder.dayIndex?.text = Character.toString(model.date!![8]) + Character.toString(model.date!![9])
        holder.DayDate?.text = model.date?.take(7)

        holder.typeRequest?.text = "Work From Home"


        holder.requestStartIn?.text = model.date?.takeLast(8)
        holder.requestEndIn?.text = model.end?.takeLast(8)


        when (model.approve) {
            "PENDING" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))

            "APPROVED" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.green))

            "REFUSED" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.red))

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case

        var typeRequest: TextView? = null
        var DayDate: TextView? = null
        var dayIndex: TextView? = null
        var Day: TextView? = null
        var requestStartIn: TextView? = null
        var requestEndIn: TextView? = null
        var notes: TextView? = null

        var container: LinearLayout? = null
        var itemStatus: LinearLayout? = null


        init {


            typeRequest = view.findViewById(R.id.typeRequest)
            DayDate = view.findViewById(R.id.DayDate)
            dayIndex = view.findViewById(R.id.dayIndex)
            Day = view.findViewById(R.id.Day)
            requestStartIn = view.findViewById(R.id.requestStartIn)
            requestEndIn = view.findViewById(R.id.requestEndIn)
            notes = view.findViewById(R.id.notes)

            container = view.findViewById(R.id.container)
            itemStatus = view.findViewById(R.id.itemStatus)


        }

    }

    private fun isToday(str: String): Boolean {

        val sdf = SimpleDateFormat("EEEE", Locale.US)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)

        return dayOfTheWeek == str

    }
}