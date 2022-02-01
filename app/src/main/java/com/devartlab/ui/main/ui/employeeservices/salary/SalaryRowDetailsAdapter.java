package com.devartlab.ui.main.ui.employeeservices.salary;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.model.SalaryItemDetails;

import java.util.ArrayList;
import java.util.List;

public class SalaryRowDetailsAdapter extends RecyclerView.Adapter<SalaryRowDetailsAdapter.ViewHolder> {
    private List<SalaryItemDetails> myData;
    private Context context;



    public SalaryRowDetailsAdapter(Context context ) {
        this.myData = new ArrayList<>();
        this.context = context;

    }

    public void setMyData(List<SalaryItemDetails> myData) {
        this.myData.clear();
        this.myData.addAll(myData);
        notifyDataSetChanged();
    }


    @Override
    public SalaryRowDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.salary_row_item, parent, false);

        return new SalaryRowDetailsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final SalaryRowDetailsAdapter.ViewHolder holder, int position) {


        SalaryItemDetails model = myData.get(position);

        holder.desc.setText(model.getDescription());
        holder.descValue.setText(model.getValueDescription());
        holder.notes.setText(model.getNotes());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView desc;
        public TextView descValue;
        public TextView notes;


        public ViewHolder(View v) {
            super(v);
            desc = v.findViewById(R.id.desc);
            descValue = v.findViewById(R.id.descValue);
            notes = v.findViewById(R.id.notes);

        }


    }


}