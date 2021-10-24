package com.devartlab.ui.dialogs.addplan.fragments.types

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.devartlab.R
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import java.util.*

class AddPlanTypesAdapter(
    context: Context,
    addPlanGetList: AddPlanGetList
) : RecyclerView.Adapter<AddPlanTypesAdapter.MyViewHolder>() {
    private var myData: List<SpecialtyParentEntity>
    private val context: Context
    private val addPlanGetList: AddPlanGetList

    interface AddPlanGetList {
        fun get_list()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.type_item, parent, false)
        return MyViewHolder(
            itemView
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val model = myData[position]
        holder.typeName.text = model.listType
        if (model.listType == "CLinic") {
            Glide.get(context).setMemoryCategory(MemoryCategory.HIGH)
            Glide.with(context)
                .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1586768097/devart/SmartHead-physician-icon.png")
                .into(holder.typeImage)
        } else if (model.listType == "Hospital") {
            Glide.get(context).setMemoryCategory(MemoryCategory.HIGH)
            Glide.with(context)
                .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1586768098/devart/Pharmacy-icon.png")
                .into(holder.typeImage)
        } else if (model.listType == "Pharmacy") {
            Glide.get(context).setMemoryCategory(MemoryCategory.HIGH)
            Glide.with(context)
                .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1586768097/devart/index.png")
                .into(holder.typeImage)
        } else {
            Glide.get(context).setMemoryCategory(MemoryCategory.HIGH)
            Glide.with(context)
                .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1586768097/devart/SocialMedia.png")
                .into(holder.typeImage)
        }
        holder.itemView.setOnClickListener { addPlanGetList.get_list() }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: List<SpecialtyParentEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var typeImage: ImageView
        var typeName: TextView

        init {
            typeImage = v.findViewById(R.id.typeImage)
            typeName = v.findViewById(R.id.typeName)
        }
    }

    init {
        myData = ArrayList()
        this.context = context
        this.addPlanGetList = addPlanGetList
    }
}