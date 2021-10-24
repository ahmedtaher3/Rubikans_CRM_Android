package com.devartlab.ui.main.ui.callmanagement.ranks.medicalriprank

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.databinding.MrRankDetailsItemBinding
import com.devartlab.model.*


class MRReportDetailsAdapter(private val context: Context, private var myData: ArrayList<MrReportDetails>) : RecyclerView.Adapter<MRReportDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                MrRankDetailsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    fun setMyData(myData: ArrayList<MrReportDetails>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<MrReportDetails> {
        return this.myData
    }

    fun addItem(model: MrReportDetails) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder._binding?.text?.text = model.text
        holder._binding?.value?.text = model.value.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: MrRankDetailsItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: MrRankDetailsItemBinding? = null

        init {
            this._binding = binding;


        }

    }


}