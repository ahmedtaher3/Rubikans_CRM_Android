package com.devartlab.ui.main.ui.employeeservices.hrrequest

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
import com.devartlab.model.GoogleSheetUser
import com.devartlab.utils.CommonUtilities


class EmployeeRequestsAdapter(private val context: Context, private var myData: ArrayList<GoogleSheetUser>) : RecyclerView.Adapter<EmployeeRequestsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.employee_requests_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<GoogleSheetUser>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<GoogleSheetUser> {
        return this.myData
    }

    fun addItem(model: GoogleSheetUser) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.date?.text = model.date?.take(10)
        holder.typeRequest?.text = model.typeRequest
        holder.notes?.text = model.notes
        holder.requestStartIn?.text = model.requestStartIn
        holder.requestEndIn?.text = model.requestEndIn

        when (model.status?.uppercase()) {
            "PENDING" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))

            "APPROVED" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.green))

            "REFUSED" -> holder.itemStatus?.setBackgroundColor(ContextCompat.getColor(context, R.color.red))

        }


        holder.setName(model.typeRequest.toString(), CommonUtilities.randomColor)

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
        var typeRequest: TextView? = null
        var notes: TextView? = null
        var requestStartIn: TextView? = null
        var requestEndIn: TextView? = null
        var view_list_main_content: LinearLayout? = null
        var mDrawableBuilder: TextDrawable? = null
        var itemStatus: LinearLayout? = null

        init {

            itemStatus = view.findViewById(R.id.itemStatus)
            date = view.findViewById(R.id.date)
            requestIcon = view.findViewById(R.id.requestIcon)
            typeRequest = view.findViewById(R.id.typeRequest)
            notes = view.findViewById(R.id.notes)
            requestStartIn = view.findViewById(R.id.requestStartIn)
            requestEndIn = view.findViewById(R.id.requestEndIn)
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