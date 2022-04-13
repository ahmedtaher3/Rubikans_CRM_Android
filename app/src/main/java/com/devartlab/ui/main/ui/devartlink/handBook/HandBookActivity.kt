package com.devartlab.ui.main.ui.devartlink.handBook

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityHandBookBinding
import com.devartlab.ui.main.ui.devartlink.handBook.model.Data
import android.view.WindowManager

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class HandBookActivity : AppCompatActivity() {
    lateinit var binding: ActivityHandBookBinding
    var viewModel: HandBookViewModel? = null
    private var adapter: SubjectsAdapter? = null
    private var adapter2: IndexHandBookAdapter? = null
    val list = ArrayList<Data>()
    val list2 = ArrayList<Data>()
    var recViewIndex: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_hand_book
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.faq)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = SubjectsAdapter(null)
        adapter2 = IndexHandBookAdapter(null)
        viewModel = ViewModelProvider(this).get(HandBookViewModel::class.java)
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
        viewModel!!.getHandBook()
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.tvIndexHandBook.setOnClickListener {
            showIndexsDialog()
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
                    binding.progressBar.setVisibility(View.GONE)
                }
                else -> {
                    //show data in recyclerView
                    list.addAll(it.data)
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

    private fun filter(text: String) {
        val filteredList: ArrayList<Data> = ArrayList()

        for (item in list) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter!!.filterData(filteredList)
    }

    private fun filter2(text: String) {
        val filteredList: ArrayList<Data> = ArrayList()

        for (item in list2) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter2!!.filterData(filteredList)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun showIndexsDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_index_hand_book)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        viewModel!!.getHandBook()
        val BtnCancel = dialog.findViewById<ImageView>(R.id.iv_cancel_dialog)
        recViewIndex = dialog.findViewById<RecyclerView>(R.id.recycler_list_subjects)
        val searchBarVideo = dialog.findViewById<EditText>(R.id.search_bar_video)
        searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter2(s.toString())
            }
        })
        viewModel!!.handBookResponse.observe(this, Observer {
            when {
                it!!.data.isEmpty() -> {
                    //errorMessage if data coming is null;
                    Toast.makeText(this, "list empty", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //show data in recyclerView
                    adapter2 = IndexHandBookAdapter(it.data)
                    recViewIndex!!.setAdapter(adapter2)
                    list2.addAll(it.data)
                    adapter2!!.setOnItemClickListener(IndexHandBookAdapter.OnItemClickListener {
                        binding.recyclerListSubjects.post {
                            binding.recyclerListSubjects.layoutManager?.scrollToPosition(it)
                        }
                        dialog.dismiss()
                    })
                }
            }
        })
        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}