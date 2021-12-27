package com.devartlab.ui.dialogs.chooseemployee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.room.filterdata.FilterDataEntity
import java.util.*

class EmployeeSearchAdapter constructor(context: Context, chooseEmployeeInterFace: ChooseEmployeeInterFace, onEmployeeFilterClick: OnEmployeeFilterClick) : RecyclerView.Adapter<EmployeeSearchAdapter.MyViewHolder>() {
    internal var myData: MutableList<FilterDataEntity>
    private val context: Context
    private val chooseEmployeeInterFace: ChooseEmployeeInterFace
    private val onEmployeeFilterClick: OnEmployeeFilterClick

    interface OnEmployeeFilterClick {
        fun setOnEmployeeFilterClick(model: FilterDataEntity?)
    }

    fun getMyData(): List<FilterDataEntity> {
        return myData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder.name.text = model.empName!!
        holder.jop.text = model.empTitle!!
        holder.view.setOnClickListener {


            chooseEmployeeInterFace.chooseEmployee( myData[position])



        }
        holder.filterEmployeesIcon.setOnClickListener { onEmployeeFilterClick.setOnEmployeeFilterClick(model) }



        if (model.fileImage != null)
        {
            Glide.with(context)
                    .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(holder.empImage?.drawable)
                    .into(holder.empImage!!)
        }
        else

        {
            Glide.with(context)
                    .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(holder.empImage?.drawable)
                    .into(holder.empImage!!)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun clearData() {

        notifyDataSetChanged()
    }

    fun setMyData(myData: MutableList<FilterDataEntity>) {
        this.myData.clear()
        this.myData.addAll(myData)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var name: TextView
        var jop: TextView
        var empImage: ImageView
        var filterEmployeesIcon: RelativeLayout
        private var mDrawableBuilder: TextDrawable? = null

        init {
            name = view.findViewById(R.id.name)
            jop = view.findViewById(R.id.jop)
            empImage = view.findViewById(R.id.empImage)
            filterEmployeesIcon = view.findViewById(R.id.filterEmployeesIcon)
        }
    }



    init {
        myData = ArrayList()
        this.context = context
        this.chooseEmployeeInterFace = chooseEmployeeInterFace
        this.onEmployeeFilterClick = onEmployeeFilterClick
    }
}