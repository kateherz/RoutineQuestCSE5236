package com.example.routinequestcse5236

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

//private val accountViewModel: AccountViewModel by viewModels()

class MainActivity : AppCompatActivity() {
    //function onCreate called when an instance of the activity subclass is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate()")
        setContentView(R.layout.activity_main)
        //Log.d("MainActivity","Got a account viewModel: $accountViewModel")

        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.signup)

        if (fragment == null) {
            fragment = SignupFragment()
            fm.beginTransaction()
                .add(R.id.signup, fragment)
                .commit()
        }

    }
}