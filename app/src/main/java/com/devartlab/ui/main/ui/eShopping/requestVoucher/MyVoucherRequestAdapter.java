package com.devartlab.ui.main.ui.eShopping.requestVoucher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.databinding.OrderRequestVoucherItemBinding;
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.myVoucherRequest.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyVoucherRequestAdapter extends RecyclerView.Adapter<MyVoucherRequestAdapter.ViewHolder> {

    List<Data> dataItems;
    private Context context;

    public MyVoucherRequestAdapter(List<Data> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_request_voucher_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = dataItems.get(position);

        if (dataItem.getStatus().equals("1")) {
            viewHolder.binding.tvStatus.setText("Accepted");
        } else if (dataItem.getStatus().equals("0")) {
            viewHolder.binding.tvStatus.setText("Pending");
            viewHolder.binding.btnAdd.setVisibility(View.GONE);
        }
        viewHolder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position, dataItem);
            }
        });

        viewHolder.binding.tvNoPharmacy.setText(String.valueOf(dataItem.getId()));
        viewHolder.binding.tvNamePharmacy.setText(dataItem.getDoctor_name());
        viewHolder.binding.tvCount.setText(String.valueOf(dataItem.getCount()/50));
        viewHolder.binding.tvVouchersCount.setText(String.valueOf(dataItem.getCount()));
        viewHolder.binding.tvUsed.setText(String.valueOf(dataItem.getUsed_voucher_values_count()));
        viewHolder.binding.tvNotUsed.setText(String.valueOf(dataItem.getCount()-dataItem.getUsed_voucher_values_count()));
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        OrderRequestVoucherItemBinding binding;

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
    public void filterData(ArrayList<Data> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
