package com.devartlab.ui.main.ui.eShopping.pharmacySales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.a4eshopping.pharmacySales.model.Data;
import com.devartlab.databinding.PharmacySalesItemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PharmaciesSalesAdapter extends RecyclerView.Adapter<PharmaciesSalesAdapter.ViewHolder> {

    List<Data> dataItems;
    private Context context;

    public PharmaciesSalesAdapter(List<Data> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pharmacy_sales_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = dataItems.get(position);

        if (dataItem.getStatus().equals("completed")) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_verified_sales_pharmacies);
        } else if (dataItem.getStatus().equals("declined")) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_error_sales);
        } else if (dataItem.getStatus().equals("pending")) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_preview_sales);
        } else if (dataItem.getStatus().equals("processing")) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_process);
        } else if (dataItem.getStatus().equals("on delivery")) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_delivery);
        } else if (dataItem.getStatus().equals("returned")) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_returned);
        } else if (dataItem.getStatus().equals("pre declined")) {
            viewHolder.binding.ivStatus.setImageResource(R.drawable.ic_pre_declined);
        }
        viewHolder.binding.tvNoPharmacy.setText(String.valueOf(dataItem.getId()));
        viewHolder.binding.tvNamePharmacy.setText(dataItem.getName());
        convertDateTime(dataItem.getCreated_at(), viewHolder.binding.tvStartDate);
        viewHolder.binding.tvUpdateDate.setText(dataItem.getUpdated_at());
        viewHolder.binding.tvOrderNumber.setText(dataItem.getOrder_number());
        viewHolder.binding.tvPayAmount.setText(String.valueOf(dataItem.getPay_amount()));

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

        PharmacySalesItemBinding binding;

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
        void onItemClick(int pos, Data dataItem);
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

}
