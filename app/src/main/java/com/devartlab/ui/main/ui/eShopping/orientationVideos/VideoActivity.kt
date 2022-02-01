package com.devartlab.ui.main.ui.eShopping.orientationVideos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.devartlab.R
import com.devartlab.databinding.ActivityVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoBinding
    var _id: String? = null
    var _name: String? = null
    var _dec: String? = null
    var _name_channel: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_video)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.video)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
            binding.name.setText(_name)
        }
        if (intent.hasExtra("_dec")) {
            _dec = intent.getStringExtra("_dec")
            binding.dec.setText(_dec)
        }
        if (intent.hasExtra("_name_channel")) {
            _name_channel = intent.getStringExtra("_name_channel")
            binding.nameChannel.setText(_name_channel)
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