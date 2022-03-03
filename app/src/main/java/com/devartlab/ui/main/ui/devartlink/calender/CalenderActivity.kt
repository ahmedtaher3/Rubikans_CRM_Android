package com.devartlab.ui.main.ui.devartlink.calender

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.devartlab.R
import com.devartlab.databinding.ActivityCalenderBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate


class CalenderActivity : AppCompatActivity() {
    lateinit var binding:ActivityCalenderBinding
    private var selectedDate: LocalDate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calender)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.calender)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    // Show the month dates. Remember that views are recycled!
                    textView.visibility = View.VISIBLE
                    if (day.date == selectedDate) {
                        // If this is the selected date, show a round background and change the text color.
                        textView.setTextColor(Color.WHITE)
                        textView.setBackgroundResource(R.drawable.blue_background)
                    } else {
                        // If this is NOT the selected date, remove the background and reset the text color.
                        textView.setTextColor(Color.BLACK)
                        textView.background = null
                    }
                } else {
                    // Hide in and out dates
                    textView.visibility = View.INVISIBLE
                }
            }
        }


        binding.btnAddEvent.setOnClickListener {
            startActivity(Intent(this, AddEventActivity::class.java))
        }

        binding.btnHideShowEvent.setOnClickListener {
            if (binding.linearAndEvent.visibility == View.VISIBLE) {
                binding.linearAndEvent.setVisibility(View.GONE)
                binding.btnHideShowEvent.setImageResource(R.drawable.ic_show_event)
            } else {
                binding.linearAndEvent.setVisibility(View.VISIBLE)
                binding.btnHideShowEvent.setImageResource(R.drawable.ic_hide_event)
            }
        }
    }

    private fun handleObserver() {
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    class DayViewContainer(view: View) : ViewContainer(view) {
        val textView = view.findViewById<TextView>(R.id.calendarDayText)
        // Will be set when this container is bound
        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                // Use the CalendarDay associated with this container.

            }
        }
    }
}