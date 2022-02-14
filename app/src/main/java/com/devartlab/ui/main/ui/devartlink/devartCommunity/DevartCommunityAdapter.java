package com.devartlab.ui.main.ui.devartlink.devartCommunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.databinding.ListVideoItemBinding;
import com.devartlab.ui.main.ui.devartlink.devartCommunity.model.Youtube;

import java.util.ArrayList;
import java.util.List;

public class DevartCommunityAdapter extends RecyclerView.Adapter<DevartCommunityAdapter.ViewHolder> {

    List<Youtube> dataItems;
    private Context context;

    public DevartCommunityAdapter(List<Youtube> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_video_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Youtube dataItem = dataItems.get(position);
        viewHolder.binding.nameChannel.setText(dataItem.getSub_title());
        viewHolder.binding.numberVideo.setText(String.valueOf(position+1));
        viewHolder.binding.name.setText(dataItem.getTitle());
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

        ListVideoItemBinding binding;

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
        void onItemClick(int pos, Youtube dataItem);
    }
    public void filterData(ArrayList<Youtube> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
