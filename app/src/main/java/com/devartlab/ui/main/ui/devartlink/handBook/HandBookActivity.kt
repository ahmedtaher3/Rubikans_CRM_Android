package com.devartlab.ui.main.ui.devartlink.handBook

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityHandBookBinding
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookItem
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookSubs
import com.iamkamrul.expandablerecyclerviewlist.listener.ExpandCollapseListener

class HandBookActivity : BaseActivity<ActivityHandBookBinding>() , HandBookAdapter.OnItemSelect {
    lateinit var binding: ActivityHandBookBinding
    var viewModel: HandBookViewModel? = null
    private val adapter = HandBookAdapter(this)
    lateinit var fullList: ArrayList<HandBookItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProvider(this)[HandBookViewModel::class.java]
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.faq)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding.index.setOnClickListener {
            if (fullList.isNullOrEmpty())
                return@setOnClickListener
            replace_fragment(BookFragment(fullList), "BookFragment")

        }

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
                    fullList = it.data!!
                    binding.progressBar.visibility = View.GONE
                    adapter.setExpandableParentItemList(fullList)
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


    override fun getLayoutId(): Int {
        return R.layout.activity_hand_book
    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
            )
            .add(
                R.id.container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }

    override fun setOnItemSelect(model: HandBookSubs) {

        replace_fragment(BookFragment(fullList , model.title!!), "BookFragment")

    }

}