package com.devartlab.ui.main.ui.devartlink.handBook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityHandBookBinding

class HandBookActivity : AppCompatActivity() {
    lateinit var binding: ActivityHandBookBinding
    var viewModel: HandBookViewModel? = null
    private var adapter: SubjectsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_hand_book)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.faq)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = SubjectsAdapter(null)
        viewModel = ViewModelProvider(this).get(HandBookViewModel::class.java)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getHandBook()
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
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

        viewModel!!.handBookResponse.observe(this, Observer {
            when {
                it!!.data.isEmpty() -> {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.setVisibility(View.VISIBLE)
                }
                else -> {
                    //show data in recyclerView
                    binding.progressBar.setVisibility(View.GONE)
                    adapter = SubjectsAdapter(it.data)
                    binding.recyclerListSubjects.setAdapter(adapter)
                }
            }
        })
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getHandBook()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}