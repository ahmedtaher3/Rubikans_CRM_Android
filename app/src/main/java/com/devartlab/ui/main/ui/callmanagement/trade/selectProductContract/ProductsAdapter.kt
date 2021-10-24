package com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.data.room.contract.ContractEntity


class ProductsAdapter(private val context: Context, private var myData: ArrayList<ContractEntity>, private var onItemSelect: OnItemSelect) : RecyclerView.Adapter<com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract.ProductsAdapter.ViewHolder>() {


    interface OnItemSelect {
        fun setOnItemSelect(model: ContractEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.select_product_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<ContractEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<ContractEntity> {
        return this.myData
    }

    fun addItem(model: ContractEntity) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.name?.text = model.itemEnName
        holder.price?.text = model.price.toString()
        holder.discount?.text = model.cashDisc.toString() + "%"
        holder.priceAfterDiscount?.text = (model.price!! -((model.price!! * model.cashDisc!!) / 100)).toString()

        Glide.with(context).load(model.image).into(holder.image!!)

        holder.itemView.setOnClickListener {
            onItemSelect.setOnItemSelect(model)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var image: ImageView? = null
        var name: TextView? = null
        var price: TextView? = null
        var discount: TextView? = null
        var priceAfterDiscount: TextView? = null


        init {
            image = view.findViewById(R.id.image)
            name = view.findViewById(R.id.name)
            price = view.findViewById(R.id.price)
            discount = view.findViewById(R.id.discount)
            priceAfterDiscount = view.findViewById(R.id.priceAfterDiscount)

        }


    }


}