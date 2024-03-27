package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R

class RoutineCreationActivity : AppCompatActivity() {
    private var questName : EditText? = null //persist in firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("RoutineCreationActivity", "OnCreate called")
        setContentView(R.layout.routine_creation)
        questName = findViewById(R.id.routine_name)
    }
}