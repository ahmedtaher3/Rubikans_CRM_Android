package com.devartlab.ui.main.ui.employeeservices.devartlink

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityDevartLinkBinding
import com.devartlab.model.CardModel
import com.devartlab.ui.main.ui.callmanagement.home.MenuListAdapter

class DevartLinkActivity : BaseActivity<ActivityDevartLinkBinding>(),
    MenuListAdapter.OnHomeItemClick {
    lateinit var binding: ActivityDevartLinkBinding
    lateinit var adapter: MenuListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Devart Link"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val list = ArrayList<CardModel>()

        list.add(CardModel(1, resources.getString(R.string.lets_talk), R.drawable.ic_message_outline))


        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.recycler?.layoutManager = layoutManager
        binding.recycler.adapter = adapter

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_devart_link
    }

    override fun setOnHomeItemClick(model: CardModel) {

        when (model.id) {
            1 -> {
                startActivity(Intent(this, DevartLinkWebView::class.java))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}