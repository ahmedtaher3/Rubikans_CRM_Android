package com.devartlab.ui.dialogs.massages;

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

import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.data.room.massages.MassageEntity;

public class MassagesAdapter extends RecyclerView.Adapter<MassagesAdapter.MyViewHolder> {
    private List<MassageEntity> myData;
    private Context context;
    private OnMassageSelect massagesInterface;
    private Activity activity;
    // Provide a suitable constructor (depends on the kind of dataset)
    public MassagesAdapter(Context context    , Activity activity  , OnMassageSelect massagesInterface) {
        this.myData = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        this.massagesInterface = massagesInterface;

    }

    @Override
    public MassagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

        return new MassagesAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MassagesAdapter.MyViewHolder holder, int position) {

        final MassageEntity model = myData.get(position);

        holder.name.setText(model.getMessageDescription());



        Glide.with(context)
                .load(model.getMessageLogoUrl())
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
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                massagesInterface.setOnMassageSelect(model);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<MassageEntity> myData) {
        this.myData = myData;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();

            }
        });
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        public TextView name;
        public ProgressBar progressBar;


        public MyViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            name = v.findViewById(R.id.name);
            progressBar = v.findViewById(R.id.progressBar);

        }
    }


}