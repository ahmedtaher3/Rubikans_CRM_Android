package com.devartlab.ui.main.ui.callmanagement.plan.addplan.doublee

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.data.room.filterdata.FilterDataEntity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.devartlab.R
import android.widget.TextView
import java.util.ArrayList

class DoubleSearchAdapter(context: Context, empInterface: EmpInterface) : RecyclerView.Adapter<DoubleSearchAdapter.MyViewHolder>() {


    private val context: Context
    private val empInterface: EmpInterface
    private val myData: MutableList<FilterDataEntity>

    fun getMyData(): List<FilterDataEntity> {
        return myData
    }

    interface EmpInterface {
        fun getEmpData(model: FilterDataEntity?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder.name.text = model.empName
        holder.jop.text = model.empTitle
        holder.view.setOnClickListener { empInterface.getEmpData(model) }


        holder.empImage?.setImageResource(R.drawable.user_logo)
        if (!model.fileImage.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(model.fileImage, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            holder.empImage?.setImageBitmap(decodedByte)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun clearData() {
        myData.clear()
        notifyDataSetChanged()
    }

    fun setMyData(myData: MutableList<FilterDataEntity>) {
        this.myData.clear()
        this.myData.addAll(myData)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var jop: TextView
        var empImage: ImageView

        init {
            name = view.findViewById(R.id.name)
            jop = view.findViewById(R.id.jop)
            empImage = view.findViewById(R.id.empImage)
        }
    }


    init {
        myData = ArrayList()
        this.context = context
        this.empInterface = empInterface
    }
}