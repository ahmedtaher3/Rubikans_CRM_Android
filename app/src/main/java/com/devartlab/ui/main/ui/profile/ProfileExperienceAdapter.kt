package com.devartlab.ui.main.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.DTExperience


class ProfileExperienceAdapter(private val context: Context, private var myData: ArrayList<DTExperience>) : RecyclerView.Adapter<ProfileExperienceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.profile_experience_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<DTExperience>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<DTExperience> {
        return this.myData
    }

    fun addItem(model: DTExperience) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]
        holder.experienceCompany?.text = model.company
        holder.experienceDescription?.text = model.descriptions
        if (!model.fromDate.isNullOrEmpty())
            holder.experienceFrom?.text = model.fromDate?.take(10)
        holder.experienceJob?.text = model.jobArName
        if (!model.toDate.isNullOrEmpty())
            holder.experienceTo?.text = model.toDate?.take(10)


        holder.experienceDelete?.setOnClickListener {


            myData.removeAt(position)
            notifyDataSetChanged()

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var experienceCompany: TextView? = null
        var experienceDelete: ImageView? = null
        var experienceDescription: TextView? = null
        var experienceFrom: TextView? = null
        var experienceJob: TextView? = null
        var experienceTo: TextView? = null

        init {

            experienceCompany = view.findViewById(R.id.experienceCompany)
            experienceDelete = view.findViewById(R.id.experienceDelete)
            experienceDescription = view.findViewById(R.id.experienceDescription)
            experienceFrom = view.findViewById(R.id.experienceFrom)
            experienceJob = view.findViewById(R.id.experienceJob)
            experienceTo = view.findViewById(R.id.experienceTo)


        }
    }


}