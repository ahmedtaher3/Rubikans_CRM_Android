package com.devartlab.ui.main.ui.callmanagement.ranks.doublevisit

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.databinding.DoubleVisitDetailsItemBinding
import com.devartlab.model.DoubleVisitReportDetails
import com.devartlab.utils.CommonUtilities


class DVDetailsAdapter(private val context: Context, private var myData: ArrayList<DoubleVisitReportDetails>, private val onItemSelect: OnItemSelect) : RecyclerView.Adapter<DVDetailsAdapter.ViewHolder>() {

    private var itemsCopy = java.util.ArrayList<DoubleVisitReportDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DoubleVisitDetailsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnItemSelect {
        fun setOnItemSelect(model: DoubleVisitReportDetails)
    }

    fun setMyData(myData: ArrayList<DoubleVisitReportDetails>) {
        this.myData = myData
        this.itemsCopy.addAll(myData)
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<DoubleVisitReportDetails> {
        return this.myData
    }

    fun addItem(model: DoubleVisitReportDetails) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.name?.text = model.customerEnName
        holder._binding?.brick?.text = model.brickEnName
        holder._binding?.specialist?.text = model.cusTypeEnName
        holder._binding?.cusClass?.text = model.cusClassEnName

        holder.setName(model.customerEnName, CommonUtilities.randomColor)





        holder.itemView.setOnClickListener {
            onItemSelect.setOnItemSelect(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: DoubleVisitDetailsItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        private var mDrawableBuilder: TextDrawable? = null

        var _binding: DoubleVisitDetailsItemBinding? = null

        init {
            this._binding = binding;


        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color)
            _binding?.image?.setImageDrawable(mDrawableBuilder)
        }

    }


    fun filter(text: String) {
        this.myData.clear()



        if (text.isEmpty()) {
            this.myData.addAll(itemsCopy)
        } else {


            for (model in itemsCopy) {
                //  || model.empArName!!.contains(text, ignoreCase = true) || model.empEnName!!.contains(text, ignoreCase = true)
                if (model.employeeEMpName!!.contains(text, ignoreCase = true)) {
                    myData.add(model)
                }

            }

        }
        notifyDataSetChanged()
    }

}