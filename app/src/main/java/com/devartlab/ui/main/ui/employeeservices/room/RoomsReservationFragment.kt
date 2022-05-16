package com.devartlab.ui.main.ui.employeeservices.room

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentRoomsReservationBinding
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RoomsReservationFragment : BaseFragment<FragmentRoomsReservationBinding>() {

    lateinit var binding: FragmentRoomsReservationBinding
    lateinit var viewModel: RoomsReservationViewModel
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var listIds: ArrayList<Int>
    var roomId = 0

    var time9 = false
    var time930 = false
    var time10 = false
    var time1030 = false
    var time11 = false
    var time1130 = false
    var time12 = false
    var time1230 = false
    var time1 = false
    var time130 = false
    var time2 = false
    var time230 = false
    var time3 = false
    var time330 = false
    var time4 = false
    var time430 = false
    var time5 = false
    var time530 = false
    var time6 = false
    var time630 = false
    var selectedDate = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_rooms_reservation
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!

        binding.date.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

                selectedDate = simpleDateFormat.format(calendar.time)
                binding.date.setText(selectedDate)


                viewModel.reservations("Rooms Reservation", "getAllRoomReservations", "", selectedDate, "", roomId.toString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")


            }
            DatePickerDialog(baseActivity, dateSetListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        })

        binding.submitRequest.setOnClickListener(View.OnClickListener {


            viewModel.reservations("Rooms Reservation", "insertReservations", viewModel.dataManager?.user?.empId.toString(), selectedDate, viewModel.dataManager?.user?.nameAr, roomId.toString(), "", time9.toString(), time930.toString(), time10.toString(), time1030.toString(), time11.toString(), time1130.toString(), time12.toString(), time1230.toString(), time1.toString(), time130.toString(), time2.toString(), time230.toString(), time3.toString(), time330.toString(), time4.toString(), time430.toString(), time5.toString(), time530.toString(), time6.toString(), time630.toString(), "")


        })




        binding.spinnerType.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                roomId = listIds[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })


        binding.time9.setOnClickListener {

            if (time9) {
                time9 = false
                binding.time9.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time9 = true
                binding.time9.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }


        }
        binding.time930.setOnClickListener {


            if (time930) {

                time930 = false
                binding.time930.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {

                time930 = true
                binding.time930.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }


        }
        binding.time10.setOnClickListener {


            if (time10) {


                time10 = false
                binding.time10.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {


                time10 = true
                binding.time10.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time1030.setOnClickListener {

            if (time1030) {
                time1030 = false
                binding.time1030.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time1030 = true
                binding.time1030.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time11.setOnClickListener {

            if (time11) {
                time11 = false
                binding.time11.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time11 = true
                binding.time11.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time1130.setOnClickListener {

            if (time1130) {
                time1130 = false
                binding.time1130.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time1130 = true
                binding.time1130.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time12.setOnClickListener {

            if (time12) {
                time12 = false
                binding.time12.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time12 = true
                binding.time12.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time1230.setOnClickListener {

            if (time1230) {
                time1230 = false
                binding.time1230.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time1230 = true
                binding.time1230.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time1.setOnClickListener {

            if (time1) {
                time1 = false
                binding.time1.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time1 = true
                binding.time1.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time130.setOnClickListener {

            if (time130) {
                time130 = false
                binding.time130.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time130 = true
                binding.time130.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time2.setOnClickListener {

            if (time2) {
                time2 = false
                binding.time2.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time2 = true
                binding.time2.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time230.setOnClickListener {

            if (time230) {
                time230 = false
                binding.time230.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time230 = true
                binding.time230.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time3.setOnClickListener {

            if (time3) {
                time3 = false
                binding.time3.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time3 = true
                binding.time3.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time330.setOnClickListener {

            if (time330) {
                time330 = false
                binding.time330.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time330 = true
                binding.time330.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time4.setOnClickListener {

            if (time4) {
                time4 = false
                binding.time4.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time4 = true
                binding.time4.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time430.setOnClickListener {

            if (time430) {
                time430 = false
                binding.time430.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time430 = true
                binding.time430.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time5.setOnClickListener {

            if (time5) {
                time5 = false
                binding.time5.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time5 = true
                binding.time5.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time530.setOnClickListener {

            if (time530) {
                time530 = false
                binding.time530.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time530 = true
                binding.time530.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time6.setOnClickListener {

            if (time6) {
                time6 = false
                binding.time6.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time6 = true
                binding.time6.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }
        binding.time630.setOnClickListener {

            if (time630) {
                time630 = true
                binding.time630.setCardBackgroundColor(baseActivity.resources.getColor(R.color.white))
            } else {
                time630 = true
                binding.time630.setCardBackgroundColor(baseActivity.resources.getColor(R.color.green))
            }

        }

        setObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RoomsReservationViewModel::class.java)
        viewModel.rooms("Room", "getAllRooms", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        listIds = ArrayList<Int>()

    }

    private fun setObservers() {
        viewModel.responseLive.observe(requireActivity(), Observer {

            val list = ArrayList<String>()

            for (m in it.rooms!!) {
                list.add(m.name)
                listIds.add(m.id)
            }

            binding.spinnerType.adapter = ArrayAdapter<String>(baseActivity, android.R.layout.simple_dropdown_item_1line, list)


        })


        viewModel.reservationLive.observe(requireActivity(), Observer {


            for (model in it.reservations!!) {

                if (model.get900()) {

                    binding.time9.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time9.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get930()) {

                    binding.time930.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time930.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get1000()) {

                    binding.time10.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time10.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get1030()) {

                    binding.time1030.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time1030.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get1100()) {

                    binding.time11.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time11.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get1130()) {

                    binding.time1130.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time1130.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get1200()) {

                    binding.time12.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time12.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get1230()) {

                    binding.time1230.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time1230.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get100()) {

                    binding.time1.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time1.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get130()) {

                    binding.time130.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time130.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get200()) {

                    binding.time2.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time2.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get230()) {

                    binding.time230.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time230.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get300()) {

                    binding.time3.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time3.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get330()) {

                    binding.time330.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time330.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get400()) {

                    binding.time4.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time4.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get430()) {

                    binding.time430.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time430.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get500()) {

                    binding.time5.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time5.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get530()) {

                    binding.time530.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time530.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get600()) {

                    binding.time6.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time6.isEnabled = false
                }
            }


            for (model in it.reservations!!) {

                if (model.get630()) {

                    binding.time630.setCardBackgroundColor(baseActivity.resources.getColor(R.color.grey))
                    binding.time630.isEnabled = false
                }
            }


        })



        viewModel.progress.observe(requireActivity(), Observer {

            when (it) {
                0 -> {
                    ProgressLoading.dismiss()
                }

                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })


    }


}