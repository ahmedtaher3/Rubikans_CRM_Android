package com.devartlab.ui.main.ui.callmanagement.report.arrange

import android.annotation.SuppressLint
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
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.devartlab.R
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.utils.CommonUtilities
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

class SelectedSlidesAdapter(context: Context, private val dao: SlideDao) : RecyclerView.Adapter<SelectedSlidesAdapter.MyViewHolder>() {
    private var myData: MutableList<SlideEntity>
    private val context: Context
    fun getMyData(): List<SlideEntity> {
        return myData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.selected_slides_items, parent, false)
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val model = myData[position]



        Glide.with(context)
                .asBitmap()
                .load(myData[position].SlideUrl)
                .apply(RequestOptions()
                        .fitCenter()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(SIZE_ORIGINAL))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {

                        holder.slideImage.setImageBitmap(resource)
                        // converted it to base and save it into room (not worked well)
                        /*      val outputStream = ByteArrayOutputStream()
                              resource.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

                               val base64 = android.util.Base64.encodeToString(outputStream.toByteArray(), android.util.Base64.DEFAULT)

                              Completable.fromAction {
                                  myData[position].converted = true
                                  myData[position].slideBase64 = base64

                                  dao.update(myData[position])

                               }.subscribeOn(Schedulers.io())
                                      .subscribe()*/


                        Completable.fromAction {
                            myData[position].converted = true
                            myData[position].slideBase64 = CommonUtilities.saveToInternalStorage(context, resource, position)

                            dao.update(myData[position])

                        }.subscribeOn(Schedulers.io())
                                .subscribe()


                    }

                })

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    fun setMyData(myData: MutableList<SlideEntity>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun setItem(s: SlideEntity) {
        myData.add(s)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var slideImage: ImageView

        init {
            slideImage = view.findViewById(R.id.slideImage)
        }
    }

    init {
        myData = ArrayList()
        this.context = context
    }
}