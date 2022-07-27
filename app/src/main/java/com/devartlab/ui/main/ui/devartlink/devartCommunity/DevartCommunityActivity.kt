package com.devartlab.ui.main.ui.devartlink.devartCommunity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.databinding.ActivityDevartCommunityBinding
import com.devartlab.ui.main.ui.devartlink.devartCommunity.model.Youtube
import java.util.*
import kotlin.collections.ArrayList

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
        viewModel = ViewModelProvider(this)[DevartCommunityViewModel::class.java]
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

    }

    private fun handleObserver() {
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel!!.devartCommunityResponse.observe(this) {
            try {
                supportActionBar!!.title = it!!.name

                when {
                    it.sub.isNotEmpty() -> {
                        //show data in recyclerView
                        binding.recyclerListTeams.visibility = View.VISIBLE
                        binding.decTeam.visibility = View.VISIBLE
                        binding.decTeam.loadDataWithBaseURL(
                            null, it.description, "text/html", "utf-8", null
                        )
                        binding.progressBar.visibility = View.GONE
                        adapter = DevartCommunitySubAdapter(it.sub)
                        binding.recyclerListTeams.adapter = adapter
                        adapter!!.setOnItemClickListener { _, dataItem ->
                            val intent = Intent(this, DevartCommunityActivity::class.java)
                            intent.putExtra("_id", dataItem._id)
                            startActivity(intent)
                        }
                    }
                    it.youtube.isNotEmpty() -> {
                        //show data in recyclerView
                        binding.recyclerListVideos.visibility = View.VISIBLE
                        binding.searchBarVideo.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        adapter2 = DevartCommunityAdapter(it.youtube)
                        list.addAll(it.youtube)
                        binding.recyclerListVideos.adapter = adapter2
                        adapter2!!.setOnItemClickListener { _, dataItem ->
                            val intent = Intent(this, DevartCommunityVideoActivity::class.java)
                            intent.putExtra("_id", dataItem.video_id)
                            intent.putExtra("_name", dataItem.title)
                            intent.putExtra("_dec", dataItem.description)
                            intent.putExtra("_name_channel", dataItem.sub_title)
                            startActivity(intent)
                        }
                    }
                    else -> {
                        //errorMessage if data coming is null;
                        binding.tvEmptyList.visibility = View.VISIBLE
                    }
                }
            }catch (e:Exception){
                binding.progressBar.visibility = View.GONE
                binding.constrAds.visibility = View.GONE
                 supportActionBar!!.title = getString(R.string.community)
                Toast.makeText(this, "Data is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Youtube> = ArrayList()

        for (item in list) {
            if (item.title.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
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
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}