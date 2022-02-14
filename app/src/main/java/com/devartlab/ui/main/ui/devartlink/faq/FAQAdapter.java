package com.devartlab.ui.main.ui.devartlink.faq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.databinding.FaqItemBinding;
import com.devartlab.databinding.ListVideoItemBinding;
import com.devartlab.ui.main.ui.devartlink.faq.model.Data;

import java.util.ArrayList;
import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {

    List<Data> dataItems;
    private Context context;

    public FAQAdapter(List<Data> dataItems) {
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
        final Data dataItem = dataItems.get(position);
        viewHolder.binding.tvAnswer.loadDataWithBaseURL(
                null,dataItem.getA(), "text/html", "utf-8", null);
//        viewHolder.binding.tvQuestion.loadDataWithBaseURL(
//                null,dataItem.getQ(), "text/html", "utf-8", null);
        viewHolder.binding.ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.binding.tvAnswer.getVisibility() == View.VISIBLE) {
                    viewHolder.binding.tvAnswer.setVisibility(View.GONE);
                    viewHolder.binding.ivDown.setImageResource(R.drawable.ic_down);
                } else {
                    viewHolder.binding.tvAnswer.setVisibility(View.VISIBLE);
                    viewHolder.binding.ivDown.setImageResource(R.drawable.ic_up);
                }
            }
        });
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
        void onItemClick(int pos, Data dataItem);
    }
    public void filterData(ArrayList<Data> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
