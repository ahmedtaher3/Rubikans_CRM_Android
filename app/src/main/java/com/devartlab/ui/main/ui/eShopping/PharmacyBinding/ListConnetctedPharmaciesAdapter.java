package com.devartlab.ui.main.ui.eShopping.PharmacyBinding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.a4eshopping.PharmacyBinding.model.searchForPharmacy.DataX;
import com.devartlab.databinding.PharmacyBingingItemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListConnetctedPharmaciesAdapter extends RecyclerView.Adapter<ListConnetctedPharmaciesAdapter.ViewHolder> {

    List<DataX> dataItems;
    private Context context;

    public ListConnetctedPharmaciesAdapter(List<DataX> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pharmacy_binging_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final DataX dataItem = dataItems.get(position);

        if (dataItem.getActivated() == 4) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_verified);
            viewHolder.binding.ivStatus.setBackgroundResource(R.color.color_20F109);
        } else if (dataItem.getActivated() == 3) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_error);
            viewHolder.binding.ivStatus.setBackgroundResource(R.color.red);
        } else if (dataItem.getActivated() == 5) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_preview);
            viewHolder.binding.ivStatus.setBackgroundResource(R.color.color_F1DA09);
        }
        viewHolder.binding.tvNoPharmacy.setText(String.valueOf(dataItem.getId()));
        viewHolder.binding.tvNamePharmacy.setText(dataItem.getName());
        convertDateTime(dataItem.getCreated_at(), viewHolder.binding.tvStartDate);
        convertDateTime(dataItem.getUpdated_at(), viewHolder.binding.tvUpdateDate);
        viewHolder.binding.ivComment.setText(String.valueOf(dataItem.getComments_track_count()));
        viewHolder.binding.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick( position, dataItem);
            }
        });
        viewHolder.binding.ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener2.onItemClick2( position, dataItem);
            }
        });
        viewHolder.binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener3.onItemClick3( position, dataItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        PharmacyBingingItemBinding binding;

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
    OnItemClickListener2 onItemClickListener2;
    OnItemClickListener3 onItemClickListener3;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, DataX dataItem);
    }
    public void setOnItemClickListener2(OnItemClickListener2 onItemClickListener2) {
        this.onItemClickListener2 = onItemClickListener2;
    }

    public interface OnItemClickListener2 {
        void onItemClick2(int pos, DataX dataItem);
    }
    public void setOnItemClickListener3(OnItemClickListener3 onItemClickListener3) {
        this.onItemClickListener3 = onItemClickListener3;
    }

    public interface OnItemClickListener3 {
         void onItemClick3(int pos, DataX dataItem);
    }


    public void convertDateTime(String format, TextView date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        date.setText(formatted);
    }

    void filterData(ArrayList<DataX> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
