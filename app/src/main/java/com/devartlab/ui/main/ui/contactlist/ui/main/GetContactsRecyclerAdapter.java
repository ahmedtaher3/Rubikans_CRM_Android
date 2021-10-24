package com.devartlab.ui.main.ui.contactlist.ui.main;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.ui.main.ui.contactlist.pojo.Contactlist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.Inflater;


public class GetContactsRecyclerAdapter extends RecyclerView.Adapter<GetContactsRecyclerAdapter.HrcontactlistRecyclerViewHolder> {


    ArrayList<Contactlist> arrayList = new ArrayList<>();
    Context context;
    GetContactsViewModel hrcontactlistViewModel;


    public GetContactsRecyclerAdapter(Context context1) {
        this.context = context1;
    }

    int lastposition;

    public ArrayList<Contactlist> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Contactlist> arrayList) {

        this.arrayList = arrayList;
        Log.d("LIST COUNT = 0 ", String.valueOf(arrayList.size()));
        notifyDataSetChanged();
        lastposition = arrayList.size();
    }


    @NonNull
    @Override
    public HrcontactlistRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HrcontactlistRecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list, parent, false));
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public void onBindViewHolder(@NonNull HrcontactlistRecyclerViewHolder holder, int position) {


        Contactlist contactlist = arrayList.get(position);

        hrcontactlistViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetContactsViewModel.class);
/*
        if (position == 0){
            holder.itemView.setBackgroundResource(R.drawable.cornerrecycler);
        }
        else if (position == lastposition-1){
            holder.itemView.setBackgroundResource(R.drawable.cornerrecyclerbottom);

        } else {
            holder.itemView.setBackgroundResource(R.color.color1);
        }
*/
        holder.name.setText(contactlist.getName().toString());
        holder.dep.setText(contactlist.getDep().toString());
        holder.titel.setText(contactlist.getTitel().toString());
        holder.firstlitter_fromname.setText(contactlist.getName().substring(0,1));


        Contactlist finalContactlist = contactlist;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactDetails.class);

                intent.putExtra("name", finalContactlist.getName());
                intent.putExtra("email", finalContactlist.getEmail());
                intent.putExtra("phone1", finalContactlist.getPhone_1() + "");
                intent.putExtra("phone2", finalContactlist.getPhone_2() + "");
                intent.putExtra("titel", finalContactlist.getTitel());
                intent.putExtra("dep", finalContactlist.getDep());
                context.startActivity(intent);

            }
        });
        holder.contactcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog;

                alertDialog = new AlertDialog.Builder(context);

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.activity_call_two_phone, null);
                alertDialog.setView(view1);
//
                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.setCanceledOnTouchOutside(false);
                Button phone1 = view1.findViewById(R.id.phone1);
                Button phone2 = view1.findViewById(R.id.phone2);
                Button back = view1.findViewById(R.id.backtomain);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });

                if (finalContactlist.getPhone_1().startsWith("0") && finalContactlist.getPhone_2().startsWith("0")) {

                    phone1.setText("   " + finalContactlist.getPhone_1());
                    phone2.setText("   " + finalContactlist.getPhone_2());
                }
                if (!finalContactlist.getPhone_1().startsWith("0")) {

                    phone1.setText("    0" + finalContactlist.getPhone_1());

                }
                if (!finalContactlist.getPhone_2().startsWith("0")) {
                    phone2.setText("    0" + finalContactlist.getPhone_2());
                }


                phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + phone1.getText()));//change the number.
                        context.startActivity(callIntent);
                    }
                });
                if (finalContactlist.getPhone_2().equals("")) {
                    phone2.setText("   ______");
                    phone2.setVisibility(View.GONE);

                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone1.getText()));//change the number.
                    context.startActivity(callIntent);

                } else {
                    alertDialog1.show();
                    phone2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + phone2.getText()));//change the number.

                            context.startActivity(callIntent);
                        }
                    });

                }


            }


        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class HrcontactlistRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name, dep, titel,firstlitter_fromname;

        CardView cardView;

        ImageView contactcall;


        @SuppressLint("ResourceAsColor")
        public HrcontactlistRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cont_name);
            dep = itemView.findViewById(R.id.cont_dep);
            titel = itemView.findViewById(R.id.cont_titel);
            contactcall = itemView.findViewById(R.id.cont_call);
            cardView = itemView.findViewById(R.id.clickto_showcontact_details);
            firstlitter_fromname = itemView.findViewById(R.id.firstlitter_fromname);

        }
    }
}
