package com.devartlab.ui.main.ui.employeeservices.meals

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.Meal
import java.text.SimpleDateFormat
import java.util.*


class WeekMealsAdapter(private val context: Context, private var myData: ArrayList<Meal>) : RecyclerView.Adapter<WeekMealsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.meal_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<Meal>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<Meal> {
        return this.myData
    }

    fun addItem(model: Meal) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.dayName?.text = model.dayAr
        holder.meal?.text = model.meal
        holder.free?.text = model.free
        holder.diet?.text = model.diet

        if (isToday(model.day)) {
            holder.container?.setBackgroundResource(R.color.colorPrimary)

            holder.dayName?.setTextColor(Color.parseColor("#ffffff"))
            holder.meal?.setTextColor(Color.parseColor("#ffffff"))
            holder.diet?.setTextColor(Color.parseColor("#ffffff"))
            holder.free?.setTextColor(Color.parseColor("#ffffff"))
        } else {
            holder.container?.setBackgroundResource(R.color.white)
            holder.dayName?.setTextColor(Color.parseColor("#000000"))
            holder.meal?.setTextColor(Color.parseColor("#000000"))
            holder.diet?.setTextColor(Color.parseColor("#000000"))
            holder.free?.setTextColor(Color.parseColor("#000000"))

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case

        var dayName: TextView? = null
        var meal: TextView? = null
        var diet: TextView? = null
        var free: TextView? = null

        var container: LinearLayout? = null


        init {


            dayName = view.findViewById(R.id.dayName)
            meal = view.findViewById(R.id.meal)
            diet = view.findViewById(R.id.diet)
            free = view.findViewById(R.id.free)

            container = view.findViewById(R.id.container)


        }

    }

    private fun isToday(str: String): Boolean {

        val sdf = SimpleDateFormat("EEEE", Locale.US)
        val d = Date()
        val dayOfTheWeek: String = sdf.format(d)

        return dayOfTheWeek == str

    }
}