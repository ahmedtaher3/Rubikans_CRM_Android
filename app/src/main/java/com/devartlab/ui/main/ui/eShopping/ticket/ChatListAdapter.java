package com.devartlab.ui.main.ui.eShopping.ticket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.a4eshopping.ticket.model.fetchMessages.Data;
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    List<Data> messages;
    private Context context;
    public ChatListAdapter(List<Data> messages) {
        this.messages = messages;
    }

    private final static int INCOMING = 1;
    private final static int OUTGOING = 2;
    private final static int OUTGOINGIMG = 3;
    private final static int INGOINGIMG = 4;

    @Override
    public int getItemViewType(int position) {
        Data message = messages.get(position);
        if (message.getUser_id()== UserPreferenceHelper.getUser().getId()) {
            if (message.getAttachment() != null) {
                return OUTGOINGIMG;
            } else {
                return OUTGOING;
            }
        } else if (message.getUser_id()!=UserPreferenceHelper.getUser().getId()) {
            if (message.getAttachment() != null) {
                return INGOINGIMG;
            }
        }
        return INCOMING;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == INCOMING) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item_message_incoming_e_shopping, parent, false);
        } else if (viewType == OUTGOING) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item_message_outgoing_e_shopping, parent, false);

        } else if (viewType == OUTGOINGIMG) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item_message_outgoing_img_e_shopping, parent, false);

        } else if (viewType == INGOINGIMG) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item_message_incoming_img_e_shopping, parent, false);

        }
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Data dataItem = messages.get(position);

        viewHolder.content.setText(dataItem.getMessage());
        viewHolder.time.setText(covertTimeToText(dataItem.getCreated_at()));
        if (dataItem.getAttachment() != null) {
            String result = dataItem.getAttachment();
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                String aJsonString = jObject.getString("new_name");
                Glide.with(context)
                        .load("https://t4e.4eshopping.com/storage/attachments/" +
                                aJsonString)
                        .into(viewHolder.attachment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (dataItem.getUser_id()==UserPreferenceHelper.getUser().getId()) {
            if (dataItem.getSeen()==0) {
                viewHolder.seen.setImageResource(R.drawable.unseen);
            } else {
                viewHolder.seen.setImageResource(R.drawable.seen);
            }
            if (onItemClickListener != null) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickListener.onItemLongClick(position, dataItem);
                        return true;
                    }
                });
            }
        }
        if (dataItem.getDeleted()==1){
            viewHolder.linear_deleted.setVisibility(View.VISIBLE);
            viewHolder.conlay_message.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (messages == null)
            return 0;
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView content;
        public TextView time;
        public ImageView seen;
        public ImageView attachment;
        public LinearLayout linear_deleted;
        public ConstraintLayout conlay_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            seen = itemView.findViewById(R.id.seen);
            attachment = itemView.findViewById(R.id.imgMsg);
            linear_deleted=itemView.findViewById(R.id.linear_deleted);
            conlay_message=itemView.findViewById(R.id.conlay_message);
        }
    }

    public String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes "+suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours "+suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day+" Days "+suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }

    OnItemLongClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemLongClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(int pos, Data dataItem);
    }
}
