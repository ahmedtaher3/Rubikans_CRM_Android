package com.devartlab.ui.main.ui.devartlink.handBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.databinding.HandBookItemBinding;
import com.devartlab.databinding.SubsItemBinding;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Sub;

import java.util.ArrayList;
import java.util.List;

public class SubsAdapter extends RecyclerView.Adapter<SubsAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    List<Sub> dataItems;
    private Context context;

    public SubsAdapter(List<Sub> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subs_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Sub dataItem = dataItems.get(position);
        viewHolder.binding.tvSubjectTitle.setText(dataItem.getTitle());
        Glide.with(context)
                .load("https://devartlink.devartlab.com/assets/images/" + dataItem.getImage())
                    .fitCenter().into(viewHolder.binding.imgSubject);
        if (!dataItem.getSections().isEmpty()){
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    viewHolder.binding.recyclerSubs.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(dataItem.getSections().size());

            // Create sub item view adapter
            SectionsAdapter subItemAdapter = new SectionsAdapter(dataItem.getSections());

            viewHolder.binding.recyclerSubs.setLayoutManager(layoutManager);
            viewHolder.binding.recyclerSubs.setAdapter(subItemAdapter);
            viewHolder.binding.recyclerSubs.setRecycledViewPool(viewPool);
        }
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SubsItemBinding binding;

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
}
