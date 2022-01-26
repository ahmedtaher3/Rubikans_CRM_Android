package com.devartlab.ui.main.ui.eShopping.ticket;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.a4eshopping.ticket.model.getContacts.Data;
import com.devartlab.databinding.ItemTicketBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GetContactsAdapter extends RecyclerView.Adapter<GetContactsAdapter.ViewHolder> {

    List<Data> dataItems;
    private Context context;

    public GetContactsAdapter(List<Data> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = dataItems.get(position);
        viewHolder.binding.tvNoTicket.setText(dataItem.getTicket_id());
        viewHolder.binding.tvDecTicket.setText(dataItem.getSubject());
        viewHolder.binding.tvTypeTicket.setText(dataItem.getTicket_type());
        convertDateTime(dataItem.getCreated_at(), viewHolder.binding.tvStartDate);
        if (dataItem.getStatus() == 1) {
            viewHolder.binding.tvConditionTicket.setText(R.string.action_open);
            viewHolder.binding.tvConditionTicket.setBackgroundResource(R.drawable.check_status_trip_green);
        } else if (dataItem.getStatus() == 2) {
            viewHolder.binding.tvConditionTicket.setText(R.string.action_inreview);
            viewHolder.binding.tvConditionTicket.setBackgroundResource(R.drawable.check_status_trip_yellow);
        } else if (dataItem.getStatus() == 3) {
            viewHolder.binding.tvConditionTicket.setText(R.string.action_close);
            viewHolder.binding.tvConditionTicket.setBackgroundResource(R.drawable.check_status_trip_red);
        } else if (dataItem.getStatus() == 4) {
            viewHolder.binding.tvConditionTicket.setText(R.string.action_pending);
            viewHolder.binding.tvConditionTicket.setBackgroundResource(R.drawable.check_status_trip_gray);
        }
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position, dataItem);
                }
            });

            if (onItemClickListener2 != null) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickListener2.onItemClick2(position, String.valueOf(dataItem.getId()), String.valueOf(dataItem.getStatus()));
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemTicketBinding binding;

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
        void onItemClick(int pos, Data dataItem);
    }

    public void setOnItemClickListener2(OnItemClickListener2 onItemClickListener2) {
        this.onItemClickListener2 = onItemClickListener2;
    }

    public interface OnItemClickListener2 {
        void onItemClick2(int pos, String noOrder,String status);
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

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS'Z'");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        try {
//            long time = sdf.parse(format).getTime();
//            long now = System.currentTimeMillis();
//            CharSequence ago =
//                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
//            date.setText(ago);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

}
