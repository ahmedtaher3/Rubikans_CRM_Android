package com.devartlab.ui.main.ui.callmanagement.report.arrange.dailogs;


import android.app.Activity;
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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private List<ProductEntity> myData;
    private Context context;
    private OpenSlides openSlides;
    private Activity activity;


    interface OpenSlides {
        void openSlides(ProductEntity entity);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsAdapter(Context context, Activity activity, OpenSlides openSlides) {
        this.myData = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        this.openSlides = openSlides;
    }

    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_small, parent, false);

        return new ProductsAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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
                openSlides.openSlides(model);
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