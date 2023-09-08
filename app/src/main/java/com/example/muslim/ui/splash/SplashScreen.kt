package com.example.muslim.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.muslim.R
import com.example.muslim.ui.home.HomeActivity


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val anim = findViewById<LottieAnimationView>(R.id.lottie_splash)
        anim.playAnimation()
        anim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.e("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.e("Animation:", "end")
                //Your code for remove the fragment
                try {
                   openHome()
                    } catch (ex: Exception) {
                    ex.toString()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.e("Animation:", "repeat")
            }
        })

    }

    private fun openHome() {
        // Your Code
        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}