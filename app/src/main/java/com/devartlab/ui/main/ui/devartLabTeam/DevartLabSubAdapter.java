package com.devartlab.ui.main.ui.devartLabTeam;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.databinding.ListTeamsItemBinding;
import com.devartlab.ui.main.ui.devartLabTeam.model.Sub;
import com.devartlab.ui.main.ui.devartLabTeam.model.Team;

import java.util.List;

public class DevartLabSubAdapter extends RecyclerView.Adapter<DevartLabSubAdapter.ViewHolder> {

    List<Sub> dataItems;
    private Context context;

    public DevartLabSubAdapter(List<Sub> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_teams_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Sub dataItem = dataItems.get(position);

        viewHolder.binding.name.setText(dataItem.getName());
        Glide.with(context).load("https://devartlink.4eshopping.com/assets/images/"+dataItem.getImage()).centerCrop().into(viewHolder.binding.image);
//        viewHolder.binding.tvDecTeam.setText(dataItem.getSubtitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.binding.tvDecTeam.setText(
                    Html.fromHtml(dataItem.getSubtitle(), Html.FROM_HTML_MODE_LEGACY));
        } else
            viewHolder.binding.tvDecTeam.setText(Html.fromHtml(dataItem.getSubtitle()));
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

        ListTeamsItemBinding binding;

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
        void onItemClick(int pos, Sub dataItem);
    }
}
