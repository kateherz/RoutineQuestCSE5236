package com.example.routinequestcse5236

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity: AppCompatActivity() {
    private lateinit var createButton: Button
    //declare levels, xp of user, and total xp needed
    //declare name of user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainMenuActivity", "onCreate()")
        setContentView(R.layout.main_menu)
        setUpCreateTasks()
        loadTaskFragments()
    }

    private fun setUpCreateTasks() {
        createButton = findViewById(R.id.addMoreTasks)
        createButton.setOnClickListener {
            Log.d("setUpCreateTasks", "Create task button created")
        }
    }

    private fun loadTaskFragments() {
        Log.d("loadTaskFragments", "loading task fragments")
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.routines)
        if (fragment == null) {
            fragment = MainRoutinesFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.routines, fragment).commit()
        }
        //TODO: figure this out
    }

}