package com.devartlab.ui.main.ui.eShopping.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.a4eshopping.main.model.CardModel
import com.devartlab.databinding.Home4eshoppingItemBinding

class MenuListAdapter(context: Context, private val myData: List<CardModel>, private val onHomeItemClick: OnHomeItemClick) : RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            Home4eshoppingItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )
    }

    interface OnHomeItemClick
    {
        fun setOnHomeItemClick(model: CardModel)
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]

        holder._binding?.name?.text = model.name
        holder._binding?.image?.setBackgroundResource(model.image);

        holder.itemView.setOnClickListener { onHomeItemClick.setOnHomeItemClick(model) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class MyViewHolder(var binding: Home4eshoppingItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: Home4eshoppingItemBinding? = null

        init {
            this._binding = binding;


        }

    }


}