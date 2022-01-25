package com.devartlab.ui.main.ui.eShopping.orientationVideos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.a4eshopping.orientationVideos.model.Item;
import com.devartlab.databinding.ListVideoItemBinding;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    List<Item> dataItems;
    private Context context;

    public VideoListAdapter(List<Item> dataItems) {
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
        final Item dataItem = dataItems.get(position);
        viewHolder.binding.dec.setText(dataItem.getSnippet().getDescription());
        viewHolder.binding.nameChannel.setText(dataItem.getSnippet().getChannelTitle());
        int postion=dataItem.getSnippet().getPosition()+1;
        viewHolder.binding.numberVideo.setText(String.valueOf(postion));
//        Picasso.get()
//                .load(dataItem.getSnippet().getThumbnails().getDefault().getUrl())
//                .centerCrop()
//                .resize(320 , 180)
//                .placeholder(R.drawable.ic_baseline_replay_24)
//                .error(android.R.drawable.stat_notify_error)
//                .into(viewHolder.binding.image);
        viewHolder.binding.name.setText(dataItem.getSnippet().getTitle());
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
        void onItemClick(int pos, Item dataItem);
    }
    public void filterData(ArrayList<Item> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
