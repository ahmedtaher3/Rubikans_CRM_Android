package com.devartlab.ui.main.ui.callmanagement.plan

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.shared.DataManager
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "PlansAdapter"

class PlansAdapter(context: Context, dataManager: DataManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val myData: ArrayList<PlanEntity>
    private val context: Context
    private var mDrawableBuilder: TextDrawable? = null
    private val dataManager: DataManager
    fun setMyData(myData: ArrayList<PlanEntity>) {
        this.myData.clear()
        this.myData.addAll(myData)
        notifyDataSetChanged()
    }


    private val layoutInflater: LayoutInflater
        private get() = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view: View
        view = if (dataManager.isTablet) LayoutInflater.from(context)
            .inflate(R.layout.plan_item, parent, false) else LayoutInflater.from(context)
            .inflate(R.layout.plan_item_mobile, parent, false)
        return ViewHolderSingle(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val baseViewHolder =
            holder as ViewHolderSingle
        baseViewHolder.bind(myData[position])
    }

    private fun doDelete(adapterPosition: Int) {
        myData.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    fun move(from: Int, to: Int) {
        val prev = myData.removeAt(from)
        myData.add(if (to > from) to - 1 else to, prev)
        notifyItemMoved(from, to)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolderSingle(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var name: TextView
        var doubleEmpName: TextView
        var specialist: TextView
        var degree: TextView

        //public TextView activity;
        var brick: TextView
        var address: TextView
        var activity: TextView
        var list_image_name: ImageView
        var mViewContent: View
        var makeCall: Button? = null
        var updated: ImageView

        fun bind(model: PlanEntity) {
            if (model.updated!!) {
                updated.setImageResource(R.drawable.ic_check)
            } else {
                updated.setImageResource(R.drawable.ic_exclamation)
            }
            if (model.extraVisit!!) {
                mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            } else {
                mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            when (model.activityTypeID) {
                1 ->                     // SINGLE
                    try {
                        list_image_name.setImageResource(R.drawable.single_visit)
                        name.text = model.customerName
                        doubleEmpName.text = ""
                    } catch (e: Exception) {
                    }
                2 ->                     // DOUBLE
                    try {
                        list_image_name.setImageResource(R.drawable.double_visit)
                        name.text = model.customerName
                        doubleEmpName.text = "( " + model.doubleVisitEmpName + " )"
                    } catch (e: Exception) {
                    }
                4 ->                     //  Special Task
                    try {
                        list_image_name.setImageResource(R.drawable.special_task)
                        name.text = model.taskText
                        doubleEmpName.text = ""
                    } catch (e: Exception) {
                    }
                5 ->                     // OFFICE
                    try {
                        list_image_name.setImageResource(R.drawable.office)
                        name.text = model.officeDescription
                        doubleEmpName.text = ""
                    } catch (e: Exception) {
                    }
                6 ->                     // Meeting
                    try {
                        list_image_name.setImageResource(R.drawable.meeting)
                        name.text = model.meetingMembers
                        doubleEmpName.text = ""
                    } catch (e: Exception) {
                    }
                7 ->                     // SOCIAL
                    try {
                        list_image_name.setImageResource(R.drawable.single_visit)
                        name.text = model.customerName
                        doubleEmpName.text = ""
                    } catch (e: Exception) {
                    }
                else -> try {
                    list_image_name.setImageResource(R.drawable.single_visit)
                    name.text = model.customerName
                    doubleEmpName.text = ""
                } catch (e: Exception) {
                }
            }


            degree.text = model._class
            activity.text = model.activityEnName
            specialist.text = model.speciality
            brick.text = model.brick
            address.text = model.address

            itemView.setOnClickListener { Log.d(TAG, "bind: " + model.startAt) }


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

            name = v.findViewById(R.id.name)
            doubleEmpName = v.findViewById(R.id.doubleEmpName)
            specialist = v.findViewById(R.id.specialist)
            brick = v.findViewById(R.id.Brick)
            activity = v.findViewById(R.id.activity)
            mViewContent = v.findViewById(R.id.view_list_main_content)
            degree = v.findViewById(R.id.degree)
            address = v.findViewById(R.id.address)
            list_image_name = v.findViewById(R.id.list_image_name)
            updated = v.findViewById(R.id.updated)
        }

    }

    init {
        myData = ArrayList()
        this.context = context
        this.dataManager = dataManager
    }
}