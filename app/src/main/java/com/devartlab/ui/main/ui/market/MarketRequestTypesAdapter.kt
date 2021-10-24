package com.devartlab.ui.main.ui.market

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.model.Summary
import com.devartlab.utils.CommonUtilities


class MarketRequestTypesAdapter(private val context: Context, private var myData: ArrayList<Summary>, private var onRequestTypeClick: OnRequestTypeClick) : RecyclerView.Adapter<MarketRequestTypesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.market_request_type_item, parent, false)
        return ViewHolder(view)
    }

    interface OnRequestTypeClick {

        fun onRequestClick(model: Summary)
    }

    fun setMyData(myData: ArrayList<Summary>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder.requestCost?.text = model.totalCoast.toString()
        holder.requestGain?.text = model.totalGain.toString()
        holder.requestTypeName?.text = model.activity
        holder.setName(model.activity, CommonUtilities.randomColor)

        holder.itemView?.setOnClickListener {

            onRequestTypeClick.onRequestClick(model)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var mDrawableBuilder: TextDrawable? = null
        var requestCost: TextView? = null
        var requestGain: TextView? = null
        var requestImage: ImageView? = null
        var requestTypeName: TextView? = null


        init {

            requestCost = view.findViewById(R.id.requestCost)
            requestGain = view.findViewById(R.id.requestGain)
            requestImage = view.findViewById(R.id.requestImage)
            requestTypeName = view.findViewById(R.id.requestTypeName)


        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color)
            requestImage?.setImageDrawable(mDrawableBuilder)
        }
    }


}