package com.devartlab.ui.main.ui.market.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.model.RequestApprovedBy
import com.devartlab.utils.CommonUtilities


class RequestApproveAdapter(private val context: Context, private var myData: ArrayList<RequestApprovedBy>) : RecyclerView.Adapter<RequestApproveAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.request_approved_by_item, parent, false)
        return ViewHolder(view)
    }

    interface OnRequestTypeClick {

        fun onRequestClick(typeId: Int)
    }

    fun setMyData(myData: ArrayList<RequestApprovedBy>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder.actionStatus?.text = model.action.toString()
        holder.empName?.text = model.employee.toString()


        holder.setName(model.employee.toString(), CommonUtilities.randomColor)


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {


        var actionStatus: TextView? = null
        var empImage: ImageView? = null
        var empName: TextView? = null
        var mDrawableBuilder: TextDrawable? = null


        init {

            actionStatus = view.findViewById(R.id.actionStatus)
            empName = view.findViewById(R.id.empName)

            empImage = view.findViewById(R.id.empImage)


        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color)
            empImage?.setImageDrawable(mDrawableBuilder)
        }
    }


}