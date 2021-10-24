package com.devartlab.ui.main.ui.callmanagement.list.addnewcus;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.model.CustomerList;

public class SelectedNewListAdapter extends RecyclerView.Adapter<SelectedNewListAdapter.MyViewHolder> {
    private List<CustomerList> myData;
    private Context context;


    public SelectedNewListAdapter(Context context) {
        this.myData = new ArrayList<>();
        this.context = context;
    }

    public List<CustomerList> getMyData() {
        return myData;
    }

    @Override
    public SelectedNewListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_items, parent, false);

        return new SelectedNewListAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final CustomerList model = myData.get(position);

        holder.selectedName.setText(model.getCustomerEnName());
        holder.selectedDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myData.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<CustomerList> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView selectedName;
        public ImageView selectedDelete;

        public MyViewHolder(View v) {
            super(v);
            selectedName = v.findViewById(R.id.selectedName);
            selectedDelete = v.findViewById(R.id.selectedDelete);


        }

    }

    public void addItem(CustomerList list) {

        if (!isFighterInFleet(list))
        {
            this.myData.add(list);
            notifyDataSetChanged();
        }

    }

    public boolean isFighterInFleet(CustomerList list) {
        for(CustomerList ship : this.myData)
       if(ship.getCustomerId()== list.getCustomerId())
                return true;
        return false;
    }

}