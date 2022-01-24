package com.devartlab.ui.main.ui.eShopping.pharmacySales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.a4eshopping.pharmacySales.model.detailsPharmacySales.Detail;
import com.devartlab.databinding.DetailsPharmacySalesItemBinding;

import java.util.List;

public class DetailsPharmaciesSalesAdapter extends RecyclerView.Adapter<DetailsPharmaciesSalesAdapter.ViewHolder> {

    List<Detail> dataItems;
    private Context context;

    public DetailsPharmaciesSalesAdapter(List<Detail> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_pharmacy_sales_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Detail dataItem = dataItems.get(position);

        Double discount = (1 - dataItem.getProduct().getPrice()) / dataItem.getProduct().getPrevious_price()*100;
        viewHolder.binding.tvNoPharmacy.setText(String.format("%.2f",discount)+"%");
        viewHolder.binding.tvNamePharmacy.setText(dataItem.getProduct().getSku()+dataItem.getProduct_name());
        viewHolder.binding.tvStartDate.setText(String.valueOf(dataItem.getProduct_qty()));
        viewHolder.binding.tvUpdateDate.setText(String.format("%.2f", dataItem.getProduct_price())+" EGP");
        viewHolder.binding.tvTotal.setText(String.format("%.2f",dataItem.getTotal_price())+" EGP");

    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        DetailsPharmacySalesItemBinding binding;

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
