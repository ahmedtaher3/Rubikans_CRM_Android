package com.devartlab.ui.main.ui.devartlink.handBook;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.databinding.HandBookItemBinding;
import com.devartlab.databinding.SectionsItemBinding;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Section;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.ViewHolder> {
    List<Section> dataItems;
    private Context context;

    public SectionsAdapter(List<Section> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sections_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Section dataItem = dataItems.get(position);
        viewHolder.binding.tvSubjectTitle.setText(dataItem.getTitle());
        Glide.with(context)
                .load("https://devartlink.devartlab.com/assets/images/" + dataItem.getImage())
                    .fitCenter().into(viewHolder.binding.imgSubject);
        String b = dataItem.getDescription().replace("\\\"", "\"");
        writeToFile(b, context);
        viewHolder.binding.wbSections.loadDataWithBaseURL(
                null, dataItem.getDescription(), "text/html", "utf-8", null);
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SectionsItemBinding binding;

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

    public void filterData(ArrayList<Section> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("form.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            Log.e("Exception", "File write success: " + data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
