package com.devartlab.ui.main.ui.devartlink.handBook;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.databinding.IndexHandBookItemBinding;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Data;

import java.util.ArrayList;
import java.util.List;

public class IndexHandBookAdapter extends RecyclerView.Adapter<IndexHandBookAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Data> dataItems;
    private Context context;

    public IndexHandBookAdapter(List<Data> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.index_hand_book_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = dataItems.get(position);
//        viewHolder.binding.tvSubjectTitle.setText(dataItem.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.binding.tvSubjectTitle.setText(
                    Html.fromHtml(dataItem.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else
            viewHolder.binding.tvSubjectTitle.setText(Html.fromHtml(dataItem.getTitle()));
        viewHolder.binding.tvSubjectTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });

        viewHolder.binding.ivHideShowTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.binding.recyclerSubs.getVisibility() == View.VISIBLE) {
                    viewHolder.binding.recyclerSubs.setVisibility(View.GONE);
                    viewHolder.binding.ivHideShowTitle.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    viewHolder.binding.recyclerSubs.setVisibility(View.VISIBLE);
                    viewHolder.binding.ivHideShowTitle.setImageResource(R.drawable.ic_arrow_drop_up);
                }

            }
        });

        if (!dataItem.getSubs().isEmpty()){
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    viewHolder.binding.recyclerSubs.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(dataItem.getSubs().size());

            // Create sub item view adapter
            IndexSubHandBookAdapter subItemAdapter = new IndexSubHandBookAdapter(dataItem.getSubs());

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

        IndexHandBookItemBinding binding;

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

    IndexHandBookAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(IndexHandBookAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void filterData(ArrayList<Data> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
