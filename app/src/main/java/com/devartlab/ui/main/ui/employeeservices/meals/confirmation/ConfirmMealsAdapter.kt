package com.devartlab.ui.main.ui.employeeservices.meals.confirmation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.model.MealRequest
import com.devartlab.utils.CommonUtilities
import java.util.*


class ConfirmMealsAdapter(private val context: Context, private var myData: ArrayList<MealRequest>, private var onMealRequestClick: OnMealRequestClick) : RecyclerView.Adapter<ConfirmMealsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.confirm_requests_item, parent, false)
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
        holder.notes?.text = model.notes
        holder.requestEmpName?.text = model.name
        holder.typeRequest?.text = model.meal

        holder.setName(model.meal.toString(), CommonUtilities.randomColor)


        holder.approveRequest?.setOnClickListener(View.OnClickListener {

            onMealRequestClick.setOnMealRequestClick(true , model.code)

        })

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var requestIcon: ImageView? = null
        var date: TextView? = null
        var requestEmpName: TextView? = null
        var typeRequest: TextView? = null
        var notes: TextView? = null


        var approveRequest: LinearLayout? = null
        var rejectRequest: LinearLayout? = null

        var view_list_main_content: LinearLayout? = null
        var mDrawableBuilder: TextDrawable? = null
        var itemStatus: LinearLayout? = null

        init {

            itemStatus = view.findViewById(R.id.itemStatus)
            date = view.findViewById(R.id.date)
            requestIcon = view.findViewById(R.id.requestIcon)
            requestEmpName = view.findViewById(R.id.requestEmpName)
            typeRequest = view.findViewById(R.id.typeRequest)
            notes = view.findViewById(R.id.notes)
            approveRequest = view.findViewById(R.id.approveRequest)
            rejectRequest = view.findViewById(R.id.rejectRequest)


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