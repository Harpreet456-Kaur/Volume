package com.example.volume

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.volume.databinding.ActivityMainBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var storage: FirebaseStorage
    var num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        storage = FirebaseStorage.getInstance()

        //image capture from camera
        binding.Button.setOnClickListener(View.OnClickListener { v: View? ->
            // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // Start the activity with camera_intent, and request pic id
            // ActivityResultLauncher callback
            startActivityForResult(cameraIntent, pic_id)
        })

        //go to next page
        binding.next.setOnClickListener {
            var intent = Intent(this, InternalStorage::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }

    //Increment/Decrement function call
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> vibrateUp()

            KeyEvent.KEYCODE_VOLUME_DOWN -> vibrateDown()
        }
        return true
    }

    //Increment
    fun vibrateUp() {
        binding.integer.text = num++.toString()

        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val vibrationEffect1: VibrationEffect

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibrationEffect1 =
                VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)

            // it is safe to cancel other vibrations currently taking place
            vibrator.cancel()
            vibrator.vibrate(vibrationEffect1)
        }
    }

    //Decrement
    fun vibrateDown() {
        binding.integer.text = num--.toString()

        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        val vibrationEffect1: VibrationEffect

        // this is the only type of the vibration which requires system version Oreo (API 26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibrationEffect1 =
                VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)

            vibrator.cancel()
            vibrator.vibrate(vibrationEffect1)
        }
    }

    companion object {
        // Define the pic id
        private const val pic_id = 123
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data!!.extras!!["data"] as Bitmap
            binding.image.setImageBitmap(imageBitmap)

            val fileName = "image_${System.currentTimeMillis()}.jpg"
            val storageRef = storage.reference.child(fileName)
            // Convert the Bitmap to a byte array
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            // Upload the image to Firebase Storage
            val uploadTask = storageRef.putBytes(data)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        Log.d("MainActivity", "Download URL: $downloadUrl")

                        // Use the downloadUrl with Glide to load and display the image
                        Glide.with(this)
                            .load(downloadUrl)
                            .into(binding.image)

                        Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT)
                            .show()
                        // You can save the downloadUrl or use it to display the image later
                    }
                }
            }
        }
    }
}