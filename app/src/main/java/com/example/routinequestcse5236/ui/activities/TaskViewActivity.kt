package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R

class TaskViewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val routineId = intent.getStringExtra("Routine")
        // Use the routineId to fetch tasks corresponding to the clicked routine
        // and display them in the activity
    }
}