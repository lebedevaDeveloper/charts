package com.example.charts.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.charts.databinding.ActivitySplashBinding
import com.example.charts.ui.activityBinding
import com.example.charts.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val binding by activityBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(MainActivity.newIntent(this))
        }, 2000)
    }
}