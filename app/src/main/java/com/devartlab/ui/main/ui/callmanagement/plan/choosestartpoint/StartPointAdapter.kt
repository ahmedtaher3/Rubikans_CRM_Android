package com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.utils.CommonUtilities.checkTwoDigits
import java.security.SecureRandom
import java.util.*

class StartPointAdapter(private  val context: AppCompatActivity, chooseStartPointInterFace: ChooseStartPointInterFace, dataManager: DataManager, activityType: Int, activities: ActivityEntity, date: String, type: Int) : RecyclerView.Adapter<StartPointAdapter.MyViewHolder>() {
    private var myData: List<ListEntity>
    private var mDrawableBuilder: TextDrawable? = null
    private val chooseStartPointInterFace: ChooseStartPointInterFace
    private val dataManager: DataManager
    private val activityType: Int
    private val activities: ActivityEntity
    private val date: String
    private val type: Int
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View
        itemView = if (dataManager.isTablet) LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false) else LayoutInflater.from(parent.context).inflate(R.layout.list_item_mobile, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder.name.text = model.customerEnName
        // holder.activity.setText(model.getActivityEnName());
        holder.degree.text = model.cusClassEnName
        // holder.specialist.setText(model.getSpeciality());
        // holder.date.setText(model.getDate());
        holder.brick.text = model.brickEnName
        holder.address.text = model.address
        holder.setName(model.customerEnName, randomColor)
        holder.itemView.setOnClickListener {
            val hour = 0
            val minute = 0
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context,  OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                val _date = date + "T" + checkTwoDigits(selectedHour.toString()) + ":" + checkTwoDigits(selectedMinute.toString()) + ":00"
                _date.replace(" ".toRegex(), "")
                if (type == 1) {
                    chooseStartPointInterFace.editStartPoint(model.customerId!!, _date,
                            selectedHour.toString() + ":" + selectedMinute.toString() + " / " + model.customerEnName
                            , activityType, activities)
                } else {
                    chooseStartPointInterFace.chooseStartPoint(model.customerId!!, _date,
                            selectedHour.toString() + ":" + selectedMinute.toString() + " / " + model.customerEnName
                            , activityType, activities)
                }
            }, hour, minute, false) //Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        // Create a circular icon consisting of  a random background colour and first letter of title
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: List<ListEntity>) {
        this.myData = myData
        context.runOnUiThread {
            notifyDataSetChanged()
        }

    }

    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var name: TextView
        var specialist: TextView
        var degree: TextView

        //public TextView activity;
        var brick: TextView
        var address: TextView

        // public TextView date;
        var makeCall: Button? = null
        var list_image_name: ImageView
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
            list_image_name = v.findViewById(R.id.list_image_name)
            specialist = v.findViewById(R.id.specialist)
            //activity = v.findViewById(R.id.activity);
            brick = v.findViewById(R.id.Brick)
            //  date = v.findViewById(R.id.date);
            degree = v.findViewById(R.id.degree)
            address = v.findViewById(R.id.address)
        }
    }

    private val randomColor: Int
        private get() {
            val rgen = SecureRandom()
            return Color.HSVToColor(150, floatArrayOf(
                    rgen.nextInt(359).toFloat(), 1f, 1f))
        }

    init {
        myData = ArrayList()

        this.chooseStartPointInterFace = chooseStartPointInterFace
        this.dataManager = dataManager
        this.activityType = activityType
        this.activities = activities
        this.date = date
        this.type = type
    }
}