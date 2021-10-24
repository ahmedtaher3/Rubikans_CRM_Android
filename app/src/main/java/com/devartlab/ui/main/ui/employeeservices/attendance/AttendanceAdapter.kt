package com.devartlab.ui.main.ui.employeeservices.attendance

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.EMployeeDayList_Class
import com.devartlab.model.EmployeeData_class
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.utils.ChangeDoctorData
import java.util.*

class AttendanceAdapter(context: Context, changeDoctorData: ChangeDoctorData, private val onDayClick: OnDayClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var myData: ArrayList<EMployeeDayList_Class>
    private val context: Context
    private val changeDoctorData: ChangeDoctorData
    private var employeeData_class = EmployeeData_class()
    private var empModel =
        FilterDataEntity(fileImage = "")
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    init {
        myData = ArrayList()
        this.context = context
        this.changeDoctorData = changeDoctorData
    }

    interface OnDayClick {
        fun showDetails(rowIndex: Int)
    }

    fun setMyData(myData: ArrayList<EMployeeDayList_Class>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun setdrData(employeeData_class: EmployeeData_class , empModel: FilterDataEntity) {
        this.employeeData_class = employeeData_class
        this.empModel = empModel
        notifyDataSetChanged()
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

            val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.header_item_attendance, parent, false)
            return HeaderViewHolder(layoutView)
        } else {

            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.attendance_recyler_item, parent, false)
            return MyViewHolder(itemView)

        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val model = myData[position]
        if (holder is MyViewHolder) {
            val model = myData[position]
            holder._Rowindex.text = Character.toString(model.dayDate[8]) + Character.toString(model.dayDate[9])
            holder.Day.text = model.day.take(3)
            holder.DayDate.text = model.dayDate.take(7)
            holder.Checkin.text = model.checkin
            holder.CheckOut.text = model.checkOut
            holder.LateTime.text = model.lateTime
            holder.LeaveEarlyTime.text = model.leaveEarlyTime
            holder.DayNatural.text = model.dayNatural
            if (model.isStopSalaryPenalties != null) {

                if (model.isStopSalaryPenalties == true) {
                    holder.isStopSalaryPenalties.visibility = View.VISIBLE
                    holder.isStopSalaryPenalties.setOnClickListener { onDayClick.showDetails(model._Rowindex) }
                }
            } else {
                holder.isStopSalaryPenalties.visibility = View.GONE
            }
            if (model.permitionToLeave == true) {
                holder.permitionToLeave.visibility = View.VISIBLE
                holder.permitionToLeave.setOnClickListener { onDayClick.showDetails(model._Rowindex) }
            } else {
                holder.permitionToLeave.visibility = View.GONE
            }
            if (model.errand == true) {
                holder.errand.visibility = View.VISIBLE
                holder.errand.setOnClickListener { onDayClick.showDetails(model._Rowindex) }
            } else {
                holder.errand.visibility = View.GONE
            }
            if (model.tradinessPenalties == true) {
                holder.tradinessPenalties.visibility = View.VISIBLE
                holder.tradinessPenalties.setOnClickListener { onDayClick.showDetails(model._Rowindex) }
            } else {
                holder.tradinessPenalties.visibility = View.GONE
            }
            if (model.noFingerFpPenalties == true) {
                holder.noFingerFpPenalties.visibility = View.VISIBLE
                holder.noFingerFpPenalties.setOnClickListener { onDayClick.showDetails(model._Rowindex) }
            } else {
                holder.noFingerFpPenalties.visibility = View.GONE
            }
            if (model.isAbsentWithoutPermission == true) {
                holder.isAbsentWithoutPermission.visibility = View.VISIBLE
                holder.isAbsentWithoutPermission.setOnClickListener { onDayClick.showDetails(model._Rowindex) }
            } else {
                holder.isAbsentWithoutPermission.visibility = View.GONE
            }
            if (model.overTime == true) {
                holder.overTime.visibility = View.VISIBLE
                holder.overTime.setOnClickListener { onDayClick.showDetails(model._Rowindex) }
            } else {
                holder.overTime.visibility = View.GONE
            }
            //        holder.color1green.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "StopSalaryPenalties", Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "sdfdsfsdfs", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
        } else if (holder is HeaderViewHolder) {

            holder.dr_name.setText(employeeData_class.empNickName)
            holder.dr_title.setText(employeeData_class.jobArName)
            holder.dr_image?.setImageResource(R.drawable.user_logo)
            if (!empModel.fileImage.isNullOrEmpty()) {
                val decodedString: ByteArray = Base64.decode(empModel.fileImage, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                holder.dr_image.setImageBitmap(decodedByte)
            }

            holder.dr_department.setText(employeeData_class.deptArName)
            holder.dr_deduction.setText(employeeData_class.totalDeduction)
            holder.dr_deduction.setOnClickListener(View.OnClickListener {
                onDayClick.showDetails(-1)
            })
            holder.dr_image.setOnClickListener(View.OnClickListener {
                changeDoctorData.changeDrData()
            })
            holder.attendanceIconsInfo.setOnClickListener(View.OnClickListener {
                changeDoctorData.showInfo()
            })
        }


    }

    override fun getItemCount(): Int {
        return myData.size
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _Rowindex: TextView
        var Day: TextView
        var DayDate: TextView
        var Checkin: TextView
        var CheckOut: TextView
        var LateTime: TextView
        var LeaveEarlyTime: TextView
        var DayNatural: TextView
        var isStopSalaryPenalties: ImageView
        var permitionToLeave: ImageView
        var errand: ImageView
        var tradinessPenalties: ImageView
        var noFingerFpPenalties: ImageView
        var isAbsentWithoutPermission: ImageView
        var overTime: ImageView

        init {
            _Rowindex = itemView.findViewById(R.id._Rowindex)
            Day = itemView.findViewById(R.id.Day)
            DayDate = itemView.findViewById(R.id.DayDate)
            Checkin = itemView.findViewById(R.id.txt_num_ckeckin)
            CheckOut = itemView.findViewById(R.id.txt_num_ckeckout)
            LateTime = itemView.findViewById(R.id.LateTime)
            LeaveEarlyTime = itemView.findViewById(R.id.LeaveEarlyTime)
            DayNatural = itemView.findViewById(R.id.DayNatural)
            isStopSalaryPenalties = itemView.findViewById(R.id.isStopSalaryPenalties)
            permitionToLeave = itemView.findViewById(R.id.permitionToLeave)
            errand = itemView.findViewById(R.id.errand)
            tradinessPenalties = itemView.findViewById(R.id.tradinessPenalties)
            noFingerFpPenalties = itemView.findViewById(R.id.noFingerFpPenalties)
            isAbsentWithoutPermission = itemView.findViewById(R.id.isAbsentWithoutPermission)
            overTime = itemView.findViewById(R.id.overTime)
        }
    }


    class HeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var dr_image: ImageView
        var attendanceIconsInfo: ImageView
        var dr_name: TextView
        var dr_title: TextView
        var dr_department: TextView
        var dr_deduction: TextView
        var dr_deduction_layout: RelativeLayout

        init {

            dr_image = v.findViewById(R.id.empImage)
            attendanceIconsInfo = v.findViewById(R.id.attendanceIconsInfo)
            dr_name = v.findViewById(R.id.dr_name)
            dr_title = v.findViewById(R.id.dr_title)
            dr_department = v.findViewById(R.id.dr_department)
            dr_deduction = v.findViewById(R.id.dr_deduction)
            dr_deduction_layout = v.findViewById(R.id.dr_deduction_layout)

        }
    }

}