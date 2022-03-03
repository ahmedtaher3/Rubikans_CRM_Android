package com.devartlab.ui.main.ui.callmanagement.incentiveRule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.databinding.ListTeamsItemBinding;
import com.devartlab.ui.main.ui.callmanagement.incentiveRule.model.Incentive;

import java.util.List;

public class DevartLabIncentiveAdapter extends RecyclerView.Adapter<DevartLabIncentiveAdapter.ViewHolder> {

    List<Incentive> dataItems;
    private Context context;

    public DevartLabIncentiveAdapter(List<Incentive> dataItems) {
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
        final Incentive dataItem = dataItems.get(position);

        viewHolder.binding.name.setText(dataItem.getName());
        Glide.with(context).load("https://devartlink.devartlab.com/assets/images/"+dataItem.getImage()).centerCrop().into(viewHolder.binding.image);
        viewHolder.binding.tvDecTeam.setText( dataItem.getSubtitle());
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
        void onItemClick(int pos, Incentive dataItem);
    }
}
