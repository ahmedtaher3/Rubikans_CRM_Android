package com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
                                //handle menu1 click
                                onItemClickListener.onItemLongClick(position, dataItem);
                                return true;
                            case R.id.action_reply:
                                //handle menu2 click
                                onItemClickListener2.onItemLongClick2(position, dataItem.getMessage());
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

            RetrofitClient.getApis().getImageProfile(dataItem.getUserapi().getId())
                    .enqueue(new Callback<ImageProfileResponse>() {
                        @Override
                        public void onResponse(Call<ImageProfileResponse> call, Response<ImageProfileResponse> response) {
                            if (response.isSuccessful()) {
                                String base64String = response.body().getImg();
                                String base64Image = base64String.split(",")[1];
                                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString
                                        , 0, decodedString.length);
                                viewHolder.img_sender.setImageBitmap(decodedByte);
                            }
                        }

                        @Override
                        public void onFailure(Call<ImageProfileResponse> call, Throwable t) {
                            //   errorMessage.postValue(1);
                        }
                    });
        }
        if (dataItem.getUserId().equals(UserPreferenceHelper.getUserChat().getId())) {
            if (dataItem.getSeen().equals("0")) {
                viewHolder.seen.setImageResource(R.drawable.unseen);
            } else {
                viewHolder.seen.setImageResource(R.drawable.seen);
            }

            RetrofitClient.getApis().getImageProfile(dataItem.getUserapi().getId())
                    .enqueue(new Callback<ImageProfileResponse>() {
                        @Override
                        public void onResponse(Call<ImageProfileResponse> call, Response<ImageProfileResponse> response) {
                            if (response.isSuccessful()) {
                                String base64String = response.body().getImg();
                                String base64Image = base64String.split(",")[1];
                                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString
                                        , 0, decodedString.length);
                                viewHolder.img_user.setImageBitmap(decodedByte);
                            }
                        }

                        @Override
                        public void onFailure(Call<ImageProfileResponse> call, Throwable t) {
                            //   errorMessage.postValue(1);
                        }
                    });

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
}
