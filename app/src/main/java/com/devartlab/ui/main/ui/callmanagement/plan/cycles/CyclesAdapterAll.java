package com.devartlab.ui.main.ui.callmanagement.plan.cycles;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.data.shared.DataManager;
import com.devartlab.model.Cycle;

import java.util.ArrayList;
import java.util.List;

public class CyclesAdapterAll extends RecyclerView.Adapter<CyclesAdapterAll.MyViewHolder> {
    private List<Cycle> myData;
    private Context context;
    private CyclesAdapter.CycleInterface cycleInterface;
    private DataManager dataManager;

    public CyclesAdapterAll(Context context, CyclesAdapter.CycleInterface cycleInterface, DataManager dataManager) {
        this.myData = new ArrayList<>();
        this.context = context;
        this.cycleInterface = cycleInterface;
        this.dataManager = dataManager;

    }
 
    @Override
    public CyclesAdapterAll.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        
 
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cycle_item, parent, false);

    
        return new CyclesAdapterAll.MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


                final Cycle model = myData.get(position);

                holder.CycleName.setText(model.getCycleArName());
                holder.CycleStart.setText(model.getFromDate().substring(0, 10));
                holder.CycleEnd.setText(model.getToDate().substring(0, 10));
                holder.cardCycle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dataManager.isNewCycle(false);
                        cycleInterface.setCycle(model);
                    }
                });
     

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void setMyData(List<Cycle> myData) {
        this.myData = myData;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView CycleName;
        public TextView CycleEnd;
        public TextView CycleStart;
        public CardView cardCycle;

        public MyViewHolder(View v) {
            super(v);
            CycleName = v.findViewById(R.id.CycleName);
            CycleStart = v.findViewById(R.id.CycleStart);
            CycleEnd = v.findViewById(R.id.CycleEnd);
            cardCycle = v.findViewById(R.id.cardCycle);
        }
    }


}