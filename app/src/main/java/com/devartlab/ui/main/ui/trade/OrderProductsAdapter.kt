package com.devartlab.ui.main.ui.trade

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.devartlab.R
import com.devartlab.model.ProductTrade
import com.google.android.material.floatingactionbutton.FloatingActionButton


class OrderProductsAdapter(private val context: Context, private var myData: ArrayList<ProductTrade>) : RecyclerView.Adapter<OrderProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.order_product_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<ProductTrade>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<ProductTrade> {
        return this.myData
    }

    fun addItem(model: ProductTrade) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.name?.text = model.name
        holder.price?.text = model.price.toString()
        holder.count?.setText(model.count.toString())


        Glide.with(context).load(model.image).into(holder.image!!)





        holder.increaseBtn.setOnClickListener(View.OnClickListener {

            myData[position].count = (holder.count?.text.toString().toInt() + 1)
            notifyDataSetChanged()


        })


        holder.decreaseBtn.setOnClickListener(View.OnClickListener {
            if (holder.count?.text.toString() != "0")
                myData[position].count = (holder.count?.text.toString().toInt() - 1)
            notifyDataSetChanged()

        })


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
        var count: EditText? = null
        val decreaseBtn: FloatingActionButton
        val increaseBtn: FloatingActionButton

        init {
            image = view.findViewById(R.id.image)
            name = view.findViewById(R.id.name)
            count = view.findViewById(R.id.count)
            decreaseBtn = view.findViewById(R.id.decreaseBtn)
            increaseBtn = view.findViewById(R.id.increaseBtn)


         count?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    try {

                    } catch (e: Exception) {
                    }
                }

                override fun afterTextChanged(editable: Editable) {

                    try {
                        myData[adapterPosition].count = editable.toString().toInt()
                    }
                    catch (e:java.lang.Exception)
                    {

                    }



                }
            })


        }


    }


}