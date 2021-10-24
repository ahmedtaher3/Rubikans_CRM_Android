package com.devartlab.ui.dialogs.chooseemployee;


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
import com.devartlab.data.room.filterdata.FilterDataEntity;

public class EmployeeSearchSelectedAdapter extends RecyclerView.Adapter<EmployeeSearchSelectedAdapter.MyViewHolder> {
    private List<FilterDataEntity> myData;
    private Context context;
    private OnFilterEmployeesChange onFilterEmployeesChange;


    public EmployeeSearchSelectedAdapter(Context context, OnFilterEmployeesChange onFilterEmployeesChange) {
        this.myData = new ArrayList<>();
        this.context = context;
        this.onFilterEmployeesChange = onFilterEmployeesChange;
    }

    interface OnFilterEmployeesChange {
        void onChange(List<FilterDataEntity> employeesList);

    }

    public List<FilterDataEntity> getMyData() {
        return myData;
    }

    @Override
    public EmployeeSearchSelectedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_items, parent, false);

        return new EmployeeSearchSelectedAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final FilterDataEntity model = myData.get(position);

        holder.selectedName.setText(model.getEmpName());
        holder.selectedDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myData.subList(position, myData.size()).clear();
                notifyDataSetChanged();
                onFilterEmployeesChange.onChange(myData);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<FilterDataEntity> myData) {
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

    public void addItem(FilterDataEntity planEntity) {
        if (!isFighterInFleet(planEntity)) {
            this.myData.add(planEntity);
            notifyDataSetChanged();
            onFilterEmployeesChange.onChange(myData);

        }

    }

    public boolean isFighterInFleet(FilterDataEntity planEntity) {
        for (FilterDataEntity ship : this.myData)
            if (ship.getEmpId() == planEntity.getEmpId())
                return true;
        return false;
    }

}