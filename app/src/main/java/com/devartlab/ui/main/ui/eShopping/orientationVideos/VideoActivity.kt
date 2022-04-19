package com.devartlab.ui.main.ui.eShopping.orientationVideos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.ActivityVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class VideoActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoBinding
    var viewModel: VideosViewModel? = null
    lateinit var dataManager: DataManager
    var _id: String? = null
    private var _name: String? = null
    private var _dec: String? = null
    private var _name_channel: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_video)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.video)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this)[VideosViewModel::class.java]
        dataManager = (application as BaseApplication).dataManager!!
        if (intent.hasExtra("_id")) {
            _id = intent.getStringExtra("_id")
            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(_id!!, 0f)
                }
            })
        }
        if (intent.hasExtra("_name")) {
            _name = intent.getStringExtra("_name")
            binding.name.text = _name
        }
        if (intent.hasExtra("_dec")) {
            _dec = intent.getStringExtra("_dec")
            binding.dec.text = _dec
        }
        if (intent.hasExtra("_name_channel")) {
            _name_channel = intent.getStringExtra("_name_channel")
            binding.nameChannel.text = _name_channel
        }
        binding.ivShare.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            val shareBody = "$_name $_dec $_name_channel https://youtu.be/$_id"
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sendIntent, "Share video $_name"))
        }
    }

    override fun onStop() {
        super.onStop()
        binding.youtubePlayerView.enableBackgroundPlayback(false)
    }

    override fun onPause() {
        super.onPause()
        binding.youtubePlayerView.enableBackgroundPlayback(false)
    }
    override fun onSupportNavigateUp(): Boolean {
        binding.youtubePlayerView.enableBackgroundPlayback(false)
        finish()
        return true
    }
}