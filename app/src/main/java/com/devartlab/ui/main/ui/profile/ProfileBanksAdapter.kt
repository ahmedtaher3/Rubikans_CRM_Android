package com.devartlab.ui.main.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.DTBankAccount
import kotlin.collections.ArrayList

class ProfileBanksAdapter(private  val context: Context, private  var myData: ArrayList<DTBankAccount> ) : RecyclerView.Adapter<ProfileBanksAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view =  LayoutInflater.from(context).inflate(R.layout.profile_bank_item, parent, false)
        return ViewHolder(view)
    }

    fun setMyData(myData: ArrayList<DTBankAccount>)
    {
        this.myData = myData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        holder.bankName.setText(model.bankArName)
        holder.bankAccountNumber.setText(model.bankAccount)
       }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var bankName: TextView
        var bankAccountNumber: TextView

        init {
            bankName = view.findViewById(R.id.bankName)
            bankAccountNumber = view.findViewById(R.id.bankAccountNumber)
        }
    }


}