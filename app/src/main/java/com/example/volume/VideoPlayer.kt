package com.example.volume

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.example.volume.databinding.ActivityVideoPlayerBinding

class VideoPlayer : AppCompatActivity() {
    lateinit var binding: ActivityVideoPlayerBinding

    private val PICK_VIDEO = 1
    var VideoUri: Uri? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)

        binding.playVideo.setOnClickListener(View.OnClickListener {
            val video = Intent()
            video.type = "video/*"
            video.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(Intent.createChooser(video, "Select Video"), PICK_VIDEO)
        })

        //landscape
        binding.landscape.setOnClickListener(View.OnClickListener {
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            else
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        })

        //Next
        val intent = Intent(this,Animations::class.java)
        startActivity(intent)

        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK) {
            VideoUri = data!!.data
            //Set the image in imageview for display
            //binding.song.text = AudioUri.toString()
            playVideo(VideoUri)
        }
    }

    private fun playVideo(VideoUri: Uri?) {

        // on below line we are setting
        // video uri for our video view.
        binding.videoView.setVideoURI(VideoUri)

        // on below line we are creating variable
        // for media controller and initializing it.
        val mediaController = MediaController(this)

        // on below line we are setting anchor
        // view for our media controller.
        mediaController.setAnchorView(binding.videoView)

        // on below line we are setting media player
        // for our media controller.
        mediaController.setMediaPlayer(binding.videoView)

        // on below line we are setting media
        // controller for our video view.
        binding.videoView.setMediaController(mediaController)

        // on below line we are
        // simply starting our video view.
        binding.videoView.start()
    }
}