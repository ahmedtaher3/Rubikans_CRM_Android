package com.devartlab.ui.main.ui.callmanagement.report.superreport

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.callmanagement.report.ReportInterface
import com.devartlab.ui.main.ui.callmanagement.report.arrange.ArrangeActivity
import java.util.*

private const val TAG = "SuperReportAdapter"

class SuperReportAdapter(context: Context, private val appCompatActivity: AppCompatActivity, private val dataManager: DataManager, private val updatePlan: UpdatePlan, private val reportInterface: ReportInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mDatas: ArrayList<PlanEntity>
    private val context: Context
    private var mDrawableBuilder: TextDrawable? = null


    var checkBox = false

    interface UpdatePlan {
        fun update(planEntity: PlanEntity?)
    }

    fun setDatas(datas: ArrayList<PlanEntity>) {
        mDatas.clear()
        mDatas.addAll(datas)
    }

    fun setCheckEnable(check: Boolean) {
        checkBox = check
        notifyDataSetChanged()
    }

    fun updateData(datas: ArrayList<PlanEntity>) {
        setDatas(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        view = if (dataManager.isTablet) LayoutInflater.from(context).inflate(R.layout.super_report_item, parent, false)
        else LayoutInflater.from(context).inflate(R.layout.super_report_item_mobile, parent, false)
        return ViewHolderSingle(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val baseViewHolder = holder as ViewHolderSingle
        baseViewHolder.bind(mDatas[position], position)
    }


    override fun getItemCount(): Int {
        return mDatas.size
    }

    inner class ViewHolderSingle(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var specialist: TextView
        var degree: TextView
        var activity: TextView
        var address: TextView
        var brick: TextView
        var doubleEmpName: TextView
        var makeCall: CheckBox
        var list_image_name: ImageView
        var mViewContent: View
        var deleteExtra: ImageView
        var call: ImageView
        var location: ImageView
        var checkVisit: ImageView
        fun bind(model: PlanEntity, position: Int) {


            makeCall.isEnabled = checkBox
            call.isEnabled = checkBox


            when (model.activityTypeID) {
                1 ->                     // ITEM_TYPE_SINGLE
                    try {
                        name.text = model.customerName
                        call.visibility = View.VISIBLE
                        makeCall.visibility = View.GONE
                    }
                    catch (e: Exception) {
                    }
                2 ->                     // ITEM_TYPE_DOUBLE
                    try {
                        name.text = model.customerName
                        call.visibility = View.GONE
                        makeCall.visibility = View.VISIBLE


                        doubleEmpName.text = "( " + model.doubleVisitEmpName + " )"

                    }
                    catch (e: Exception) {
                    }
                4 ->                     // ITEM_TYPE_OFFICE
                    try {
                        name.text = model.officeDescription
                        call.visibility = View.VISIBLE
                        makeCall.visibility = View.GONE
                    }
                    catch (e: Exception) {
                    }
                5 ->                     // ITEM_TYPE_OFFICE
                    try {
                        name.text = model.officeDescription
                        call.visibility = View.VISIBLE
                        makeCall.visibility = View.GONE
                    }
                    catch (e: Exception) {
                    }
                6 ->                     // ITEM_TYPE_MEETing
                    try {
                        name.text = model.meetingMembers
                        call.visibility = View.VISIBLE
                        makeCall.visibility = View.GONE
                    }
                    catch (e: Exception) {
                    }
                7 ->                     // ITEM_TYPE_SOCIAL
                    try {
                        name.text = model.customerName
                        call.visibility = View.VISIBLE
                        makeCall.visibility = View.GONE
                    }
                    catch (e: Exception) {
                    }
                else -> try {
                    name.text = model.customerName
                    call.visibility = View.VISIBLE
                    makeCall.visibility = View.GONE
                }
                catch (e: Exception) {
                }
            }
            activity.text = model.activityEnName
            degree.text = model._class
            specialist.text = model.speciality
            brick.text = model.brick
            address.text = model.address
            setName(model.customerName, model.planColor!!)
            makeCall.setOnClickListener {

                mDatas[position].visit = makeCall.isChecked
                updatePlan.update(mDatas[position])
            }

            call.setOnClickListener {


                if (model.activityTypeID == 1 || model.activityTypeID == 2) reportInterface.startVisit(model)
                else reportInterface.startSocialVisit(model)


                /*          val dialogBuilder = AlertDialog.Builder(context)
                          val dialogView: View = appCompatActivity.layoutInflater.inflate(R.layout.edit_layout, null)
                          dialogBuilder.setView(dialogView)


                          val call = dialogView.findViewById<LinearLayout>(R.id.call)
                          val order = dialogView.findViewById<LinearLayout>(R.id.order)
                          val cancel = dialogView.findViewById<LinearLayout>(R.id.cancel)
                          val collect = dialogView.findViewById<LinearLayout>(R.id.collect)

                          val alertDialog = dialogBuilder.create()

                          alertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                          alertDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                          call.setOnClickListener(View.OnClickListener {

                              if (model.activityTypeID == 1 || model.activityTypeID == 2)
                                  reportInterface.startVisit(model)
                              else
                                  reportInterface.startSocialVisit(model)

                              alertDialog.dismiss()
                          })

                          order.setOnClickListener(View.OnClickListener {

                              reportInterface.order(model)

                              alertDialog.dismiss()
                          })

                          collect.setOnClickListener(View.OnClickListener {

                              reportInterface.collect(model)

                              alertDialog.dismiss()
                          })


                          cancel.setOnClickListener(
                              View.OnClickListener { alertDialog?.dismiss() }
                          )

                          alertDialog.show()


          */

            }


            itemView.setOnLongClickListener {

                val intent = Intent(context, ArrangeActivity::class.java);
                intent.putExtra("CustomerModel", model);
                context.startActivity(intent);


                true
            }


            if (model.extraVisit!!) {
                mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                if (!model.reported!!) {
                    deleteExtra.visibility = View.VISIBLE
                }
            }
            else {
                mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                deleteExtra.visibility = View.GONE
            }
            if (model.visit!!) {
                makeCall.isChecked = true
            }
            else {
                makeCall.isChecked = false
            }
            if (model.visit!!) {

                location.visibility = View.VISIBLE
                if (model.cusLat == "" || model.cusLang == "" || model.cusLat == "0" || model.cusLang == "0" || model.cusLat == "1" || model.cusLang == "1") {

                    location.setImageResource(R.drawable.not_valid_location)
                    location.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_red_dark), PorterDuff.Mode.MULTIPLY)

                }
                else {
                    location.setImageResource(R.drawable.valid_location)
                    location.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_dark), PorterDuff.Mode.MULTIPLY)

                }




                checkVisit.setImageResource(R.drawable.ic_check_circle_grey600_24dp)
                checkVisit.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_light), PorterDuff.Mode.MULTIPLY)
            }
            else if (model.isStarted!!) {
                location.visibility = View.GONE
                checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                checkVisit.setColorFilter(ContextCompat.getColor(context, R.color.yellow), PorterDuff.Mode.MULTIPLY)
            }
            else {
                location.visibility = View.GONE
                checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                checkVisit.setColorFilter(ContextCompat.getColor(context, R.color.grey), PorterDuff.Mode.MULTIPLY)
            }

            itemView.setOnClickListener { Log.d(TAG, "bind: " + model.toString()) }

            deleteExtra.setOnClickListener { reportInterface.deleteExtra(model) }

        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder().buildRound(letter, color)
            list_image_name.setImageDrawable(mDrawableBuilder)
        }

        init {
            location = itemView.findViewById(R.id.location)
            name = itemView.findViewById(R.id.name)
            call = itemView.findViewById(R.id.call)
            specialist = itemView.findViewById(R.id.specialist)
            activity = itemView.findViewById(R.id.activity)
            brick = itemView.findViewById(R.id.brick)
            address = itemView.findViewById(R.id.address)
            checkVisit = itemView.findViewById(R.id.checkVisit)
            degree = itemView.findViewById(R.id.degree)
            makeCall = itemView.findViewById(R.id.makeCall)
            mViewContent = itemView.findViewById(R.id.view_list_main_content)
            list_image_name = itemView.findViewById(R.id.image)
            deleteExtra = itemView.findViewById(R.id.deleteExtra)
            doubleEmpName = itemView.findViewById(R.id.doubleEmpName)
        }
    }

    init {
        mDatas = ArrayList()
        this.context = context
    }
}