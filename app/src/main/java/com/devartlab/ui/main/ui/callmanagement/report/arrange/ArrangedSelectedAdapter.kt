package com.devartlab.ui.main.ui.callmanagement.report.arrange

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.devartlab.R
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.model.SlideModel
import com.devartlab.utils.CommonUtilities
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

class ArrangedSelectedAdapter(context: Context, private val onSelectedDelete: OnSelectedDelete, private val dao: SlideDao) : RecyclerView.Adapter<ArrangedSelectedAdapter.MyViewHolder>() {
    private var myData: List<SlideModel>
    private val context: Context
    private val Plan_Visit_ID: String? = null


    interface OnSelectedDelete {
        fun setOnSelectedDelete(model: SlideModel?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item_selected, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        Glide.get(context).setMemoryCategory(MemoryCategory.HIGH)
        Glide.with(context).load(myData[position].slideUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(holder.imageView)





        Glide.with(context)
                .load(myData[position].slideUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(holder.imageView)





        holder.itemView.setOnClickListener { onSelectedDelete.setOnSelectedDelete(model) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: List<SlideModel>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<SlideModel> {
        return myData as ArrayList<SlideModel>
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var delete: ImageView
        var imageView: ImageView

        init {
            imageView = v.findViewById(R.id.image)
            delete = v.findViewById(R.id.delete)
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    init {
        myData = ArrayList()
        this.context = context
    }
}