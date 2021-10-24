package com.devartlab.ui.main.ui.employeeservices.meals.previousmeals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.model.MealRequest
import com.devartlab.utils.CommonUtilities
import java.util.*


class PreviousMealsAdapter(private val context: Context, private var myData: ArrayList<MealRequest>) : RecyclerView.Adapter<PreviousMealsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.meal_requests_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<MealRequest>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<MealRequest> {
        return this.myData
    }

    fun addItem(model: MealRequest) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.date?.text = model.date.take(10)
        holder.meal?.text = model.meal
        holder.mealPrice?.text = " (" +model.price + " LE)"
        holder.notes?.text = model.notes
        holder.mealType?.text = model.type
        holder.approvedAt?.text = "Delvried at : " + model.deliveredAt.takeLast(8)
        if (model.received) {
            holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            holder.approvedAt?.visibility = View.VISIBLE

        } else {
            holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            holder.approvedAt?.visibility = View.GONE

        }


        holder.setName(model.meal.toString(), CommonUtilities.randomColor)

        /*   holder.experienceDelete?.setOnClickListener {


               myData.removeAt(position)
               notifyDataSetChanged()

           }*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var requestIcon: ImageView? = null
        var date: TextView? = null
        var meal: TextView? = null
        var mealType: TextView? = null
        var notes: TextView? = null
        var approvedAt: TextView? = null
        var mealPrice: TextView? = null
        var view_list_main_content: LinearLayout? = null
        var mDrawableBuilder: TextDrawable? = null
        var itemStatus: LinearLayout? = null

        init {

            itemStatus = view.findViewById(R.id.itemStatus)
            date = view.findViewById(R.id.date)
            requestIcon = view.findViewById(R.id.requestIcon)
            meal = view.findViewById(R.id.meal)
            mealType = view.findViewById(R.id.mealType)
            notes = view.findViewById(R.id.notes)
            approvedAt = view.findViewById(R.id.approvedAt)
            mealPrice = view.findViewById(R.id.mealPrice)
            view_list_main_content = view.findViewById(R.id.view_list_main_content)

        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color)
            requestIcon?.setImageDrawable(mDrawableBuilder)
        }
    }


}