package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.fragments.SignupFragment

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
        Log.d("MainActivity","pause method called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "onDestroy called")
    }
}