package com.devartlab.ui.main.ui.callmanagement.report.superreport;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import com.devartlab.R;
import com.devartlab.model.EmPloyeeAppraisalFlag;
import com.devartlab.utils.CommonUtilities;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<EmPloyeeAppraisalFlag> itemList;
    ArrayList<String> correctiveActions;
    ArrayList<Integer> correctiveActionsIds;
    private Context context;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition = 0;
    private TextDrawable mDrawableBuilder;

    public ArrayList<EmPloyeeAppraisalFlag> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<EmPloyeeAppraisalFlag> itemList) {
        this.itemList = itemList;
    }

    ItemAdapter(Context context, ArrayList<EmPloyeeAppraisalFlag> itemList, ArrayList<String> correctiveActions, ArrayList<Integer> correctiveActionsIds) {
        this.context = context;
        this.itemList = itemList;
        this.correctiveActions = correctiveActions;
        this.correctiveActionsIds = correctiveActionsIds;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, @SuppressLint("RecyclerView") int i) {
        EmPloyeeAppraisalFlag item = itemList.get(i);
        itemViewHolder.tvItemTitle.setText(item.getEmpName());
        itemViewHolder.correctiveEditText.setText(item.getCorrectiveActionsComment());

        itemViewHolder.correctiveEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                itemList.get(i).setCorrectiveActionsComment(cs.toString());
                itemList.get(i).setCorrectiveActionsID(correctiveActionsIds.get(i));

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        itemViewHolder.setName(item.getEmpName(), CommonUtilities.getRandomColor());

        itemViewHolder.correctiveSpinner.setText(item.getCorrectiveActions());
        itemViewHolder.correctiveSpinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, correctiveActions));
        itemViewHolder.correctiveSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                itemList.get(i).setCorrectiveActions(correctiveActions.get(pos));
                //itemList.get(i).setCorrectiveActionsID(correctiveActions.get(i));
            }
        });




        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.rvSubItem.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.geteMPloyeeAppraisal().size());

        // Create sub item view adapter
        SubItemAdapter subItemAdapter = new SubItemAdapter(item.geteMPloyeeAppraisal());

        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemTitle;
        private RecyclerView rvSubItem;
        private ImageView list_image_name;
        private MaterialBetterSpinner correctiveSpinner;
        private LinearLayout recyclerLayout;
        private EditText correctiveEditText;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
            list_image_name = itemView.findViewById(R.id.list_image_name);
            correctiveSpinner = itemView.findViewById(R.id.correctiveSpinner);
            recyclerLayout = itemView.findViewById(R.id.recyclerLayout);
            correctiveEditText = itemView.findViewById(R.id.correctiveEditText);
        }

        public void setName(String title, int color) {
            String letter = "A";

            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1);
            }


            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color);
            list_image_name.setImageDrawable(mDrawableBuilder);
        }

    }


}
