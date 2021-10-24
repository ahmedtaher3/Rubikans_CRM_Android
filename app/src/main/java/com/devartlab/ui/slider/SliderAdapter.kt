package com.devartlab.ui.slider

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.devartlab.R
import com.devartlab.data.room.arrangedslides.ArrangedSlidesDao
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.github.chrisbanes.photoview.PhotoView
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.io.*


class SliderAdapter(var context: Context, private val dao: SlideDao, private val arrangedSlidesDao: ArrangedSlidesDao) : PagerAdapter() {
    var myData: ArrayList<SlideEntity>
    var mLayoutInflater: LayoutInflater
    override fun getCount(): Int {
        return myData.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.image_slider_item, container, false)
        val imageView: PhotoView = itemView.findViewById(R.id.imageSlider)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)


        if (myData[position].converted && !myData[position].slideBase64.isNullOrEmpty()) {
            System.out.println(" slideBase64 " + myData[position].slideBase64)



            progressBar.visibility = View.GONE
            // converted base onto Bitmap (not worked well)
            /*    val decodedString: ByteArray = android.util.Base64.decode(myData[position].slideBase64, android.util.Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                imageView.setImageBitmap(decodedByte)*/
            try {
                val f = File(myData[position].slideBase64)
                val b = BitmapFactory.decodeStream(FileInputStream(f))
                imageView.setImageBitmap(b)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }


        } else {

            System.out.println(" slideBase64 " + "else")

            Glide.with(context)
                    .asBitmap()
                    .load(myData[position].SlideUrl)
                    .apply(RequestOptions()
                            .fitCenter()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(SIZE_ORIGINAL))
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onLoadCleared(placeholder: Drawable?) {
                            progressBar.visibility = View.GONE

                        }

                        override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                            progressBar.visibility = View.GONE
                            imageView.setImageBitmap(resource)



                            Completable.fromAction {

                                val path = saveToInternalStorage(resource, position)

                                System.out.println(" ImagePath " + path)

                                if (!path.isNullOrBlank()) {

                                    val item = dao.getById(myData[position].id)

                                    System.out.println(" ImagePath " + path)


                                    if (item != null) {
                                        item.converted = true
                                        item.slideBase64 = path
                                        dao.update(item)
                                    }


                                    val arrangesItem = arrangedSlidesDao.getById(myData[position].id)
                                    if (arrangesItem != null) {
                                        arrangesItem.converted = true
                                        arrangesItem.base64 = path
                                        arrangedSlidesDao.update(arrangesItem)
                                    }

                                }


                            }.subscribeOn(Schedulers.io())
                                    .subscribe()


                        }

                    })
        }




        container.addView(itemView)
        return itemView
    }

    fun setMyData(myData: List<SlideEntity>) {
        this.myData.clear()
        this.myData.addAll(myData)
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    init {
        myData = ArrayList()
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap, position: Int): String? {

        val name = position.toString() + "_" + System.currentTimeMillis().toString()
        val cw = ContextWrapper(context)
        // path to /data/data/yourapp/app_data/imageDir
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, name)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.getAbsolutePath() + "/" + name
    }
}