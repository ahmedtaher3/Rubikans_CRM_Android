package com.devartlab.ui.main.ui.employeeservices;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.devartlab.R;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<EmployeeServices> myData;
     private Context context;


    public EmployeeAdapter(Context context ) {
        this.myData = new ArrayList<>();
        this.context = context;
     }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_services_item, parent, false);

        return new EmployeeAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final EmployeeServices model = myData.get(position);

        holder.name.setText(model.getName());
        holder.desc.setText(model.getDesc());

        Glide.with(context).load(model.icon).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.link));
                context.startActivity(browserIntent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<EmployeeServices> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


        ImageView image;
        TextView name , desc;


        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            name = v.findViewById(R.id.name);
            desc = v.findViewById(R.id.desc);

        }
    }


}