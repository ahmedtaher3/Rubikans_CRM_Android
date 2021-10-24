package com.devartlab.ui.main.ui.callmanagement.employee

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.data.shared.DataManager
import com.devartlab.model.EmployeeReport
import com.devartlab.model.StartEndPoint
import com.devartlab.utils.CommonUtilities
import java.util.*

private const val TAG = "EmployeeReportAdapter"

class EmployeeReportAdapter(context: Context, dataManager: DataManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mDatas: ArrayList<EmployeeReport>
    private val context: Context
    private var mDrawableBuilder: TextDrawable? = null
    var dataManager: DataManager
    var startShift: StartEndPoint
    var endShift: StartEndPoint
    var TYPE_HEADER = 0
    var TYPE_ITEM = 1
    var TYPE_Footer = 2
    fun setMyData(
        datas: ArrayList<EmployeeReport>,
        startShift: StartEndPoint,
        endShift: StartEndPoint
    ) {
        mDatas.clear()
        mDatas.addAll(datas)
        this.startShift = startShift
        this.endShift = endShift
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else if (position == mDatas.size) {
            TYPE_Footer
        } else {
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.employee_report_start_shift_item, parent, false)
            ViewHolderStartShift(view)
        } else if (viewType == TYPE_Footer) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.employee_report_end_shift_item, parent, false)
            ViewHolderEndShift(view)
        } else {
            view = if (dataManager.isTablet) LayoutInflater.from(context)
                .inflate(R.layout.employee_report_item, parent, false) else LayoutInflater.from(
                context
            ).inflate(R.layout.employee_report_item_mobile, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.d(TAG, "onBindViewHolder: " + endShift.toString())
        Log.d(TAG, "onBindViewHolder: " + startShift.toString())


        if (holder is ViewHolder) {
            holder.bind(mDatas[position])
        } else if (holder is ViewHolderStartShift) {


            try {
                holder.plan.text =
                    "Plan : " + CommonUtilities.getPlanText1(startShift.startPointDateTime!!)

            } catch (e: java.lang.Exception) {

            }
            try {
                holder.plan.text =
                    "Plan : " + CommonUtilities.getPlanText1(startShift.startPointDateTime!!)

            } catch (e: java.lang.Exception) {

            }

            try {
                holder.time.text =
                    "Start At : " + CommonUtilities.getPlanText2(startShift.startPointDateTime!!)

            } catch (e: java.lang.Exception) {
                try {
                    holder.time.text = "Start At : " + startShift.startPointDateTime!!
                } catch (e: java.lang.Exception) {


                }
            }

            try {
                holder.location.text = "Address : " + startShift.address
            } catch (e: java.lang.Exception) {

            }


        } else if (holder is ViewHolderEndShift) {

            try {
                holder.location.text = "Address : " + endShift.address
            } catch (e: java.lang.Exception) {

            }

            try {
                holder.time.text = "End At : " + endShift.startPointDateTime
            } catch (e: java.lang.Exception) {

            }


        }
    }

    override fun getItemCount(): Int {
        return mDatas.size + 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var specialist: TextView
        var degree: TextView
        var activity: TextView
        var address: TextView
        var brick: TextView
        var empName: TextView
        var checkVisit: ImageView
        var list_image_name: ImageView
        var location: ImageView
        var mViewContent: View
        fun bind(model: EmployeeReport) {
            when (model.activityId) {
                1 ->                     // ITEM_TYPE_SINGLE
                    try {
                        name.text = model.doctor
                        empName.text = model.doubleVisitEmpName.toString()
                    } catch (e: Exception) {
                    }
                2 ->                     // ITEM_TYPE_DOUBLE
                    try {
                        name.text = model.doctor
                        empName.text = model.doubleVisitEmpName.toString()
                    } catch (e: Exception) {
                    }
                4 ->                     // ITEM_TYPE_OFFICE
                    try {
                        name.text = model.doctor
                        empName.text = model.doubleVisitEmpName.toString()
                    } catch (e: Exception) {
                    }
                5 ->                     // ITEM_TYPE_OFFICE
                    try {
                        name.text = model.doctor
                        empName.text = model.doubleVisitEmpName.toString()
                    } catch (e: Exception) {
                    }
                6 ->                     // ITEM_TYPE_MEETing
                    try {
                        name.text = model.doctor
                        empName.text = model.doubleVisitEmpName.toString()
                    } catch (e: Exception) {
                    }
                7 ->                     // ITEM_TYPE_SOCIAL
                    try {
                        name.text = model.doctor
                        empName.text = model.doubleVisitEmpName.toString()
                    } catch (e: Exception) {
                    }
                else -> try {
                    name.text = model.doctor
                    empName.text = model.doubleVisitEmpName.toString()
                } catch (e: Exception) {
                }
            }




            activity.text = model.activity
            degree.text = model.class_
            specialist.text = model.specility
            brick.text = model.brick
            address.text = model.pmAddress
            setName(model.doctor, CommonUtilities.randomColor)



            if (model.expired) {
                mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
                checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                checkVisit.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        android.R.color.darker_gray
                    ), PorterDuff.Mode.MULTIPLY
                )
                location.visibility = View.GONE
            }
            else {

                if (model.isExtraVisit) {
                    mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                } else {
                    mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                }


                if (model.visited) {

                    location.visibility = View.VISIBLE
                    if (model.visitLat == "" ||model.visitLang == "" ||model.visitLat == "0" || model.visitLang == "0" || model.visitLat == "1" || model.visitLang == "1") {

                        location.setImageResource(R.drawable.not_valid_location)
                        location.setColorFilter(
                            ContextCompat.getColor(
                                context,
                                android.R.color.holo_red_dark
                            ), PorterDuff.Mode.MULTIPLY
                        )

                    } else {
                        location.setImageResource(R.drawable.valid_location)
                        location.setColorFilter(
                            ContextCompat.getColor(
                                context,
                                android.R.color.holo_green_dark
                            ), PorterDuff.Mode.MULTIPLY
                        )

                    }


                    checkVisit.setImageResource(R.drawable.ic_check_circle_grey600_24dp)
                    checkVisit.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            android.R.color.holo_green_light
                        ), PorterDuff.Mode.MULTIPLY
                    )
                } else {
                    location.visibility = View.GONE
                    checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                    checkVisit.setColorFilter(
                        ContextCompat.getColor(context, R.color.grey),
                        PorterDuff.Mode.MULTIPLY
                    )
                }

            }


        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color)
            list_image_name.setImageDrawable(mDrawableBuilder)
        }

        init {
            name = itemView.findViewById(R.id.name)
            location = itemView.findViewById(R.id.location)
            specialist = itemView.findViewById(R.id.specialist)
            activity = itemView.findViewById(R.id.activity)
            brick = itemView.findViewById(R.id.brick)
            address = itemView.findViewById(R.id.address)
            degree = itemView.findViewById(R.id.degree)
            empName = itemView.findViewById(R.id.empName)
            checkVisit = itemView.findViewById(R.id.checkVisit)
            mViewContent = itemView.findViewById(R.id.view_list_main_content)
            list_image_name = itemView.findViewById(R.id.list_image_name)
        }
    }

    inner class ViewHolderStartShift(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plan: TextView
        var time: TextView
        var location: TextView

        init {
            plan = itemView.findViewById(R.id.plan)
            time = itemView.findViewById(R.id.time)
            location = itemView.findViewById(R.id.location)
        }
    }

    inner class ViewHolderEndShift(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView
        var location: TextView

        init {
            time = itemView.findViewById(R.id.time)
            location = itemView.findViewById(R.id.location)
        }
    }

    init {
        mDatas = ArrayList()
        startShift = StartEndPoint()
        endShift = StartEndPoint()
        this.context = context
        this.dataManager = dataManager
    }
}