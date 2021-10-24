package com.devartlab.ui.main.ui.callmanagement.report.arrange

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.devartlab.R
import com.devartlab.data.room.arranged.ArrangedEntity
import com.devartlab.data.room.poduct.ProductEntity
import java.lang.Exception
import java.util.*


class ArrangeAdapter(context: Context, private val onCustomMassageClick: OnCustomMassageClick) : RecyclerView.Adapter<ArrangeAdapter.MyViewHolder>() {
    private var myData: List<ArrangedEntity>
    private val context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_massage_item, parent, false)
        return MyViewHolder(itemView)
    }


    interface OnCustomMassageClick {
        fun setOnCustomMassageDelete(model: ArrangedEntity)
        fun setOnCustomMassageClick(model: ArrangedEntity)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]

        try {
            holder.textView.text = model.massageName.toString()


            Glide.with(context)
                    .load(myData[position].image)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            holder.progressBar.setVisibility(View.GONE)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            holder.progressBar.setVisibility(View.GONE)
                            return false
                        }
                    })
                    .into(holder.imageView)


        } catch (e: Exception) {

        }

        holder.delete!!.setOnClickListener { onCustomMassageClick.setOnCustomMassageDelete(model) }
        holder.itemView!!.setOnClickListener {
            onCustomMassageClick.setOnCustomMassageClick(model)


        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: List<ArrangedEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var textView: TextView
        var imageView: ImageView
        var progressBar: ProgressBar
        var delete: RelativeLayout? = null

        init {
            imageView = v.findViewById(R.id.image)
            textView = v.findViewById(R.id.name)
            progressBar = v.findViewById(R.id.progressBar)
            delete = v.findViewById(R.id.delete)
        }
    }

    init {
        myData = ArrayList()
        this.context = context
    }
}