package com.devartlab.ui.main.ui.devartlink.handBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.databinding.SectionsItemBinding;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Section;
import java.util.ArrayList;
import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.ViewHolder> {
    List<Section> dataItems;
    private Context context;

    public SectionsAdapter(List<Section> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sections_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Section dataItem = dataItems.get(position);
        viewHolder.binding.tvSubjectTitle.loadDataWithBaseURL(
                null, dataItem.getTitle(), "text/html", "utf-8", null);
        Glide.with(context)
                .load("https://devartlink.4eshopping.com/assets/images/" + dataItem.getImage())
                    .fitCenter().into(viewHolder.binding.imgSubject);
        viewHolder.binding.wbSections.loadDataWithBaseURL(
                null, dataItem.getDescription(), "text/html", "utf-8", null);
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SectionsItemBinding binding;

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

    public void filterData(ArrayList<Section> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
