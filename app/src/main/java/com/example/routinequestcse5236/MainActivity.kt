package com.example.routinequestcse5236

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //function onCreate called when an instance of the activity subclass is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate()")
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.signup)

        if (fragment == null) {
            fragment = SignupFragment()
            fm.beginTransaction()
                .add(R.id.signup, fragment)
                .commit()
        }

    }
    override fun onPause() {
        super.onPause()
        Log.d("method call","pause method called")
    }
}