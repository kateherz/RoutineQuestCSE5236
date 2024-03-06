package com.example.routinequestcse5236

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private val accountViewModel: AccountViewModel by viewModels()

    //function onCreate called when an instance of the activity subclass is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")
        setContentView(R.layout.activity_main)
        Log.d(TAG,"Got a account viewModel: $accountViewModel")

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