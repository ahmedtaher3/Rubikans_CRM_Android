package com.devartlab.ui.main.ui.devartlink.devartCommunity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.ActivityDevartCommunityBinding
import com.devartlab.ui.main.ui.devartlink.devartCommunity.model.Youtube

class DevartCommunityActivity : AppCompatActivity() {
    lateinit var binding: ActivityDevartCommunityBinding
    var viewModel: DevartCommunityViewModel? = null
    var _id: String? = null
    private var adapter2: DevartCommunityAdapter? = null
    private var adapter: DevartCommunitySubAdapter? = null
    val list = ArrayList<Youtube>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_devart_community
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter2 = DevartCommunityAdapter(null)
        adapter = DevartCommunitySubAdapter(null)
        viewModel = ViewModelProvider(this).get(DevartCommunityViewModel::class.java)
        if (intent.hasExtra("_id")) {
            _id = intent.getStringExtra("_id")
            viewModel!!.getDevartCommunity(_id!!)
        } else {
            viewModel!!.getDevartCommunity("0")
        }
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.btnHideShowAds.setOnClickListener {
            if (binding.constrAds.visibility == View.VISIBLE) {
                binding.constrAds.setVisibility(View.GONE)
                binding.btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
            } else {
                binding.constrAds.setVisibility(View.VISIBLE)
                binding.btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
            }
        }
    }

    private fun handleObserver() {
        viewModel!!.errorMessage.observe(this, Observer { integer: Int ->
            if (integer == 1) {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel!!.devartCommunityResponse.observe(this, Observer {
            supportActionBar!!.title = it!!.name
            Glide.with(this)
                .load("https://devartlink.devartlab.com/assets/images/" + it!!.image)
                .fitCenter().into(binding.imageView)
            when {
                it.sub.isNotEmpty() -> {
                    //show data in recyclerView
                    binding.recyclerListTeams.setVisibility(View.VISIBLE)
                    binding.decTeam.setVisibility(View.VISIBLE)
                    binding.decTeam.loadDataWithBaseURL(
                        null, it.description, "text/html", "utf-8", null)
                    binding.progressBar.setVisibility(View.GONE)
                    adapter = DevartCommunitySubAdapter(it.sub)
                    binding.recyclerListTeams.setAdapter(adapter)
                    adapter!!.setOnItemClickListener(DevartCommunitySubAdapter.OnItemClickListener { pos, dataItem ->
                        val intent = Intent(this, DevartCommunityActivity::class.java)
                        intent.putExtra("_id", dataItem._id)
                        startActivity(intent)
                    })
                }
                it.youtube.isNotEmpty() -> {
                    //show data in recyclerView
                    binding.recyclerListVideos.setVisibility(View.VISIBLE)
                    binding.searchBarVideo.setVisibility(View.VISIBLE)
                    binding.progressBar.setVisibility(View.GONE)
                    adapter2 = DevartCommunityAdapter(it.youtube)
                    list.addAll(it.youtube)
                    binding.recyclerListVideos.setAdapter(adapter2)
                    adapter2!!.setOnItemClickListener(DevartCommunityAdapter.OnItemClickListener { pos, dataItem ->
                        val intent = Intent(this, DevartCommunityVideoActivity::class.java)
                        intent.putExtra("_id", dataItem.video_id)
                        intent.putExtra("_name", dataItem.title)
                        intent.putExtra("_dec", dataItem.description)
                        intent.putExtra("_name_channel", dataItem.sub_title)
                        startActivity(intent)
                    })
                }
                else -> {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.setVisibility(View.VISIBLE)
                }
            }
        })
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Youtube> = ArrayList()

        for (item in list) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter2!!.filterData(filteredList)
    }

    private fun refresh() {
        synchronized(this) {
            if (_id==null){
                viewModel!!.getDevartCommunity(_id!!)
            }else{
                viewModel!!.getDevartCommunity("0")
            }
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}