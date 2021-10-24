package com.devartlab.ui.main.ui.employeeservices.vacation

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.shared.DataManager
import com.devartlab.model.EmployeeVacation
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.utils.ChangeDoctorData
import com.devartlab.utils.MyHeaderViewHolder
import java.util.*


class VacationAdapter(context: Context, dataManager: DataManager, changeDoctorData: ChangeDoctorData) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var myData: ArrayList<EmployeeVacation>
    private val context: Context
    private val dataManager: DataManager
    private val changeDoctorData: ChangeDoctorData
    private var empModel =
        FilterDataEntity(fileImage = "")
    val TYPE_HEADER = 0
    val TYPE_ITEM = 1

    init {
        myData = ArrayList()
        this.context = context
        this.dataManager = dataManager
        this.changeDoctorData = changeDoctorData
    }

    override fun getItemViewType(position: Int): Int {

        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        if (viewType == TYPE_HEADER) {

            val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
            return MyHeaderViewHolder(layoutView)
        } else {

            val itemView = if (dataManager.isTablet) LayoutInflater.from(parent.context).inflate(R.layout.vacation_item, parent, false) else LayoutInflater.from(parent.context).inflate(R.layout.vacation_item_mobile, parent, false)
            return MyViewHolder(itemView)

        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        if (holder is MyViewHolder) {
            val model = myData[position]
            try {
                holder.VacationCount.text = model.dayNum.toString() + " Day"

            } catch (e: Exception) {

            }
            holder.VacationType.text = model.vacTypeArName
            holder.VacationFrom.text = "From : " + model.fromDate.take(10)
            holder.VacationTo.text = "To : " + model.toDate.take(10)
            holder.pendingBy.text = model.pendingUser.toString()
            /*
        Glide.with(context)
                    .load(model.expTypeIconUrl)
                    .placeholder(holder.ExpensesIcon.drawable)
                    .into(holder.ExpensesIcon)*/

            if (model.vacStatus == 2) {
                holder.check.setImageResource(R.drawable.ic_check_circle_grey600_24dp);
                holder.check.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_light), android.graphics.PorterDuff.Mode.MULTIPLY);

            } else if (model.vacStatus == 1) {
                holder.check.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp);
                holder.check.setColorFilter(ContextCompat.getColor(context, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);

            } else {
                holder.check.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp);
                holder.check.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        } else if (holder is MyHeaderViewHolder) {
            holder.empImage.setImageResource(R.drawable.user_logo)
            holder.dr_name.setText(empModel.empName)
            holder.dr_title.setText(empModel.empTitle)
            if (!empModel.fileImage.isNullOrEmpty()) {
                val decodedString: ByteArray = Base64.decode(empModel.fileImage, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                holder.empImage.setImageBitmap(decodedByte)
            }

            holder.empImage.setOnClickListener(View.OnClickListener {
                changeDoctorData.changeDrData()
            })
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: ArrayList<EmployeeVacation>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun setdrData(empModel: FilterDataEntity) {
        this.empModel = empModel
        notifyDataSetChanged()
    }

    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var VacationCount: TextView
        var VacationType: TextView
        var VacationFrom: TextView
        var VacationTo: TextView
        var VacationIcon: ImageView
        var check: ImageView
        var pendingBy: TextView

        init {

            pendingBy = v.findViewById(R.id.pendingBy)
            check = v.findViewById(R.id.check)
            VacationIcon = v.findViewById(R.id.VacationIcon)
            VacationCount = v.findViewById(R.id.VacationCount)
            VacationType = v.findViewById(R.id.VacationType)
            VacationFrom = v.findViewById(R.id.VacationFrom)
            VacationTo = v.findViewById(R.id.VacationTo)

        }
    }


}