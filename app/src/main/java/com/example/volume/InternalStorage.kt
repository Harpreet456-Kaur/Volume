package com.example.volume

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.volume.databinding.ActivityInternalStorageBinding
import java.io.IOException


class InternalStorage : AppCompatActivity() {
    lateinit var binding: ActivityInternalStorageBinding
    lateinit var mediaPlayer: MediaPlayer

    private val PICK_AUDIO = 1
    var AudioUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternalStorageBinding.inflate(layoutInflater)

        binding.next.setOnClickListener {
            var intent = Intent(this,VideoPlayer::class.java)
            startActivity(intent)
        }

        binding.getItem.setOnClickListener(View.OnClickListener {
            val audio = Intent()
            audio.type = "audio/*"
            audio.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(Intent.createChooser(audio, "Select Audio"), PICK_AUDIO)
        })

        binding.stop.setOnClickListener(View.OnClickListener {
            // checking the media player
            // if the audio is playing or not.
            if (mediaPlayer.isPlaying) {
                // pausing the media player if media player
                // is playing we are calling below line to
                // stop our media player.
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()

                // below line is to display a message
                // when media player is paused.
                Toast.makeText(this, "Audio has been paused", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // this method is called when media
                // player is not playing.
                Toast.makeText(this, "Audio has not played", Toast.LENGTH_SHORT).show()
            }
        })

        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_AUDIO && resultCode == RESULT_OK) {
            AudioUri = data!!.data
            // Set the image in imageview for display
            binding.song.text = AudioUri.toString()
            playAudio(AudioUri)
        }
    }

    private fun playAudio(AudioUri: Uri?) {
        // initializing media player
        mediaPlayer = MediaPlayer()

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        // below line is use to set our
        // url to our media player.
        try {
                mediaPlayer.setDataSource(this,AudioUri!!)
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show()
    }
}