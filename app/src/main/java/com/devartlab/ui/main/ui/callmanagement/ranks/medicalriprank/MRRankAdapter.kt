package com.devartlab.ui.main.ui.callmanagement.ranks.medicalriprank

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.MrRankItemBinding
import com.devartlab.model.MRRank


class MRRankAdapter(private val context: Context, private var myData: ArrayList<MRRank>, private val onItemSelect: OnItemSelect) : RecyclerView.Adapter<MRRankAdapter.ViewHolder>() {

    private var itemsCopy = java.util.ArrayList<MRRank>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                MrRankItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnItemSelect {
        fun setOnItemSelect(model: MRRank)
    }

    fun setMyData(myData: ArrayList<MRRank>) {
        this.myData = myData
        this.itemsCopy.addAll(myData)
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<MRRank> {
        return this.myData
    }

    fun addItem(model: MRRank) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.empName?.text = model.geteMpName()
        holder._binding?.teritory?.text = model.salTerriotryEnName


        holder._binding?.CPText?.text = model.visitRatio
        holder._binding?.TTText?.text = model.unCoverRatio
        holder._binding?.PTText?.text = model.listRatio

        holder._binding?.CPPercentage?.text = model.visitPercentage?.toInt().toString() + " %"
        holder._binding?.TTPercentage?.text = model.unCoverPercentage?.toInt().toString() + " %"
        holder._binding?.PTPercentage?.text = model.listPercentage?.toInt().toString() + " %"



        if (model.imagePath != null)
        {
            Glide.with(context)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/" + model.imagePath)
                    .placeholder(holder._binding?.empImage?.drawable)
                    .into(holder._binding?.empImage!!)
        }
        else

        {
            Glide.with(context)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(holder._binding?.empImage?.drawable)
                    .into(holder._binding?.empImage!!)
        }


        holder.itemView.setOnClickListener {
            onItemSelect.setOnItemSelect(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: MrRankItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: MrRankItemBinding? = null

        init {
            this._binding = binding;


        }

    }




    fun filter(text: String) {
        this.myData.clear()



        if (text.isEmpty()) {
            this.myData.addAll(itemsCopy)
        } else {


            for (model in itemsCopy) {
                //  || model.empArName!!.contains(text, ignoreCase = true) || model.empEnName!!.contains(text, ignoreCase = true)
                if (model.geteMpName()!!.contains(text, ignoreCase = true)) {
                    myData.add(model)
                }

            }

        }
        notifyDataSetChanged()
    }


}