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
import com.devartlab.databinding.IndexSubHandBookItemBinding;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Data;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Sub;

import java.util.ArrayList;
import java.util.List;

public class IndexSubHandBookAdapter extends RecyclerView.Adapter<IndexSubHandBookAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Sub> dataItems;
    private Context context;

    public IndexSubHandBookAdapter(List<Sub> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.index_sub_hand_book_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Sub dataItem = dataItems.get(position);
//        viewHolder.binding.tvSubjectTitle.setText(dataItem.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.binding.tvSubjectTitle.setText(
                    Html.fromHtml(dataItem.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else
            viewHolder.binding.tvSubjectTitle.setText(Html.fromHtml(dataItem.getTitle()));

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


        if (!dataItem.getSections().isEmpty()){
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    viewHolder.binding.recyclerSubs.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(dataItem.getSections().size());

            // Create sub item view adapter
            IndexSectionHandBookAdapter subItemAdapter = new IndexSectionHandBookAdapter(dataItem.getSections());
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

        IndexSubHandBookItemBinding binding;

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

    IndexSubHandBookAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(IndexSubHandBookAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void filterData(ArrayList<Sub> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
