package com.devartlab.ui.main.ui.eShopping.addProductsToThePharmacy;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.Pharmacy.Prod;
import com.devartlab.a4eshopping.orientationVideos.model.Item;
import com.devartlab.databinding.AddProductsPharmacyItemBinding;

import java.util.ArrayList;
import java.util.List;

public class AddProductsPharmaciesAdapter extends RecyclerView.Adapter<AddProductsPharmaciesAdapter.ViewHolder> {

    List<Prod> dataItems;
    private Context context;
    int removeOrAdd = 0;
    double amount;

    public AddProductsPharmaciesAdapter(List<Prod> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_products_pharmacy_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Prod dataItem = dataItems.get(position);
        Double discount = (1 - (dataItem.getPrice() / dataItem.getPrevious_price())) * 100;
        viewHolder.binding.tvNamePharmacy.setText(dataItem.getName());
        viewHolder.binding.tvIdPharmacy.setText(dataItem.getSku());
        viewHolder.binding.tvUpdateDate.setText(String.format("%.2f", dataItem.getPrevious_price()) + " EGP");
        viewHolder.binding.tvPrice.setText(String.format("%.2f", dataItem.getPrice()) + " EGP");
        viewHolder.binding.tvUpdateDate.setPaintFlags(viewHolder.binding.tvUpdateDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.binding.tvNoPharmacy.setText(String.format("%.2f", discount) + "%");

        viewHolder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(viewHolder.binding.pharmacySearch.getText().toString()) || Integer.parseInt(viewHolder.binding.pharmacySearch.getText().toString()) <= 0||viewHolder.binding.pharmacySearch.getText().toString()=="-") {
                    viewHolder.binding.pharmacySearch.setError("please enter right quantity");
                } else {
                    if (!dataItem.getFlag()) {
                        Log.e("ccc", String.valueOf(dataItem.getFlag()));
                        amount = Float.parseFloat(viewHolder.binding.pharmacySearch.getText().toString());
                        viewHolder.binding.tvTotal.setText(String.format("%.2f", amount * dataItem.getPrice()) + " EGP");
                        dataItem.setFlag(true);
                        viewHolder.binding.pharmacySearch.setEnabled(false);
                        viewHolder.binding.btnAdd.setText("remove");
                        viewHolder.binding.btnAdd.setBackgroundResource(R.drawable.bg_red);
                        onItemClickListener.onItemClick(position, dataItem.getId(), Integer.parseInt(viewHolder.binding.pharmacySearch.getText().toString()));
                    } else {
                        Log.e("ccc", String.valueOf(dataItem.getFlag()));
                        onItemClickListener2.onItemClick2(position, dataItem.getId(), Integer.parseInt(viewHolder.binding.pharmacySearch.getText().toString()));
                        dataItem.setFlag(false);
                        viewHolder.binding.pharmacySearch.setEnabled(true);
                        viewHolder.binding.btnAdd.setText("Add");
                        viewHolder.binding.btnAdd.setBackgroundResource(R.drawable.check_status_trip_green);
                        viewHolder.binding.pharmacySearch.setText(null);
                        viewHolder.binding.tvTotal.setText("0 EGP");
                    }
                }
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

        AddProductsPharmacyItemBinding binding;

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, int id, int amount);
    }

    public void setOnItemClickListener2(OnItemClickListener2 onItemClickListener2) {
        this.onItemClickListener2 = onItemClickListener2;
    }

    public interface OnItemClickListener2 {
        void onItemClick2(int pos, int id, int amount);
    }

    public void filterData(ArrayList<Prod> newList) {
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }


}
