package com.devartlab.ui.main.ui.eshopping.orientationVideos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.a4eshopping.orientationVideos.VideosViewModel
import com.devartlab.a4eshopping.orientationVideos.model.Item
import com.devartlab.a4eshopping.orientationVideos.videoActivity
import com.devartlab.databinding.ActivityOrientationVideosBinding
import com.devartlab.ui.main.ui.eShopping.orientationVideos.VideoListAdapter


class OrientationVideosActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrientationVideosBinding
    var viewModel: VideosViewModel? = null
    private var adapter: VideoListAdapter? = null
    val list = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_orientation_videos)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.orientation_videos)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = VideoListAdapter(null)
        viewModel = ViewModelProvider(this).get(VideosViewModel::class.java)
        onClickListener()
        handleObserver()
        binding.searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private fun onClickListener() {
        viewModel!!.getVideos()
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }

    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.responseVideos.observe(this, Observer {
            if (it!!.items == null) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = VideoListAdapter(it.items)
                list.addAll(it.items)
                binding.recyclerListVideos.setAdapter(adapter)
                adapter!!.setOnItemClickListener(VideoListAdapter.OnItemClickListener { pos, dataItem ->

                    val intent = Intent(this, videoActivity::class.java)
                    intent.putExtra("_id", dataItem.snippet.resourceId.videoId)
                    intent.putExtra("_name", dataItem.snippet.title)
                    intent.putExtra("_dec", dataItem.snippet.description)
                    intent.putExtra("_name_channel", dataItem.snippet.channelTitle)
                    startActivity(intent)
                })
            }
        })
    }


    private fun filter(text: String) {
        val filteredList: ArrayList<Item> = ArrayList()

        for (item in list) {
            if (item.snippet.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
                Log.e("xxx",item.toString())
            }
        }
        adapter!!.filterData(filteredList)
    }


    private fun refresh() {
        synchronized(this) {
            viewModel!!.getVideos()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}