package com.devartlab.ui.main.ui.employeeservices.attendance.daydetails

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.EMployeeDayDetails_class
import java.util.ArrayList

class DayDetailsDailog(context: Context, private val list: ArrayList<EMployeeDayDetails_class>) : Dialog(context) {

    lateinit var recyclerView: RecyclerView
    lateinit var close: ImageView
    lateinit var adapter: DayDetailsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.day_details_employee)

        adapter = DayDetailsAdapter(context)
        recyclerView = findViewById(R.id.recyclerView)
        close = findViewById(R.id.close)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.setMyData(list)
        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })

    }

}
