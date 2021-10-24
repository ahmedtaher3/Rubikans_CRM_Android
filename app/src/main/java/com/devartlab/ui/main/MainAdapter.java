package com.devartlab.ui.main;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devartlab.R;
import com.devartlab.data.room.poduct.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private List<ProductEntity> myData;
    private Context context;
    private String Plan_Visit_ID;
    private OpenMassages openMassages;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(Context context, ArrayList<ProductEntity> myData, String Plan_Visit_ID, OpenMassages openMassages) {
        this.myData = myData;
        this.context = context;
        this.openMassages = openMassages;
        this.Plan_Visit_ID = Plan_Visit_ID;
    }

    interface OpenMassages {
        void openMassages(ProductEntity productEntity);
    }

    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

        return new MainAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ProductEntity model = myData.get(position);

        holder.textView.setText(String.valueOf(model.getName()));

        Glide.with(context)
                .load(myData.get(position).getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMassages.openMassages(model);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<ProductEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public ProgressBar progressBar;

        public MyViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.image);
            textView = v.findViewById(R.id.name);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }


}