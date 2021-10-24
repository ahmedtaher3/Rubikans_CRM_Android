package com.devartlab.ui.main.ui.callmanagement.plan.addplan.meeting;


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
import com.devartlab.model.CustomerList;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> {
    private List<CustomerList> myData;
    private Context context;
    private TextDrawable mDrawableBuilder;
    private AddMeetingActivityInterface addMeetingActivity;


    public MeetingAdapter(Context context ,  AddMeetingActivityInterface addMeetingActivity) {
        myData = new ArrayList<>();
        this.context = context;
        this.addMeetingActivity = addMeetingActivity;
    }

    public interface AddMeetingActivityInterface
    {
        void addActivity(CustomerList customerList);
    }

    @Override
    public MeetingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MeetingAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final CustomerList model = myData.get(position);
        holder.name.setText(model.getCustomerEnName());
        // holder.activity.setText(model.getActivityEnName());
        holder.degree.setText(model.getCusClassEnName());
        // holder.specialist.setText(model.getSpeciality());
        // holder.date.setText(model.getDate());
        holder.brick.setText(model.getBrickEnName());
        holder.address.setText(model.getAddress());
        holder.setName(model.getCustomerEnName(),getRandomColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMeetingActivity.addActivity(model);
            }
        });


        // Create a circular icon consisting of  a random background colour and first letter of title

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

        public MyViewHolder(View v) {
            super(v);
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