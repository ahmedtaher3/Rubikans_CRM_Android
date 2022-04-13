package com.devartlab.ui.main.ui.devartlink.handBook;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devartlab.R;
import com.devartlab.databinding.IndexSecHandBookItemBinding;
import com.devartlab.databinding.IndexSubHandBookItemBinding;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Section;
import com.devartlab.ui.main.ui.devartlink.handBook.model.Sub;

import java.util.ArrayList;
import java.util.List;

public class IndexSectionHandBookAdapter extends RecyclerView.Adapter<IndexSectionHandBookAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Section> dataItems;
    private Context context;

    public IndexSectionHandBookAdapter(List<Section> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.index_sec_hand_book_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Section dataItem = dataItems.get(position);
//        viewHolder.binding.tvSubjectTitle.setText(dataItem.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.binding.tvSubjectTitle.setText(
                    Html.fromHtml(dataItem.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else
            viewHolder.binding.tvSubjectTitle.setText(Html.fromHtml(dataItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        if (dataItems == null)
            return 0;
        return dataItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        IndexSecHandBookItemBinding binding;

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

    IndexSectionHandBookAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(IndexSectionHandBookAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void filterData(ArrayList<Section> newList ){
        this.dataItems = newList;
        notifyDataSetChanged(); // refresh
    }
}
