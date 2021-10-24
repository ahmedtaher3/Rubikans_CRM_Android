package com.devartlab.ui.main.ui.callmanagement.report.arrange

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import kotlin.collections.ArrayList


class ArrangeSlidesAdapter(context: Context, private val activity: Activity, private val onCustomSlideClick: OnCustomSlideClick, private val dao: SlideDao) : RecyclerView.Adapter<ArrangeSlidesAdapter.MyViewHolder>() {
    private var myData: List<SlideModel>
    private var arranged: ArrayList<SlideModel>
    private val context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_slide_item, parent, false)
        return MyViewHolder(itemView)
    }


    interface OnCustomSlideClick {
        fun setOnCustomSlideClick(url: String)
        fun setOnCustomSlideSelected(isSelected: Boolean, model: SlideModel)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myData[position]
        holder.textView.text = model.slideName.toString()


        if (model.checked) {
            holder.checkBox!!.isChecked = true
            holder.indexText.visibility = View.VISIBLE
            holder.indexText.text = model.checkedIndex.toString()
        } else {
            holder.checkBox!!.isChecked = false
            holder.indexText.visibility = View.GONE


        }



            Glide.with(context)
                    .load(myData[position].slideUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            holder.progressBar.setVisibility(View.GONE);
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            holder.progressBar.setVisibility(View.GONE);
                            return false
                        }
                    })
                    .into(holder.imageView)


        holder.imageView!!.setOnClickListener { onCustomSlideClick.setOnCustomSlideClick(myData[position].slideUrl) }
        holder.checkBox!!.setOnClickListener {

            onCustomSlideClick.setOnCustomSlideSelected(holder.checkBox!!.isChecked, model)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: List<SlideModel>) {
        this.myData = myData
        activity.runOnUiThread {
            notifyDataSetChanged()
        }

    }


    fun getMyData(): List<SlideModel> {
        return this.myData
    }

    fun getArrangedData(): ArrayList<SlideModel> {
        return this.arranged
    }

    fun setArrangedData(arranged: ArrayList<SlideModel>) {
        this.arranged = arranged
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var textView: TextView
        var imageView: ImageView
        var progressBar: ProgressBar
        var indexText: TextView
        var checkBox: CheckBox? = null

        init {
            indexText = v.findViewById(R.id.indexText)
            imageView = v.findViewById(R.id.image)
            textView = v.findViewById(R.id.name)
            progressBar = v.findViewById(R.id.progressBar)
            checkBox = v.findViewById(R.id.CheckBox)
        }
    }

    init {
        myData = ArrayList()
        arranged = ArrayList()
        this.context = context
    }
}