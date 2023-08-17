package com.example.muslim.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.muslim.R
import com.example.muslim.ui.home.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val anim = AnimationUtils.loadAnimation(this, R.anim.animation_splash)

        val image = findViewById(R.id.iv_splash) as ImageView
        image.startAnimation(anim)
        val typingText = findViewById<TextView>(R.id.tv_splash)

        Handler().postDelayed({ typingText.setText("M") }, 800)
        Handler().postDelayed({ typingText.append("u") }, 1000)
        Handler().postDelayed({ typingText.append("s") }, 1500)
        Handler().postDelayed({ typingText.append("l") }, 2000)
        Handler().postDelayed({ typingText.append("i") }, 2500)
        Handler().postDelayed(
            {
                typingText.append("m")
                openHome()
            },

            3000
        )
    }

    private fun openHome() {
        // Your Code
        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}