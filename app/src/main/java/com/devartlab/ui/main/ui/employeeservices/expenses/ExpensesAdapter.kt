package com.devartlab.ui.main.ui.employeeservices.expenses

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
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.devartlab.R
import com.devartlab.data.shared.DataManager
import com.devartlab.model.Expenses
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.utils.ChangeDoctorData
import com.devartlab.utils.MyHeaderViewHolder
import java.util.*

class ExpensesAdapter(context: Context, dataManager: DataManager, changeDoctorData: ChangeDoctorData) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var myData: ArrayList<Expenses>
    private val context: Context
    private val dataManager: DataManager
    private val changeDoctorData: ChangeDoctorData
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private var empModel =
        FilterDataEntity(fileImage = "")

    init {
        myData = ArrayList()
        this.context = context
        this.dataManager = dataManager
        this.changeDoctorData = changeDoctorData
    }

    fun setMyData(myData: ArrayList<Expenses>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun setdrData(empModel: FilterDataEntity) {
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

            val layoutView: View = LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
            return MyHeaderViewHolder(layoutView)
        } else {

            val itemView = if (dataManager.isTablet) LayoutInflater.from(parent.context).inflate(R.layout.expenses_item, parent, false) else LayoutInflater.from(parent.context).inflate(R.layout.expenses_item_mobile, parent, false)
            return MyViewHolder(itemView)

        }
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val model = myData[position]
        if (holder is MyViewHolder) {
            val model = myData[position]
            holder.ExpensesDate.text = model.expDate.take(10)
            holder.ExpensesType.text = model.expTypeArName
            holder.ExpensesNote.text = model.notes
            holder.ExpensesQtry.text = model.qty.toString()
            holder.ExpensesValue.text = model.value.toString()
            holder.ExpensesTotal.text = model.totalValue.toString()
            holder.pendingBy.text = model.pendingUser.toString()


        Glide.with(context)
                    .load(model.expTypeIconUrl)
                    .placeholder(holder.ExpensesIcon.drawable)
                    .into(holder.ExpensesIcon)

            if (model.expStatus == 2) {
                holder.check.setImageResource(R.drawable.ic_check_circle_grey600_24dp);
                holder.check.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_light), android.graphics.PorterDuff.Mode.MULTIPLY);

            } else if (model.expStatus == 1) {
                holder.check.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp);
                holder.check.setColorFilter(ContextCompat.getColor(context, R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);

            } else {
                holder.check.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp);
                holder.check.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        } else if (holder is MyHeaderViewHolder) {

            holder.dr_name.setText(empModel.empName)
            holder.dr_title.setText(empModel.empTitle)
            holder.empImage?.setImageResource(R.drawable.user_logo)
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


    inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var ExpensesDate: TextView
        var ExpensesType: TextView
        var ExpensesNote: TextView
        var ExpensesQtry: TextView
        var ExpensesValue: TextView
        var ExpensesTotal: TextView
        var ExpensesIcon: ImageView
        var check: ImageView

        var pendingBy: TextView

        init {
            pendingBy = v.findViewById(R.id.pendingBy)
            check = v.findViewById(R.id.check)
            ExpensesIcon = v.findViewById(R.id.ExpensesIcon)
            ExpensesDate = v.findViewById(R.id.ExpensesDate)
            ExpensesType = v.findViewById(R.id.ExpensesType)
            ExpensesNote = v.findViewById(R.id.ExpensesNote)
            ExpensesQtry = v.findViewById(R.id.ExpensesQtry)
            ExpensesValue = v.findViewById(R.id.ExpensesValue)
            ExpensesTotal = v.findViewById(R.id.ExpensesTotal)
        }
    }


}