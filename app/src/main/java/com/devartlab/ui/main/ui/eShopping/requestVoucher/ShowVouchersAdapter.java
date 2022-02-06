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
import com.devartlab.databinding.ShowVouchersItemBinding;
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.getVoucher.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowVouchersAdapter extends RecyclerView.Adapter<ShowVouchersAdapter.ViewHolder> {

    List<Data> dataItems;
    private Context context;

    public ShowVouchersAdapter(List<Data> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_vouchers_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = dataItems.get(position);

        if (!dataItem.getDelivered_time().equals(null)) {
            convertDateTime(dataItem.getDelivered_time(),viewHolder.binding.tvNamePharmacy);
            viewHolder.binding.btnAdd.setVisibility(View.GONE);
        } else if (dataItem.getDelivered_time().equals(null)) {
            viewHolder.binding.tvNamePharmacy.setVisibility(View.GONE);
        }
        viewHolder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position, dataItem);
            }
        });

        viewHolder.binding.tvNoPharmacy.setText(String.valueOf(dataItem.getId()));
        viewHolder.binding.tvFrom.setText(String.valueOf(dataItem.getFrom()));
        viewHolder.binding.tvVouchersCount.setText(String.valueOf(dataItem.getTo()));
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ShowVouchersItemBinding binding;

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
