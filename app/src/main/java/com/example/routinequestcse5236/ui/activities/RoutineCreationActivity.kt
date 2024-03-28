package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Task
import com.example.routinequestcse5236.model.TaskAdapter

class RoutineCreationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var questName : EditText? = null //persist in firebase
    private var taskList: ArrayList<Task> = arrayListOf() //persist in firebase
    private lateinit var addTaskButton: Button
    private lateinit var saveButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        //why are no views being interacted with?
        super.onCreate(savedInstanceState)
        Log.d("RoutineCreationActivity", "OnCreate called")
        setContentView(R.layout.routine_creation)
        questName = findViewById(R.id.routine_name)
        addTaskButton = findViewById(R.id.addMoreTasks)
        saveButton = findViewById(R.id.saveButton)
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList)
        recyclerView.adapter = taskAdapter
        Log.d("RoutineCreationActivity", "OnCreate Views Finished")
        addTaskButton.setOnClickListener() {
            taskList.add(Task("",null)) //TODO: Figure out how to update once editTexts are written down
            taskAdapter.notifyDataSetChanged()
            Log.d("TaskaddButton", taskAdapter.itemCount.toString())
        }
    }

}