package com.devartlab.ui.main.ui.callmanagement.report.arrange;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devartlab.R;
import com.devartlab.data.room.arranged.ArrangedEntity;

public class ArrangedProuductsAdapter extends RecyclerView.Adapter<ArrangedProuductsAdapter.MyViewHolder> {
    private List<ArrangedEntity> myData;
    private Context context;
    private String Plan_Visit_ID;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ArrangedProuductsAdapter(Context context) {
        this.myData = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ArrangedProuductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

        return new ArrangedProuductsAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ArrangedEntity model = myData.get(position);

        holder.textView.setText(String.valueOf(model.getMassageName()));

        Glide.with(context).load(myData.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent intent = new Intent(context, ImagesSlider.class);
                intent.putExtra("PRODUCT_NAME",String.valueOf(model.getName()));
                intent.putExtra("Plan_Visit_ID",Plan_Visit_ID);
                context.startActivity(intent);*/
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<ArrangedEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.image);
            textView = v.findViewById(R.id.name);
        }
    }


}