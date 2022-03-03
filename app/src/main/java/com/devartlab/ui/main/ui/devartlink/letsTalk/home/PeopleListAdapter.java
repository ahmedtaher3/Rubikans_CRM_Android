package com.devartlab.ui.main.ui.devartlink.letsTalk.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.devartlab.R;
import com.devartlab.base.BaseApplication;
import com.devartlab.data.retrofit.RetrofitClient;
import com.devartlab.data.shared.DataManager;
import com.devartlab.databinding.ItemChatUserBinding;
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.ImageModel.ImageProfileResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.peopleList.DataItem;
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ViewHolder> {

    List<DataItem> dataItems;
    private Context context;

    public PeopleListAdapter(List<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_user, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final DataItem dataItem = dataItems.get(position);

        if (dataItem.getDepartementsUserNameapi().getId().equals(UserPreferenceHelper.getUserChat().getId())) {
            viewHolder.binding.name.setText(dataItem.getUserapi().getName());
            viewHolder.binding.details.setText(dataItem.getUserapi().getHrmsJobEnName());
            convertDateTime(dataItem.getCreatedAt(), viewHolder.binding.date,
                    viewHolder.binding.time);

        } else {
            viewHolder.binding.name.setText(dataItem.getDepartementsUserNameapi().getName());
            viewHolder.binding.details.setText(dataItem.getDepartementsUserNameapi().getHrmsJobEnName());
            convertDateTime(dataItem.getCreatedAt(), viewHolder.binding.date,
                    viewHolder.binding.time);

        }

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position, dataItem);
                }
            });
        }

        Picasso.get()
                .load("https://devartlink.devartlab.com/api/imagev3?id="+dataItem.getUserapi().getId()+".png")
                .centerCrop()
                .resize(66, 66)
                .placeholder(R.drawable.user_defult)
                .error(android.R.drawable.stat_notify_error)
                .into(viewHolder.binding.profileImage);
        if (dataItem.getUnseenapi().size()!=0){
            viewHolder.binding.tvCountUnseed.setVisibility(View.VISIBLE);
            viewHolder.binding.tvCountUnseed.setText(String.valueOf(dataItem.getUnseenapi().size()));
        }
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemChatUserBinding binding;

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
        void onItemClick(int pos, DataItem dataItem);
    }

    public void convertDateTime(String format, TextView date, TextView time) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputTime = new SimpleDateFormat("HH:mm");

        Date d = null;
        try {
            d = input.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        String formatted1 = outputTime.format(d);
        date.setText(formatted);
        time.setText(formatted1);
    }

}
