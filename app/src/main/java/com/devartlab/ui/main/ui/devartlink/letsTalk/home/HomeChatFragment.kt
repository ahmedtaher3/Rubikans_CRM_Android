package com.chat.home

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.FragmentHomeChatBinding
import com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.ChatThreadActivity
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.HomeViewModel
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.PeopleListAdapter
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.mareSeen.MarkSeenRequest
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange


class HomeChatFragment : Fragment() {
    lateinit var binding: FragmentHomeChatBinding
    var viewModel: HomeViewModel? = null
    private var adapter: PeopleListAdapter? = null
    var forward: String? = null
    lateinit var markSeenRequest: MarkSeenRequest
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil
            .inflate(inflater!!, R.layout.fragment_home_chat, container, false)
        if (arguments != null) {
            forward = requireArguments().getString("forward")
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel!!.getListPeaple(UserPreferenceHelper.getUserChat().id)
        onClickListener()
        handleObserver()
//        pusher()
    }

    fun init() {
        adapter = PeopleListAdapter(null)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    fun onClickListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    fun pusher() {
        val options = PusherOptions()
        options.setCluster("ap4")

        val pusher = Pusher("cc31df9923d00ef5b9c9", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i(
                    "Pusher",
                    "State changed from ${change.previousState} to ${change.currentState}"
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

        val channel = pusher.subscribe("chatify")
        channel.bind("messaging") { event ->
            Log.i("Pusher", "Received event with data: $event")
            viewModel!!.getListPeaple(UserPreferenceHelper.getUserChat().id)
        }
        channel.bind("client-seen") { event ->
            Log.i("Pusher", "Received event with data: $event")
        }
    }

    fun handleObserver() {

        viewModel!!.getErrorMessage().observe(getViewLifecycleOwner(), Observer {
            if (it == 1) {
                Toast.makeText(context, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel!!.getPeapleResponseMutableLiveData()
            .observe(getViewLifecycleOwner(), Observer {
                if (it.data == null) {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.setVisibility(View.VISIBLE);
                } else {
                    //show data in recyclerView
                    binding.progressBar.setVisibility(View.GONE)
                    adapter = PeopleListAdapter(it.data)
                    binding.recHomePeople.setAdapter(adapter)
                    adapter!!.setOnItemClickListener(PeopleListAdapter.OnItemClickListener { pos, dataItem ->
                        markSeenRequest = MarkSeenRequest()
                        markSeenRequest.id = dataItem.id
                        markSeenRequest.toId = dataItem.id
                        markSeenRequest.userId = UserPreferenceHelper.getUserChat().id
                        viewModel!!.markSeen(markSeenRequest)
                        val intent = Intent(requireContext(), ChatThreadActivity::class.java)
                        intent.putExtra("people_id", dataItem.id)
                        intent.putExtra("people_name", dataItem.userapi.name)
                        if (forward != null) {
                            intent.putExtra("forward", forward)
                        }
                        startActivity(intent)
                    })
                }
            })
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getListPeaple(
                UserPreferenceHelper.getUserChat().id
            )
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }


}