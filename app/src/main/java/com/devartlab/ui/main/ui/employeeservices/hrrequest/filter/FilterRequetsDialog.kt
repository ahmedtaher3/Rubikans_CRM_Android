package com.devartlab.ui.main.ui.employeeservices.hrrequest.filter

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.devartlab.R
import com.devartlab.ui.main.ui.employeeservices.hrrequest.RequestType
import com.devartlab.utils.CommonUtilities
import kotlinx.android.synthetic.main.filter_requests_dialog.*
import java.util.*
import kotlin.collections.ArrayList


class FilterRequetsDialog(private var activity: Context, private var onFilterSelect: OnFilterSelect) : Dialog(activity) {


    var typeName: String = ""
    var fromDateMS: Long = 0
    var toDateMS: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_requests_dialog)


        var list = ArrayList<String>()
        list.add(RequestType.أجازة.name)
        list.add(RequestType.أذن.name)
        list.add(RequestType.مأمورية.name)
        list.add(RequestType.وقت_إضافى.name)

        requestType.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))
        requestType.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            typeName = list[i]

        })


        fromDate.setOnClickListener(View.OnClickListener {
            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->


                        val text = year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString()
                        fromDateMS = CommonUtilities.convertToMillis(text)
                        fromDate.setText(text)

                        Toast.makeText(activity, fromDateMS.toString(), Toast.LENGTH_SHORT).show()


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        })

        close.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        toDate.setOnClickListener(View.OnClickListener {
            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val text = year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString()
                        toDateMS = CommonUtilities.convertToMillis(text)
                        toDate.setText(text)

                        Toast.makeText(activity, fromDateMS.toString(), Toast.LENGTH_SHORT).show()


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        })


        applyFilter.setOnClickListener(View.OnClickListener {

            onFilterSelect.setOnFilterSelect(typeName, fromDateMS, toDateMS)
        })

    }


}
