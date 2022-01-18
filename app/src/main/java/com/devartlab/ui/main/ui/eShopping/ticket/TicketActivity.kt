package com.devartlab.ui.main.ui.eShopping.ticket

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.a4eshopping.ticket.model.addTicket.AddTicketRequest
import com.devartlab.databinding.ActivityTicketBinding
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import java.lang.Exception

class TicketActivity : AppCompatActivity() {
    lateinit var binding: ActivityTicketBinding
    var viewModel: TicketViewModel? = null
    private var adapter: GetContactsAdapter? = null
    lateinit var request: AddTicketRequest
    var pusher: Pusher? = null
    var channel: Channel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_ticket)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.ticket)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
        pusher()
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getGetContacts("", "")
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.ivAddTicket.setOnClickListener {
            addTicketDialog()
        }
        binding.edFilter.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, binding.edFilter)
            popupMenu.menuInflater.inflate(R.menu.filter_popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_all -> {
                        binding.edFilter.setText(R.string.action_all)
                        viewModel!!.getGetContacts("0", "")
                    }
                    R.id.action_open -> {
                        binding.edFilter.setText(R.string.action_open)
                        viewModel!!.getGetContacts("1", "")
                    }
                    R.id.action_inreview -> {
                        binding.edFilter.setText(R.string.action_inreview)
                        viewModel!!.getGetContacts("2", "")
                    }
                    R.id.action_close -> {
                        binding.edFilter.setText(R.string.action_inreview)
                        viewModel!!.getGetContacts("3", "")
                    }
                }
                true
            })
            popupMenu.show()
        }

        binding.tvPeopleSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel!!.getGetContacts("",binding.tvPeopleSearch.text.toString())
            }
            override fun afterTextChanged(editable: Editable) {
            }
        })

    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.deleteTicketsResponse.observe(this, Observer {
            finish()
            startActivity(getIntent())
        })
        viewModel!!.getContactsResponse.observe(this, Observer {


            if (it!!.data == null) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
                binding.progressBar.setVisibility(View.GONE)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = GetContactsAdapter(it.data)
                binding.recHomePeople.setAdapter(adapter)
                adapter!!.setOnItemClickListener(GetContactsAdapter.OnItemClickListener { pos, dataItem ->
                    val intent = Intent(this, ChatThread4EShoppingActivity::class.java)
                    intent.putExtra("ticket_id", dataItem.id.toString())
                    intent.putExtra("status", dataItem.status.toString())
                    startActivity(intent)
                })

                adapter!!.setOnItemClickListener2(GetContactsAdapter.OnItemClickListener2 { pos, noOrder, status ->
                    if (status == "1") {
                        deleteMessagesDialog(noOrder)
                    }
                })
            }
        })
        viewModel!!.addTicketRsponse.observe(this, Observer {
            viewModel!!.getGetContacts("", "")
        })
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getGetContacts("", "")
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }


    private fun pusher() {
        val options = PusherOptions()
            .setCluster("ap4")
        pusher = Pusher("c2cd55b18d0d95d534e4", options)

        pusher!!.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i(
                    "Pusher", "State changed from ${change.previousState} to ${change.currentState}"
                )
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.i(
                    "Pusher",
                    "There was a problem connecting! code ($code), " + "message ($message), exception($e)"
                )
            }
        }, ConnectionState.ALL)
        channel = pusher!!.subscribe("chatify")
        channel!!.bind("statuschanged") { event ->
            Log.i("Pusher", "Received event with data: $event")
            viewModel!!.getGetContacts("", "")
        }
    }

    fun deleteMessagesDialog(id_messages: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_delete_message)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val BtnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val BtnSubmit = dialog.findViewById<Button>(R.id.btn_delete)

        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        BtnSubmit.setOnClickListener {
            viewModel!!.deleteTicket(id_messages)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun addTicketDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_add_ticket)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val BtnAddTicket = dialog.findViewById<Button>(R.id.btn_add_problem)
        val BtnCancel = dialog.findViewById<ImageView>(R.id.iv_cancel_dialog)
        val TvSelectProblem = dialog.findViewById<TextView>(R.id.ed_select_problems)
        val EdOther = dialog.findViewById<EditText>(R.id.ed_other)
        val EdTitleProblem = dialog.findViewById<EditText>(R.id.ed_title_problem)
        val EdMessageProblem = dialog.findViewById<EditText>(R.id.ed_message_problem)
        TvSelectProblem.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, TvSelectProblem)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_problem_register -> {
                        TvSelectProblem.setText(R.string.action_problem_register)
                        EdOther.setVisibility(View.GONE)
                    }
                    R.id.action_active_customer -> {
                        TvSelectProblem.setText(R.string.action_active_customer)
                        EdOther.setVisibility(View.GONE)
                    }
                    R.id.action_order_problem -> {
                        TvSelectProblem.setText(R.string.action_order_problem)
                        EdOther.setVisibility(View.VISIBLE)
                        if (TextUtils.isEmpty(EdOther.getText().toString())) {
                            EdOther.setError("please enter order number")
                        }
                    }
                    R.id.action_problem_old_ticket -> {
                        EdOther.setVisibility(View.GONE)
                        TvSelectProblem.setText(R.string.action_problem_old_ticket)
                    }
                    R.id.other -> {
                        EdOther.setVisibility(View.VISIBLE)
                        TvSelectProblem.setText(R.string.other)
                    }

                }
                true
            })
            popupMenu.show()
        }
        BtnAddTicket.setOnClickListener {
            if (TextUtils.isEmpty(TvSelectProblem.getText().toString())) {
                TvSelectProblem.setError("please select problem")
            } else if (TextUtils.isEmpty(EdTitleProblem.getText().toString())) {
                EdTitleProblem.setError("please enter title")
            } else if (TextUtils.isEmpty(EdMessageProblem.getText().toString())) {
                EdMessageProblem.setError("please enter message")
            } else {
                request = AddTicketRequest(
                    EdMessageProblem.text.toString(),
                    EdOther.text.toString(),
                    EdTitleProblem.text.toString(),
                    TvSelectProblem.text.toString()
                )
                viewModel!!.addTicket(request)
                dialog.dismiss()
            }
        }
        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}