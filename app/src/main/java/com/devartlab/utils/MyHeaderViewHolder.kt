package com.devartlab.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R

class MyHeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var empImage: ImageView
    var dr_name: TextView
    var dr_title: TextView

    init {

        empImage = v.findViewById(R.id.empImage)
        dr_name = v.findViewById(R.id.dr_name)
        dr_title = v.findViewById(R.id.dr_title)

    }
}