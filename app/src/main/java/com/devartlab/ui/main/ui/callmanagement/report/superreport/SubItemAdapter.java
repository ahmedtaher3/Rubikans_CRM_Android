package com.devartlab.ui.main.ui.callmanagement.report.superreport;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.devartlab.R;
import com.devartlab.model.EMPloyeeAppraisalQ;

;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private ArrayList<EMPloyeeAppraisalQ> subItemList;

    SubItemAdapter(ArrayList<EMPloyeeAppraisalQ> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, @SuppressLint("RecyclerView") int i) {
        EMPloyeeAppraisalQ subItem = subItemList.get(i);
        subItemViewHolder.Category.setText(subItem.getCategory());
        subItemViewHolder.Item.setText(subItem.getItem());
        subItemViewHolder.Rate.setRating(subItem.getRate());

        subItemViewHolder.Rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                if (b) {
                    subItemList.get(i).setRate((int) (Math.round(v)));
                    System.out.println("Position = " + String.valueOf(i));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView Category;
        TextView Item;
        RatingBar Rate;

        SubItemViewHolder(View itemView) {
            super(itemView);
            Category = itemView.findViewById(R.id.Category);
            Item = itemView.findViewById(R.id.Item);
            Rate = itemView.findViewById(R.id.Rate);
        }
    }
}
