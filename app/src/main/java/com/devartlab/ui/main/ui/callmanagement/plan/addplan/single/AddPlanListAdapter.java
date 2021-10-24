package com.devartlab.ui.main.ui.callmanagement.plan.addplan.single;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.data.room.list.ListEntity;
import com.devartlab.data.shared.DataManager;
import com.devartlab.model.CustomerList;

public class AddPlanListAdapter extends RecyclerView.Adapter<AddPlanListAdapter.MyViewHolder> {
    private List<ListEntity> myData;
    private Context context;
    private AddToList addToList;
    private DataManager dataManager;


   public interface AddToList
    {
        void addToList(ListEntity listEntity);
    }


    public AddPlanListAdapter(Context context ,  AddToList addToList , DataManager dataManager) {
        myData = new ArrayList<>();
        this.context = context;
        this.addToList = addToList;
        this.dataManager = dataManager;

    }

    @Override
    public AddPlanListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if (dataManager.isTablet())
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        else
          itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mobile, parent, false);

        return new AddPlanListAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ListEntity model = myData.get(position);
        holder.name.setText(model.getCustomerEnName());
        // holder.activity.setText(model.getActivityEnName());
        holder.degree.setText(model.getCusClassEnName());
          holder.specialist.setText(model.getOldSpeciality());
        // holder.date.setText(model.getDate());
        holder.brick.setText(model.getBrickEnName());
        holder.address.setText(model.getAddress());
        holder.setName(model.getCustomerEnName(), getRandomColor());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToList.addToList(model);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<ListEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        private TextDrawable mDrawableBuilder;

        // each data item is just a string in this case
        public TextView name;
        public TextView specialist;
        public TextView degree;
        //public TextView activity;
        public TextView brick;
        public TextView address;
        // public TextView date;
        public Button makeCall;
        public ImageView list_image_name;
        public View view;


        public MyViewHolder(View v) {
            super(v);
            view = v;
            name = v.findViewById(R.id.name);
            list_image_name = v.findViewById(R.id.list_image_name);
             specialist = v.findViewById(R.id.specialist);
            //activity = v.findViewById(R.id.activity);
            brick = v.findViewById(R.id.Brick);
            //  date = v.findViewById(R.id.date);
            degree = v.findViewById(R.id.degree);
            address = v.findViewById(R.id.address);

        }


        public void setName(String title, int color) {
            String letter = "A";

            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1);
            }


            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color);
            list_image_name.setImageDrawable(mDrawableBuilder);
        }





    }

    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });


    }


}