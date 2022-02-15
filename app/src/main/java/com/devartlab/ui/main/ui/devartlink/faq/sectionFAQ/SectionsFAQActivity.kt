package com.devartlab.ui.main.ui.devartlink.faq.sectionFAQ
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
import com.devartlab.databinding.ActivitySectionsFaqBinding
import com.devartlab.ui.main.ui.devartlink.faq.FAQViewModel
import com.devartlab.ui.main.ui.devartlink.faq.model.section.Section

class SectionsFAQActivity : AppCompatActivity() {
    lateinit var binding:ActivitySectionsFaqBinding
    var viewModel: FAQViewModel? = null
    private var adapter: SectionsFAQAdapter? = null
    var _id: String? = null
    val list = ArrayList<Section>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_sections_faq)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = SectionsFAQAdapter(null)
        viewModel = ViewModelProvider(this).get(FAQViewModel::class.java)
        if (intent.hasExtra("_id")) {
            _id = intent.getStringExtra("_id")
            viewModel!!.getSectionsFAQ(_id!!)
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

        viewModel!!.sectionsResponse.observe(this, Observer {
            when {
                it!!.sections.isEmpty() -> {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.setVisibility(View.VISIBLE)
                }
                else -> {
                    //show data in recyclerView
                    supportActionBar!!.title = it.title
                    binding.progressBar.setVisibility(View.GONE)
                    adapter = SectionsFAQAdapter(it.sections)
                    list.addAll(it.sections)
                    binding.recyclerListVideos.setAdapter(adapter)
                }
            }
        })
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Section> = ArrayList()

        for (item in list) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter!!.filterData(filteredList)
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getSubsFAQ(_id!!)
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}