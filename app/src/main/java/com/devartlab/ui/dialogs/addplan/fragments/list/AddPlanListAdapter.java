package com.devartlab.ui.dialogs.addplan.fragments.list;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.model.AddPlanList;

public class AddPlanListAdapter extends RecyclerView.Adapter<AddPlanListAdapter.MyViewHolder> {
    private List<AddPlanList> myData;
    private List<AddPlanList> myDataChecked ;
    private Context context;


    public AddPlanListAdapter(Context context) {
        myData = new ArrayList<>();
        this.context = context;
        this.myDataChecked = new ArrayList<>();
    }

    @Override
    public AddPlanListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_plan_list_item, parent, false);

        return new AddPlanListAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final AddPlanList model = myData.get(position);
        holder.name.setText(model.getCustomerList().getCustomerEnName());
        // holder.activity.setText(model.getActivityEnName());
        holder.degree.setText(model.getCustomerList().getCusClassEnName());
        // holder.specialist.setText(model.getSpeciality());
        // holder.date.setText(model.getDate());
        holder.brick.setText(model.getCustomerList().getBrickEnName());
        holder.address.setText(model.getCustomerList().getAddress());
        holder.setName(model.getCustomerList().getCustomerEnName(), getRandomColor());




        holder.addPlanCheckBox.setChecked(model.isChecked());
        holder.addPlanCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( holder.addPlanCheckBox.isChecked()) {
                    model.setChecked(true);
                    myDataChecked.add(model);
                } else if (! holder.addPlanCheckBox.isChecked()) {
                    model.setChecked(false);
                    myDataChecked.remove(model);

                }
            }
        });
        // Create a circular icon consisting of  a random background colour and first letter of title

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<AddPlanList> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public List<AddPlanList> getMyDataChecked() {
        return myDataChecked;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        public CheckBox addPlanCheckBox;

        ItemClickListener itemClickListener;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            list_image_name = v.findViewById(R.id.list_image_name);
            addPlanCheckBox = v.findViewById(R.id.addPlanCheckBox);
            specialist = v.findViewById(R.id.specialist);
            //activity = v.findViewById(R.id.activity);
            brick = v.findViewById(R.id.Brick);
            //  date = v.findViewById(R.id.date);
            degree = v.findViewById(R.id.degree);
            address = v.findViewById(R.id.address);

        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
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


        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }


        interface ItemClickListener {

            void onItemClick(View v, int pos);
        }
    }

    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });


    }


}