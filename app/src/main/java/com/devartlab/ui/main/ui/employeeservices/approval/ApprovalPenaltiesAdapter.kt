package com.devartlab.ui.main.ui.employeeservices.approval

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.model.PenaltiesGoogle
import com.devartlab.utils.CommonUtilities
import java.text.SimpleDateFormat
import java.util.*


class ApprovalPenaltiesAdapter(
    private val context: Context,
    private var myData: ArrayList<PenaltiesGoogle>,
    private var onApprovalItemClick: OnPenaltiesApprovalItemClick,
    private var approveInterface: ApproveInterface
) : RecyclerView.Adapter<ApprovalPenaltiesAdapter.ViewHolder>() {

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.approve_penalties_item, parent, false)
        return ViewHolder(view)
    }

    interface OnPenaltiesApprovalItemClick {
        fun setOnPenaltiesApprovalItemClick(status: String, model: PenaltiesGoogle)
    }

    fun setMyData(myData: ArrayList<PenaltiesGoogle>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<PenaltiesGoogle> {
        return this.myData
    }

    fun addItem(model: PenaltiesGoogle) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        holder.approveCheckbox!!.isChecked = myData[position].checked!!


        holder.date?.text = model.date?.take(10)
        holder.typeRequest?.text = model.type
        holder.notes?.text = model.notes
        holder.requestEndIn?.text = model.reason
        holder.requestEmpName?.text = model.empName
        holder.penaltyOn?.text = "from : " + model.name


        if (model.approve == "APPROVED") {
            holder.rejectRequest?.visibility = View.GONE
            holder.approveRequest?.visibility = View.VISIBLE
            holder.approveRequestText?.text = "Approved"
            holder.approvedAt?.visibility = View.VISIBLE

            holder.approvedAt?.text = model.approveDate?.take(16)

        } else if (model.approve == "REFUSED") {
            holder.rejectRequest?.visibility = View.VISIBLE
            holder.approveRequest?.visibility = View.GONE
            holder.rejectRequestText?.text = "Rejected"
            holder.approvedAt?.visibility = View.VISIBLE

            holder.approvedAt?.text = model.approveDate?.take(16)


        }

        holder.setName(model.name.toString(), CommonUtilities.randomColor)












        holder.approveRequest?.setOnClickListener {

            onApprovalItemClick.setOnPenaltiesApprovalItemClick("APPROVED", model)

        }


        holder.rejectRequest?.setOnClickListener {

            onApprovalItemClick.setOnPenaltiesApprovalItemClick("REFUSED", model)
        }

        holder.approveCheckbox?.setOnClickListener {

            myData[position].checked = holder.approveCheckbox!!.isChecked


            if (holder.approveCheckbox!!.isChecked) {
                approveInterface.addApprove(
                    ApproveModel(
                        "Work From Home",
                        "approve",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "PENDING",
                        "",
                        simpleDateFormat?.format(System.currentTimeMillis()),
                        model.code!!
                    )
                )
            } else {
                approveInterface.removeApprove(
                    ApproveModel(
                        "Work From Home",
                        "approve",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "Approved",
                        "",
                        simpleDateFormat?.format(System.currentTimeMillis()),
                        model.code!!
                    )
                )
            }


        }


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
        var requestEmpName: TextView? = null
        var requestStartIn: TextView? = null
        var requestEndIn: TextView? = null
        var view_list_main_content: LinearLayout? = null
        var mDrawableBuilder: TextDrawable? = null
        var approveRequest: LinearLayout? = null
        var rejectRequest: LinearLayout? = null
        var approveRequestText: TextView? = null
        var rejectRequestText: TextView? = null
        var penaltyOn: TextView? = null
        var approvedAt: TextView? = null
        var approveCheckbox: CheckBox? = null

        init {

            penaltyOn = view.findViewById(R.id.penaltyOn)
            approvedAt = view.findViewById(R.id.approvedAt)
            date = view.findViewById(R.id.date)
            requestIcon = view.findViewById(R.id.requestIcon)
            typeRequest = view.findViewById(R.id.typeRequest)
            notes = view.findViewById(R.id.notes)
            requestStartIn = view.findViewById(R.id.requestStartIn)
            requestEndIn = view.findViewById(R.id.requestEndIn)
            view_list_main_content = view.findViewById(R.id.view_list_main_content)
            approveRequest = view.findViewById(R.id.approveRequest)
            rejectRequest = view.findViewById(R.id.rejectRequest)
            requestEmpName = view.findViewById(R.id.requestEmpName)

            approveRequestText = view.findViewById(R.id.approveRequestText)
            rejectRequestText = view.findViewById(R.id.rejectRequestText)
            approveCheckbox = view.findViewById(R.id.approveCheckbox)

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