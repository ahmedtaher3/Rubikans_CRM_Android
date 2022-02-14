package com.devartlab.ui.main.ui.devartlink.faq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.databinding.FaqItemBinding;
import com.devartlab.ui.main.ui.devartlink.faq.model.FAQResponseItem;

import java.util.ArrayList;
import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {

    List<FAQResponseItem> dataItems;
    private Context context;

    public FAQAdapter(List<FAQResponseItem> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final FAQResponseItem dataItem = dataItems.get(position);
        viewHolder.binding.tvQuestion.setText(dataItem.getTitle());
        Glide.with(context)
                .load("https://devartlink.devartlab.com/assets/images/" + dataItem.getImage())
                    .centerCrop().into(viewHolder.binding.ivDown);
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position, dataItem);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FaqItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }


        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind();
            }
        }

    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, FAQResponseItem dataItem);
    }
    public void filterData(ArrayList<FAQResponseItem> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
