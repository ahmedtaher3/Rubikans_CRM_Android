package com.devartlab.ui.main.ui.callmanagement.report.arrange;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devartlab.R;
import com.devartlab.data.room.slides.SlideEntity;

public class SlidesAdapter extends RecyclerView.Adapter<SlidesAdapter.MyViewHolder> {
    private List<SlideEntity> myData;
    private Context context;
    private    SetSlides setSlides ;


    public SlidesAdapter(Context context , SetSlides setSlides ) {
        this.myData = new ArrayList<>();
        this.context = context;
        this.setSlides = setSlides;

    }

    interface SetSlides
    {
        void addSlide(SlideEntity entity);
    }

    public List<SlideEntity> getMyData() {
        return myData;
    }


    @Override
    public SlidesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slides_items, parent, false);

        return new SlidesAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final SlideEntity model = myData.get(position);


        Glide.with(context).load(model.SlideUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.slideImage);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSlides.addSlide(model);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<SlideEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }

    public void setItem(SlideEntity s) {
        myData.add(s);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView slideImage;
        public View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            slideImage = v.findViewById(R.id.slideImage);
        }

    }
}