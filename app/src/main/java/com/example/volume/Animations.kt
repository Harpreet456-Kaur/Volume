package com.example.volume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import com.example.volume.databinding.ActivityAnimationsBinding

class Animations : AppCompatActivity() {
    lateinit var binding: ActivityAnimationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)

        binding.fadeInBtn.setOnClickListener {
            binding.name.visibility = View.VISIBLE
            val animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)
            binding.name.startAnimation(animationFadeIn)
        }

        binding.fadeOutBtn.setOnClickListener {
            val animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout)
            binding.name.startAnimation(animationFadeOut)
            Handler().postDelayed({
            }, 2000)
        }

        binding.zoomInBtn.setOnClickListener { 
            val animationZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoomin)
            binding.name.startAnimation(animationZoomIn)
        }

        binding.zoomOutBtn.setOnClickListener {
            val animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout)
            binding.name.startAnimation(animationZoomOut)
        }

        binding.slideUpBtn.setOnClickListener {
            val animationSlideUp = AnimationUtils.loadAnimation(this, R.anim.slideup)
            binding.name.startAnimation(animationSlideUp)
        }

        binding.slideDownBtn.setOnClickListener {
            val animationSlideDown = AnimationUtils.loadAnimation(this, R.anim.slidedown)
            binding.name.startAnimation(animationSlideDown)
        }

        binding.bounceBtn.setOnClickListener {
            val animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
            binding.name.startAnimation(animationBounce)
        }

        binding.rotateBtn.setOnClickListener {
            val animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
            binding.name.startAnimation(animationRotate)
            binding.name.visibility = View.VISIBLE
        }

        setContentView(binding.root)
    }
}