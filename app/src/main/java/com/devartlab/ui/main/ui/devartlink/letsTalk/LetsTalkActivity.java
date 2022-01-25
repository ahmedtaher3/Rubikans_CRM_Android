package com.devartlab.ui.main.ui.devartlink.letsTalk;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.chat.home.HomeChatFragment;
import com.devartlab.R;
import com.devartlab.base.BaseApplication;
import com.devartlab.data.shared.DataManager;
import com.devartlab.databinding.ActivityLetsTalkBinding;
import com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.ChatThreadActivity;
import com.devartlab.ui.main.ui.devartlink.letsTalk.TR.TRFragment;
import com.devartlab.ui.main.ui.devartlink.letsTalk.groups.GroupsFragment;
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.searchPeople.SearchPeapleResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.user.UserResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.userID.UserIDResponse;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

public class LetsTalkActivity extends AppCompatActivity {

    ActivityLetsTalkBinding binding;
    LetsTalkViewModel viewModel;
    FragmentPagerItemAdapter adapter;
    DataManager dataManager;
    String name;

    private List<String> people = new ArrayList<>();
    private ArrayAdapter<String> adapterPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lets_talk);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.lat_talk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(LetsTalkViewModel.class);

        dataManager = ((BaseApplication) getApplication()).getDataManager();
        viewModel.getpeapleSearchList();
        prepareTabsLayout();
        onClickListener();
    }

    private void onClickListener() {
        viewModel.getErrorMessage().observe(this, integer -> {
            if (integer == 1) {
                Toast.makeText(LetsTalkActivity.this, "error in response data", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LetsTalkActivity.this, "error in Network", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getPeapleResponseMutableLiveData().observe(this, new Observer<SearchPeapleResponse>() {
            @Override
            public void onChanged(SearchPeapleResponse searchPeapleResponse) {
                adapterPeople = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, people);
                binding.tvPeopleSearch.setAdapter(adapterPeople);
                try {
                    for (int i = 0; i < searchPeapleResponse.getData().size(); i++) {
                        String name = searchPeapleResponse.getData().get(i).getName();
                        people.add(name);
                    }
                    adapterPeople.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                binding.tvPeopleSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (people.contains(String.valueOf(charSequence))) {
                            int index = people.indexOf(String.valueOf(charSequence));
                            viewModel.getUserID(searchPeapleResponse.getData().get(index).getId());
                             name=searchPeapleResponse.getData().get(index).getName();
                        } else {
                            Toast.makeText(LetsTalkActivity.this
                                    , "please choose right name", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });
        viewModel.getUserIDResponseMutableLiveData().observe(this, new Observer<UserIDResponse>() {
            @Override
            public void onChanged(UserIDResponse userIDResponse) {
                Intent intent = new Intent(LetsTalkActivity.this, ChatThreadActivity.class);
                intent.putExtra("peopleItem", userIDResponse.getId());
                intent.putExtra("people_name", name);
                binding.tvPeopleSearch.setText(null);
                startActivity(intent);
            }
        });
    }

    public void prepareTabsLayout() {
        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add(R.string.title_home, HomeChatFragment.class)
//                        .add(R.string.title_groups, GroupsFragment.class)
//                        .add(R.string.title_t_r, TRFragment.class)
                        .create());


        binding.viewpager.setAdapter(adapter);
        binding.viewpagertab.setViewPager(binding.viewpager);


        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.viewpager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}