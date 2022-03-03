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
import com.devartlab.ui.main.ui.devartlink.handBook.model.Data;

import java.util.ArrayList;
import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Data> dataItems;
    private Context context;

    public SubjectsAdapter(List<Data> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hand_book_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = dataItems.get(position);
        viewHolder.binding.tvSubjectTitle.loadDataWithBaseURL(
                null, dataItem.getTitle(), "text/html", "utf-8", null);

        if (!dataItem.getSubs().isEmpty()){
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    viewHolder.binding.recyclerSubs.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(dataItem.getSubs().size());

            // Create sub item view adapter
            SubsAdapter subItemAdapter = new SubsAdapter(dataItem.getSubs());

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

        HandBookItemBinding binding;

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

    public void filterData(ArrayList<Data> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
