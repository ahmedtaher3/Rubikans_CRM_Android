package com.devartlab.ui.main.ui.callmanagement.plan.addplan.doublee;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.devartlab.R;
import com.devartlab.data.room.plan.PlanEntity;

public class SelectedPlanAdapterMeeting extends RecyclerView.Adapter<SelectedPlanAdapterMeeting.MyViewHolder> {
    private List<PlanEntity> myData;
    private Context context;
    private TextDrawable mDrawableBuilder;


    public SelectedPlanAdapterMeeting(Context context) {
        this.myData = new ArrayList<>();
        this.context = context;
    }

    public List<PlanEntity> getMyData() {
        return myData;
    }

    @Override
    public SelectedPlanAdapterMeeting.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_items, parent, false);

        return new SelectedPlanAdapterMeeting.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final PlanEntity model = myData.get(position);

        holder.selectedName.setText(model.getMeetingMembers());
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

    public void setMyData(List<PlanEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView selectedName;
        public ImageView selectedDelete;


        public MyViewHolder(View v) {
            super(v);
            selectedName = v.findViewById(R.id.selectedName);
            selectedDelete = v.findViewById(R.id.selectedDelete);


        }

       /* public void setName(String title, int color) {
            String letter = "A";

            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1);
            }


            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color);
            list_image_name.setImageDrawable(mDrawableBuilder);
        }*/
    }

    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });
    }


    public void addItem(PlanEntity planEntity) {
        this.myData.add(planEntity);
        notifyDataSetChanged();
    }

}