package com.devartlab.ui.main.ui.devartlink.handBook

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityHandBookBinding
import android.view.WindowManager

import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.base.BaseActivity
import com.iamkamrul.expandablerecyclerviewlist.listener.ExpandCollapseListener
import java.util.*
import kotlin.collections.ArrayList

class HandBookActivity : BaseActivity<ActivityHandBookBinding>() {
    lateinit var binding: ActivityHandBookBinding
    var viewModel: HandBookViewModel? = null
    private val adapter = HandBookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProvider(this)[HandBookViewModel::class.java]
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.faq)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        adapter.setExpandCollapseListener(object : ExpandCollapseListener {
            override fun onListItemExpanded(position: Int) {
            }

            override fun onListItemCollapsed(position: Int) {

            }

        })
        binding.recycler.adapter = adapter


        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {


            }
        })
        viewModel!!.getHandBook()
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.tvIndexHandBook.setOnClickListener {
           // showIndexsDialog()
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

        viewModel!!.handBookResponse.observe(this) {
            when {
                it!!.data!!.isEmpty() -> {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                else -> {

                    binding.progressBar.visibility = View.GONE
                    adapter.setExpandableParentItemList(it.data!!)
                 }
            }
        }
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getHandBook()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = View.VISIBLE
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

   /* private fun showIndexsDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_index_hand_book)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        viewModel!!.getHandBook()
        val BtnCancel = dialog.findViewById<ImageView>(R.id.iv_cancel_dialog)
        recViewIndex = dialog.findViewById(R.id.recycler_list_subjects)
        val searchBarVideo = dialog.findViewById<EditText>(R.id.search_bar_video)
        searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter2(s.toString())
            }
        })
        viewModel!!.handBookResponse.observe(this) {
            when {
                it!!.data.isEmpty() -> {
                    //errorMessage if data coming is null;
                    Toast.makeText(this, "list empty", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //show data in recyclerView
                    adapter2 = IndexHandBookAdapter(it.data)
                    recViewIndex!!.adapter = adapter2
                    list2.addAll(it.data)
                    adapter2!!.setOnItemClickListener {
                        binding.recyclerListSubjects.post {
                            binding.recyclerListSubjects.layoutManager?.scrollToPosition(it)
                        }
                        dialog.dismiss()
                    }
                }
            }
        }
        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }*/

    override fun getLayoutId(): Int {
        return R.layout.activity_hand_book
    }

}