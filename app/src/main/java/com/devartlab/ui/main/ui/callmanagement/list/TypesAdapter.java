package com.devartlab.ui.main.ui.callmanagement.list;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.MemoryCategory;
import com.devartlab.R;
import com.devartlab.data.room.listtypes.ListTypesEntity;
import com.devartlab.ui.main.ui.callmanagement.list.list.CustomerListFragment;


public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.MyViewHolder> {
    private List<ListTypesEntity> myData;
    private FragmentTransaction fragmentTransaction;
    private Context context;


    public TypesAdapter(Context context, FragmentTransaction fragmentTransaction) {
        this.myData = new ArrayList<>();
        this.context = context;
        this.fragmentTransaction = fragmentTransaction;
    }

    @Override
    public TypesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);

        return new TypesAdapter.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ListTypesEntity model = myData.get(position);

        holder.typeName.setText(model.listType);
        holder.count.setText("(" + model.totalCustomer +" customer)");
        Log.d("TAG", "onBindViewHolder: " + model.iconImageUrl);
        System.out.println("onBindViewHolder: " + model.iconImageUrl);
       /*
        Glide.with(context)
                .load(model.getIconImageUrl())
                .into(holder.typeImage);*/
      if (model.listTypeId == 1) {

        Glide.with(context)
                  .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/clinic-22.png")
                  .into(holder.typeImage);
        } else if (model.listTypeId == 2) {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/pharmacy-21.png")
                    .into(holder.typeImage);
        } else if (model.listTypeId == 3) {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/Distruption-24.png")
                    .into(holder.typeImage);
        }else if (model.listTypeId == 11) {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/hospital-23.png")
                    .into(holder.typeImage);
        }else if (model.listTypeId == 64) {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/pharmacy-21.png")
                    .into(holder.typeImage);
        }else if (model.listTypeId == 67) {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/chans-28.png")
                    .into(holder.typeImage);
        }
        else if (model.listTypeId == 69) {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/Trade-26.png")
                    .into(holder.typeImage);
        }else if (model.listTypeId == 224) {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/social_media_group-27.png")
                    .into(holder.typeImage);
        }else {

        Glide.with(context)
                    .load("https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589458219/icons/clinic-22.png")
                    .into(holder.typeImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Bundle bundle = new Bundle();
                bundle.putSerializable("LIST_TYPE_MODEL", model);
                CustomerListFragment fragment = new CustomerListFragment();
                fragment.setArguments(bundle);


                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left
                )
                        .add(
                                R.id.Container,
                                fragment
                        )
                        .addToBackStack("ListkFragment")
                        .commit();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<ListTypesEntity> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


        ImageView typeImage;
        TextView typeName;
        TextView count;


        public MyViewHolder(View v) {
            super(v);
            typeImage = v.findViewById(R.id.typeImage);
            typeName = v.findViewById(R.id.typeName);
            count = v.findViewById(R.id.count);

        }
    }


}