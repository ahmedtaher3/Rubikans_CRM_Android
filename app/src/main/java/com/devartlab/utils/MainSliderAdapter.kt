package com.devartlab.utils

import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class MainSliderAdapter(private val list: ArrayList<String>) : SliderAdapter() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindImageSlide(position: Int, viewHolder: ImageSlideViewHolder) {

        val model = list[position]
        viewHolder.bindImageSlide(model)

    }
}