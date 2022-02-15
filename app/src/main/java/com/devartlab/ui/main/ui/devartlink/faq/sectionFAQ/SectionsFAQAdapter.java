package com.devartlab.ui.main.ui.devartlink.faq.sectionFAQ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.databinding.SectionFaqItemBinding;
import com.devartlab.ui.main.ui.devartlink.faq.model.section.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionsFAQAdapter extends RecyclerView.Adapter<SectionsFAQAdapter.ViewHolder> {

    List<Section> dataItems;
    private Context context;

    public SectionsFAQAdapter(List<Section> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_faq_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Section dataItem = dataItems.get(position);
        viewHolder.binding.tvTitleSection.setText(dataItem.getTitle());
        Glide.with(context)
                .load("https://devartlink.devartlab.com/assets/images/" + dataItem.getImage())
                    .fitCenter().into(viewHolder.binding.ivSection);
        viewHolder.binding.tvDec.loadDataWithBaseURL(
                null, dataItem.getDescription(), "text/html", "utf-8", null);
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SectionFaqItemBinding binding;

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
