package com.devartlab.ui.main.ui.callmanagement.plan.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import java.util.*

class InvoiceTypsAdapter(context: Context, chooseActivity: ChooseInvoiceType) : RecyclerView.Adapter<InvoiceTypsAdapter.MyViewHolder>() {
    private var myData: List<PurchaseTypeEntity>
    private val context: Context
    private val chooseActivity: ChooseInvoiceType

    interface ChooseInvoiceType {
        fun setChooseInvoiceType(activities: PurchaseTypeEntity?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder.name.text = model.invoiceTypeDescription
        holder.view.setOnClickListener { chooseActivity.setChooseInvoiceType(model) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: List<PurchaseTypeEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private val mDrawableBuilder: TextDrawable? = null

        // each data item is just a string in this case
        var name: TextView

        init {
            name = view.findViewById(R.id.name)
        }
    }

    init {
        myData = ArrayList()
        this.context = context
        this.chooseActivity = chooseActivity
    }
}