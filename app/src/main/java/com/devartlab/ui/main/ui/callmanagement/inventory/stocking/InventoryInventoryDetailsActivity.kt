package com.devartlab.ui.trade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.VanStoctaking
import com.devartlab.ui.main.ui.callmanagement.inventory.movements.StockingActivity.Companion.da

class InventoryInventoryDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_inventory_details)

        val recycler_view: RecyclerView = findViewById(R.id.recyclerInventoryInventoryDetails)




        recycler_view.adapter = InventoryInventoryDetailsAdapter(this, da as ArrayList<VanStoctaking>)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)


    }
}