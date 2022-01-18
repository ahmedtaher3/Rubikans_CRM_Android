package com.devartlab.ui.main.ui.eShopping.PharmacyBinding.allComments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.a4eshopping.PharmacyBinding.allComments.model.Data;
import com.devartlab.databinding.AllCommentsItemBinding;

import java.util.List;

public class AllCommentsAdapter extends RecyclerView.Adapter<AllCommentsAdapter.ViewHolder> {

    List<Data> dataItems;
    private Context context;

    public AllCommentsAdapter(List<Data> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_comments_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = dataItems.get(position);

        viewHolder.binding.tvNoPharmacy.setText(dataItem.getCreated_at());
        viewHolder.binding.tvNamePharmacy.setText(dataItem.getName());
        viewHolder.binding.tvStartDate.setText(dataItem.getComment());
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AllCommentsItemBinding binding;

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
