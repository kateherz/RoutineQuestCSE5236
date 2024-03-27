package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Task
import com.google.android.gms.tasks.Tasks

class RoutineCreationActivity : AppCompatActivity() {
    private var questName : EditText? = null //persist in firebase
    private var taskList: ArrayList<Task> = arrayListOf() //persist in firebase
    private lateinit var addTaskButton: Button
    private lateinit var saveButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("RoutineCreationActivity", "OnCreate called")
        setContentView(R.layout.routine_creation)
        questName = findViewById(R.id.routine_name)
        addTaskButton = findViewById(R.id.addMoreTasks)
        saveButton = findViewById(R.id.saveButton)
    }
}