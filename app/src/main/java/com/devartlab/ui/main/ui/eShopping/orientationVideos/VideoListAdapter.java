package com.devartlab.ui.main.ui.eShopping.orientationVideos;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.devartlab.R;
import com.devartlab.databinding.ListVideoItemBinding;
import com.devartlab.ui.main.ui.eShopping.orientationVideos.model.ItemsVideos;
import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    List<ItemsVideos> dataItems;

    public VideoListAdapter(List<ItemsVideos> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final ItemsVideos dataItem = dataItems.get(position);
        viewHolder.binding.dec.setText(dataItem.getSnippet().getDescription());
        viewHolder.binding.nameChannel.setText(dataItem.getSnippet().getChannelTitle());
        int pos=dataItem.getSnippet().getPosition()+1;
        viewHolder.binding.numberVideo.setText(String.valueOf(pos));
        viewHolder.binding.name.setText(dataItem.getSnippet().getTitle());
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position, dataItem));
        }
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

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

    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, ItemsVideos dataItem);
    }
    public void filterData(ArrayList<ItemsVideos> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
