package com.example.routinequestcse5236.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R

class AvatarCreationConfirmationActivity: AppCompatActivity() {
    private lateinit var nextButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar_confirmation)
        nextButton = findViewById(R.id.okay_button)
        nextButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Avatar Creation Confirmation", "onDestroy called")
    }
}