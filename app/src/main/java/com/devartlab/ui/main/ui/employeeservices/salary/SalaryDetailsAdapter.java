package com.devartlab.ui.main.ui.employeeservices.salary;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.data.shared.DataManager;
import com.devartlab.model.SalaryItem;

public class SalaryDetailsAdapter extends RecyclerView.Adapter<SalaryDetailsAdapter.ViewHolder> {
    private List<SalaryItem> myData;
    private Context context;
    private DataManager dataManager;
    private SalaryDetailsInterface salaryDetailsInterface;


    public SalaryDetailsAdapter(Context context, DataManager dataManager , SalaryDetailsInterface salaryDetailsInterface) {
        this.myData = new ArrayList<>();
        this.context = context;
        this.dataManager = dataManager;
        this.salaryDetailsInterface = salaryDetailsInterface;
    }

    public void setMyData(List<SalaryItem> myData) {
        this.myData.clear();
        this.myData.addAll(myData);
        notifyDataSetChanged();
     }

     interface SalaryDetailsInterface {

        void showDetails(int id , String value);
     }

    @Override
    public SalaryDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;
        if (dataManager.isTablet())
            view = LayoutInflater.from(context).inflate(R.layout.salary_item, parent, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.salary_item, parent, false);

        return new SalaryDetailsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final SalaryDetailsAdapter.ViewHolder holder, int position) {


        SalaryItem model = myData.get(position);

        holder.salaryItemName.setText(model.getDuesDeductionEnName());
        holder.salaryItemValue.setText(String.valueOf(model.getTotalValue()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salaryDetailsInterface.showDetails(model.getDuesDeductionItemId() , String.valueOf(model.getTotalValue()));
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView salaryItemName;
        public TextView salaryItemValue;
        public View view;


        public ViewHolder(View v) {
            super(v);
            view = v;
            salaryItemName = v.findViewById(R.id.salaryItemName);
            salaryItemValue = v.findViewById(R.id.salaryItemValue);

        }


    }


}