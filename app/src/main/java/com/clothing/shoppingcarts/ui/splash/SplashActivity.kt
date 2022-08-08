package com.clothing.shoppingcarts.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.WindowInsetsControllerCompat
import com.clothing.shoppingcarts.MainActivity
import com.clothing.shoppingcarts.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setWindowLightStatus()

        Handler(Looper.getMainLooper()).postDelayed({
            // Start your app main activity
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 3000)
    }

    private fun setWindowLightStatus() {
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = true
    }
}