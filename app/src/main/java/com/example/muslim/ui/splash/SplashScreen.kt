package com.example.muslim.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.muslim.R
import com.example.muslim.ui.home.HomeActivity

class SplashScreen : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

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