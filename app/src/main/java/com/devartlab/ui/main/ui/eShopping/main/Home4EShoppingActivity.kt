package com.devartlab.ui.main.ui.a4eshopping.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.a4eshopping.addProductsToThePharmacy.AddProductsPharmacyActivity
import com.devartlab.a4eshopping.main.model.CardModel
import com.devartlab.databinding.Activity4eshoppingBinding
import com.devartlab.ui.main.ui.eShopping.PharmacyBinding.PharmacyBindingActivity
import com.devartlab.ui.main.ui.eShopping.pharmacySales.PharmacySalesActivity
import com.devartlab.ui.main.ui.eShopping.ticket.TicketActivity
import com.devartlab.ui.main.ui.eshopping.main.MenuListAdapter
import com.devartlab.ui.main.ui.eshopping.orientationVideos.OrientationVideosActivity

class Home4EShoppingActivity : AppCompatActivity() ,
    MenuListAdapter.OnHomeItemClick{

    lateinit var binding: Activity4eshoppingBinding
    lateinit var adapter: MenuListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_4eshopping)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.eshopping)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        val list = ArrayList<CardModel>()
        list.add(CardModel(1, resources.getString(R.string.orientation_videos), "",R.drawable.ic_video))
        list.add(CardModel(2, resources.getString(R.string.Pharmacy_binding), "", R.drawable.ic_pharmacy_binding))
        list.add(CardModel(3, resources.getString(R.string.Pharmacy_sales), "", R.drawable.pharmacy_sales))
        list.add(CardModel(4, resources.getString(R.string.Add_products_to_Pharmacy), "", R.drawable.ic_add_pharmacy))
        list.add(CardModel(5, resources.getString(R.string.Add_ticket), "", R.drawable.ic_chat))

        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter

    }

    override fun setOnHomeItemClick(model: CardModel) {
        when (model.id) {
            1 -> {
                val intent = Intent(this, OrientationVideosActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(this, PharmacyBindingActivity::class.java)
                startActivity(intent)
            }
            3 -> {
                    val intent = Intent(this, PharmacySalesActivity::class.java)
                    startActivity(intent)
            }
            4 -> {
                val intent = Intent(this, AddProductsPharmacyActivity::class.java)
                startActivity(intent)
            }
            5 -> {
                val intent = Intent(this, TicketActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}