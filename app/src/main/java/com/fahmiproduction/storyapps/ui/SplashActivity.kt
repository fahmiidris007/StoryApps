package com.fahmiproduction.storyapps.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fahmiproduction.storyapps.viewmodel.AuthViewModel
import com.fahmiproduction.storyapps.viewmodel.ViewModelFactory

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLauncher()
    }

    private fun setLauncher() {
        val factory = ViewModelFactory.getInstance(this)
        val authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        Handler(Looper.getMainLooper()).postDelayed({
            authViewModel.getUser().observe(this) {
                if (it.userId.isEmpty()) {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 500)
    }
}