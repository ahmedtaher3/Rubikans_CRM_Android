package com.devartlab.ui.main.ui.devartlink.faq.subFAQ

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivitySubFaqBinding
import com.devartlab.ui.main.ui.devartlink.faq.FAQViewModel
import com.devartlab.ui.main.ui.devartlink.faq.model.sub.Sub
import com.devartlab.ui.main.ui.devartlink.faq.sectionFAQ.SectionsFAQActivity

class SubFAQActivity : AppCompatActivity() {
    lateinit var binding:ActivitySubFaqBinding
    var viewModel: FAQViewModel? = null
    private var adapter: SubsFAQAdapter? = null
    var _id: String? = null
    val list = ArrayList<Sub>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_sub_faq)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = SubsFAQAdapter(null)
        viewModel = ViewModelProvider(this).get(FAQViewModel::class.java)
        if (intent.hasExtra("_id")) {
            _id = intent.getStringExtra("_id")
            viewModel!!.getSubsFAQ(_id!!)
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
        viewModel!!.errorMessage.observe(this, Observer { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel!!.subsFaqResponse.observe(this, Observer {
            when {
                it!!.subs.isEmpty() -> {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.setVisibility(View.VISIBLE)
                }
                else -> {
                    //show data in recyclerView
                    supportActionBar!!.title = it.title
                    binding.progressBar.setVisibility(View.GONE)
                    adapter = SubsFAQAdapter(it.subs)
                    list.addAll(it.subs)
                    binding.recyclerListVideos.setAdapter(adapter)
                    adapter!!.setOnItemClickListener(SubsFAQAdapter.OnItemClickListener { pos, dataItem ->
                        val intent = Intent(this,SectionsFAQActivity::class.java)
                        intent.putExtra("_id", dataItem._id)
                        startActivity(intent)
                    })
                }
            }
        })
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Sub> = ArrayList()

        for (item in list) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter!!.filterData(filteredList)
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getSectionsFAQ(_id!!)
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}