package com.devartlab.ui.main.ui.callmanagement.plan.activities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.data.room.activity.ActivityEntity;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.MyViewHolder> {
    private List<ActivityEntity> myData;
    private Context context;
    private ChooseActivity chooseActivity;


    public interface ChooseActivity {
        void saveActivityType(ActivityEntity activities);
    }


    public ActivitiesAdapter(Context context, ChooseActivity chooseActivity) {
        myData = new ArrayList<>();
        this.context = context;
        this.chooseActivity = chooseActivity;

    }

    @Override
    public ActivitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);

        return new ActivitiesAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ActivityEntity model = myData.get(position);

        holder.name.setText(model.getActivityEnName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseActivity.saveActivityType(model);
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<ActivityEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextDrawable mDrawableBuilder;

        // each data item is just a string in this case
        public TextView name;
        public View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            name = v.findViewById(R.id.name);


        }

    }


}