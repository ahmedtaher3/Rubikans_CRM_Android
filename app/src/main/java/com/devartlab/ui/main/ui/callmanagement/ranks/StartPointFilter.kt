package com.devartlab.ui.main.ui.callmanagement.ranks

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.StartPointReportTitle

class StartPointFilter(context: Context, private var myData: ArrayList<StartPointReportTitle>, private var startPointInterface: StartPointInterface)
    : Dialog(context) , StartPointFilterAdapter.OnTitleSelect {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: StartPointFilterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_title)



        recyclerView = findViewById(R.id.recyclerView)
        adapter = StartPointFilterAdapter(context , myData , this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    override fun setOnTitleSelect(model: StartPointReportTitle) {
dismiss()
        startPointInterface.onTitleSelect(model)
    }


}
