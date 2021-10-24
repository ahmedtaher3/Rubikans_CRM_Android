package com.devartlab.ui.main.ui.callmanagement.trade.selectProducts

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
import com.devartlab.R
import com.devartlab.data.room.contract.ContractEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class GeneralConfirmProductsAdapter(private val context: Context, private var myData: ArrayList<ContractEntity> , private val offline :Boolean?) : RecyclerView.Adapter<GeneralConfirmProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.order_product_item, parent, false)
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
        holder.count?.setText(model.count.toString())


        holder.remove?.setOnClickListener(View.OnClickListener {

            myData.removeAt(position)
            notifyDataSetChanged()

        })



        holder.increaseBtn.setOnClickListener(View.OnClickListener {

            myData[position].count = (holder.count?.text.toString().toInt() + 1)
            notifyDataSetChanged()

        })


        holder.decreaseBtn.setOnClickListener(View.OnClickListener {

            if (holder.count?.text.toString() != "1")
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
        var remove: ImageView? = null
        var name: TextView? = null
        var price: TextView? = null
        var count: EditText? = null
        val decreaseBtn: FloatingActionButton
        val increaseBtn: FloatingActionButton

        init {

            name = view.findViewById(R.id.name)
            count = view.findViewById(R.id.count)
            decreaseBtn = view.findViewById(R.id.decreaseBtn)
            increaseBtn = view.findViewById(R.id.increaseBtn)
            remove = view.findViewById(R.id.remove)


            if (!offline!!)
            {
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
                        } catch (e: java.lang.Exception) {

                        }


                    }
                })
            }


        }


    }


}