package com.devartlab.utils;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {


    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1606387234/bg-100_mtlkgl.jpg");
                break;
            case 1:
                viewHolder.bindImageSlide("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1606387235/ferrobella-calcibella-vitabella_dlyja5.jpg");
                break;
            case 2:
                viewHolder.bindImageSlide("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1606387231/bg-88_it3iiw.jpg");
                break;
            case 3:
                viewHolder.bindImageSlide("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1606387226/bg-102_gyx2yc.jpg");
                break;
        }
    }
}
