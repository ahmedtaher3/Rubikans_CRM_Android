package com.devartlab.ui.main.ui.callmanagement.ranks.planandcover

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.databinding.PlanAndCoverItemBinding
import com.devartlab.model.PlanAndCoverCustomers
import com.devartlab.utils.CommonUtilities


class PlanAndCoverAdapter(private val context: Context, private var myData: ArrayList<PlanAndCoverCustomers>, private val onItemSelect: OnItemSelect) : RecyclerView.Adapter<PlanAndCoverAdapter.ViewHolder>() {

    private var itemsCopy = java.util.ArrayList<PlanAndCoverCustomers>()
    private var mDrawableBuilder: TextDrawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PlanAndCoverItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnItemSelect {
        fun setOnItemSelect(model: PlanAndCoverCustomers)
    }

    fun setMyData(myData: ArrayList<PlanAndCoverCustomers>) {
        this.myData = myData
        this.itemsCopy.addAll(myData)
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<PlanAndCoverCustomers> {
        return this.myData
    }

    fun addItem(model: PlanAndCoverCustomers) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.name?.text = model.customerEnName
        holder._binding?.address?.text = model.salTerriotryEnName
        holder._binding?.brick?.text = model.brickEnName


        holder._binding?.specialist?.text = model.cusTypeEnName
        holder._binding?.degree?.text = model.cusClassEnName

        holder._binding?.plan?.text = model.planCounter?.toInt().toString()
        holder._binding?.cover?.text = model.coverCounter?.toInt().toString()


        holder._binding?.totalPaid?.text = model.tOtalPaied?.toInt().toString()
        holder._binding?.totalPending?.text = model.tOtalPending?.toInt().toString()
        holder._binding?.requestCount?.text = model.requestCount?.toInt().toString()
        holder.setName(model.customerEnName, CommonUtilities.randomColor)

        /*   if (model.imagePath != null)
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
           }*/


        holder.itemView.setOnClickListener {


            onItemSelect.setOnItemSelect(model)

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: PlanAndCoverItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: PlanAndCoverItemBinding? = null

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
                if (model.customerEnName!!.contains(text, ignoreCase = true)) {
                    myData.add(model)
                }

            }

        }
        notifyDataSetChanged()
    }


}