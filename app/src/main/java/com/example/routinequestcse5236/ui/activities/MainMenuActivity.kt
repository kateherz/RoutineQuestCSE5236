package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.fragments.MainRoutinesFragment

class MainMenuActivity: AppCompatActivity() {
    private lateinit var createButton: Button
    //declare levels, xp of user, and total xp needed
    //declare name of user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainMenuActivity", "onCreate()")
        setContentView(R.layout.activity_main_menu)
        setUpCreateTasks()
        loadTaskFragments()
    }

    private fun setUpCreateTasks() {
        Log.d("main menu activity", "set up create tasks")
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
    fun onLayoutClick(view: View) {
        Log.d(" MainRoutinesFragment", "RoutineClicked")
        Toast.makeText(view.context, "Layout clicked", Toast.LENGTH_SHORT).show()
    }
}