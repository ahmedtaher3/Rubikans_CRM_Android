package com.devartlab.ui.dialogs.addplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.data.room.plan.PlanEntity;

public class AddPlanAdapter extends RecyclerView.Adapter<AddPlanAdapter.MyViewHolder> {
    private List<PlanEntity> myData;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AddPlanAdapter(Context context, ArrayList<PlanEntity> myData ) {
        this.myData = myData;
        this.context = context;

    }

    @Override
    public AddPlanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_list_item, parent, false);

        return new AddPlanAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AddPlanAdapter.MyViewHolder holder, int position) {

        final PlanEntity model = myData.get(position);



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<PlanEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CheckBox checkbox;


        public MyViewHolder(View v) {
            super(v);
            checkbox = v.findViewById(R.id.checkbox);

        }
    }


}