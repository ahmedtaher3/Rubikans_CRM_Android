package com.devartlab.ui.main.ui.eShopping.report

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.devartlab.R
import com.devartlab.databinding.ActivityReport4eShoppingBinding
import android.widget.Toast

import com.archit.calendardaterangepicker.customviews.CalendarListener
import java.text.SimpleDateFormat
import java.util.*
import com.github.mikephil.charting.animation.Easing

import com.github.mikephil.charting.formatter.PercentFormatter

import com.github.mikephil.charting.data.PieData

import com.github.mikephil.charting.data.PieDataSet

import com.github.mikephil.charting.utils.ColorTemplate

import com.github.mikephil.charting.data.PieEntry

import com.github.mikephil.charting.components.Legend
import android.widget.LinearLayout
import com.devartlab.ui.main.ui.eShopping.report.MyHolder.IconTreeItem
import com.devartlab.ui.main.ui.eShopping.report.atv.model.TreeNode

import com.devartlab.ui.main.ui.eShopping.report.atv.view.AndroidTreeView




class Report4eShoppingActivity : AppCompatActivity() {
    lateinit var binding: ActivityReport4eShoppingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report4e_shopping)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.report)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        setupOrderStatusValue()
        loadOrderStatusValueData()
        binding.tvFromToDate.setOnClickListener {
            selectDateDialog(binding.tvFromToDate)
            binding.ivRemoveSearch.setVisibility(View.VISIBLE)
        }

        binding.ivRemoveSearch.setOnClickListener {
            binding.tvFromToDate.setHint(R.string.from_to_date)
        }
        binding.ivTree.setOnClickListener {
            binding.cardParent.setVisibility(View.VISIBLE)
        }

        //Root
        //Root
        val root: TreeNode = TreeNode.root()

        //Parent
        //Parent
        val nodeItem = IconTreeItem(R.drawable.ic_arrow_drop_down, "Parent")
        val parent: TreeNode = TreeNode(nodeItem).setViewHolder(
            MyHolder(applicationContext, true, MyHolder.DEFAULT, MyHolder.DEFAULT))

        //Child
        //Child
        val childItem = IconTreeItem(R.drawable.ic_folder, "Child")
        val child: TreeNode = TreeNode(childItem).setViewHolder(
            MyHolder(applicationContext, false, R.layout.child, 25))

        //Sub Child
        //Sub Child
        val subChildItem = IconTreeItem(R.drawable.ic_folder, "Sub Child")
        val subChild: TreeNode = TreeNode(subChildItem).setViewHolder(
            MyHolder(applicationContext, false, R.layout.child, 50))

        //Add sub child.
        //Add sub child.
        child.addChild(subChild)


        //Add child.
        //Add child.
        parent.addChildren(child)
        root.addChild(parent)

        //Add AndroidTreeView into view.

        //Add AndroidTreeView into view.
        val tView = AndroidTreeView(applicationContext, root)
        (findViewById<View>(R.id.ll_parent) as LinearLayout).addView(tView.view)
    }

    private fun handleObserver() {
    }

    fun selectDateDialog(cal:TextView) {
        var  z:String? =null
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_from_to_date)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val BtnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val BtnSubmit = dialog.findViewById<Button>(R.id.btn_delete)
        val calendar = dialog.findViewById<DateRangeCalendarView>(R.id.calendar)
        calendar.setCalendarListener(object : CalendarListener {
            override fun onFirstDateSelected(startDate: Calendar) {}
            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                val sYear: Int = startDate.get(Calendar.YEAR)
                val sMonth: Int = startDate.get(Calendar.MONTH)
                val sDay: Int = startDate.get(Calendar.DAY_OF_MONTH)
                startDate.set(sYear, sMonth, sDay)
                val sFormatter = SimpleDateFormat("dd/MM/yyyy")
                val sDate: String = sFormatter.format(startDate.getTime())
                val eYear: Int = endDate.get(Calendar.YEAR)
                val eMonth: Int = endDate.get(Calendar.MONTH)
                val eDay: Int = endDate.get(Calendar.DAY_OF_MONTH)
                endDate.set(eYear, eMonth, eDay)
                val eFormatter = SimpleDateFormat("dd/MM/yyyy")
                val eDate: String = eFormatter.format(endDate.getTime())
                z = sDate+" - "+eDate
            }
        })
        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        BtnSubmit.setOnClickListener {
            if (z == null) {
                Toast.makeText(this@Report4eShoppingActivity, "choose from to date ", Toast.LENGTH_SHORT).show()
            } else {
                cal.setText(z)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun setupOrderStatusValue() {
        binding.activityMainPiechart.setDrawHoleEnabled(true)
        binding.activityMainPiechart.setUsePercentValues(true)
        binding.activityMainPiechart.setEntryLabelTextSize(12F)
        binding.activityMainPiechart.setEntryLabelColor(Color.BLACK)
        binding.activityMainPiechart.setCenterText("Order Status & Values")
        binding.activityMainPiechart.setCenterTextSize(18F)
        binding.activityMainPiechart.getDescription().setEnabled(false)
        val l: Legend = binding.activityMainPiechart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadOrderStatusValueData() {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(0.70f, "Completed"))
        entries.add(PieEntry(0.20f, "In process"))
        entries.add(PieEntry(0.10f, "Cancel"))
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "for all orders in 2020-2021")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(binding.activityMainPiechart))
        data.setValueTextSize(8f)
        data.setValueTextColor(Color.BLACK)
        binding.activityMainPiechart.setData(data)
        binding.activityMainPiechart.invalidate()
        binding.activityMainPiechart.animateY(1400, Easing.EaseInOutQuad)
    }
}