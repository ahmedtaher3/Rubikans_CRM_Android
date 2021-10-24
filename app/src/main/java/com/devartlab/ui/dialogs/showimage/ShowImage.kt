package com.devartlab.ui.dialogs.showimage

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.devartlab.R

class ShowImage(context: Context, private val imageUrl: String) : Dialog(context) {


    lateinit var close: ImageView
    lateinit var image: ImageView
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_image)

        close = findViewById(R.id.close)
        image = findViewById(R.id.image)
        progressBar = findViewById(R.id.progressBar)


        Glide.with(context)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        progressBar.setVisibility(View.GONE)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        progressBar.setVisibility(View.GONE)
                        return false
                    }
                })
                .into(image)


        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })


    }


}
