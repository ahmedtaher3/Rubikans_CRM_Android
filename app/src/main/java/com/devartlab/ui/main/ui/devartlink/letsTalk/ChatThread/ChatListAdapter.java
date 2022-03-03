package com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.base.BaseApplication;
import com.devartlab.data.retrofit.RetrofitClient;
import com.devartlab.data.shared.DataManager;
import com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model.DataItem;
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.ImageModel.ImageProfileResponse;
import com.devartlab.ui.main.ui.eShopping.ticket.model.fetchMessages.Data;
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    List<DataItem> messages;
    private Context context;
    Pusher pusher;
    Channel channel;

    public ChatListAdapter(List<DataItem> messages) {
        this.messages = messages;
    }

    private final static int INCOMING = 1;
    private final static int OUTGOING = 2;
    private final static int OUTGOINGIMG = 3;
    private final static int INGOINGIMG = 4;

    @Override
    public int getItemViewType(int position) {
        DataItem message = messages.get(position);
        if (message.getUserId().equals(UserPreferenceHelper.getUserChat().getId())) {
            if (message.getAttachment() != null) {
                return OUTGOINGIMG;
            } else {
                return OUTGOING;
            }
        } else if (!message.getUserId().equals(UserPreferenceHelper.getUserChat().getId())) {
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
                    .inflate(R.layout.card_item_message_incoming, parent, false);
        } else if (viewType == OUTGOING) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item_message_outgoing, parent, false);

        } else if (viewType == OUTGOINGIMG) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item_message_outgoing_img, parent, false);

        } else if (viewType == INGOINGIMG) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item_message_incoming_img, parent, false);

        }
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final DataItem dataItem = messages.get(position);
        viewHolder.content.setText(dataItem.getMessage());
        viewHolder.time.setText(covertTimeToText(dataItem.getUpdatedAt()));
        if (dataItem.getAttachment() != null) {
            String result = dataItem.getAttachment();
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                String aJsonString = jObject.getString("new_name");
                Glide.with(context)
                        .load("https://devartlink.devartlab.com/storage/attachments/" +
                                aJsonString)
                        .into(viewHolder.attachment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!dataItem.getUserId().equals(UserPreferenceHelper.getUserChat().getId())) {
            viewHolder.sender_name.setText(dataItem.getUserapi().getName());
            viewHolder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, viewHolder.textViewOptions);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.lats_talk_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_delete:
                                    Toast.makeText(context,"you can't delete message",Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.action_reply:
                                    onItemClickListener2.onItemLongClick2(position, dataItem.getMessage());
                                    return true;
                                case R.id.action_forward:
                                    onItemClickListener3.onItemLongClick3(position, dataItem.getMessage());
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();

                }
            });
            Picasso.get()
                    .load("https://devartlink.devartlab.com/api/imagev3?id="+dataItem.getUserapi().getId()+".png")
                    .centerCrop()
                    .resize(50, 50)
                    .placeholder(R.drawable.user_defult)
                    .error(android.R.drawable.stat_notify_error)
                    .into(viewHolder.img_sender);
        }
        if (dataItem.getUserId().equals(UserPreferenceHelper.getUserChat().getId())) {
            viewHolder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, viewHolder.textViewOptions);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.lats_talk_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_delete:
                                    onItemClickListener.onItemLongClick(position, dataItem);
                                    return true;
                                case R.id.action_reply:
                                    onItemClickListener2.onItemLongClick2(position, dataItem.getMessage());
                                    return true;
                                case R.id.action_forward:
                                    onItemClickListener3.onItemLongClick3(position, dataItem.getMessage());
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //displaying the popup
                    popup.show();

                }
            });
            if (dataItem.getSeen().equals("0")) {
                viewHolder.seen.setImageResource(R.drawable.unseen);
            } else if (dataItem.getSeen().equals("1"))  {
                viewHolder.seen.setImageResource(R.drawable.seen);
            }else {
                viewHolder.seen.setImageResource(R.drawable.ic_clock);
            }

            if (isConnected()) {
                Picasso.get()
                        .load("https://devartlink.devartlab.com/api/imagev3?id="+dataItem.getUserapi().getId()+".png")
                        .centerCrop()
                        .resize(50, 50)
                        .placeholder(R.drawable.user_defult)
                        .error(android.R.drawable.stat_notify_error)
                        .into(viewHolder.img_user);
            }

        }
        if (dataItem.isIs_deleted()){
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
        public TextView sender_name;
        public ImageView img_user;
        public ImageView img_sender;
        public ImageView seen;
        public ImageView attachment;
        public LinearLayout linear_deleted;
        public ConstraintLayout conlay_message;
        public TextView textViewOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            sender_name = itemView.findViewById(R.id.sender_name);
            img_user = itemView.findViewById(R.id.img_user);
            img_sender = itemView.findViewById(R.id.img_sender);
            seen = itemView.findViewById(R.id.seen);
            attachment = itemView.findViewById(R.id.imgMsg);
            linear_deleted=itemView.findViewById(R.id.linear_deleted);
            conlay_message=itemView.findViewById(R.id.conlay_message);
            textViewOptions=itemView.findViewById(R.id.textViewOptions);
        }
    }


    public String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
    OnItemLongClickListener2 onItemClickListener2;
    OnItemLongClickListener3 onItemClickListener3;
    public void setOnItemClickListener(OnItemLongClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(int pos, DataItem dataItem);
    }
    public void setOnItemClickListener2(OnItemLongClickListener2 onItemClickListener2) {
        this.onItemClickListener2 = onItemClickListener2;
    }
    public interface OnItemLongClickListener2 {
        void onItemLongClick2(int pos, String dataItem);
    }
    public void setOnItemClickListener3(OnItemLongClickListener3 onItemClickListener3) {
        this.onItemClickListener3 = onItemClickListener3;
    }
    public interface OnItemLongClickListener3 {
        void onItemLongClick3(int pos, String dataItem);
    }

    public void refreshMessages(DataItem message) {
        this.messages.add(0,message);
        Log.e("saassa", String.valueOf(message));
        notifyItemInserted(messages.size() - 1);
        notifyDataSetChanged();
    }

    boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;

    }
}
