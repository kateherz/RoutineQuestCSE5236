package com.example.routinequestcse5236.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Task
import com.example.routinequestcse5236.model.TaskListAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class TaskViewActivity: AppCompatActivity() {
    private lateinit var taskListView : ListView
    private lateinit var tasks: ArrayList<HashMap<String,Any>>
    private lateinit var taskArrayAdapter: ArrayAdapter<String>
    private lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        button = findViewById(R.id.back_task)
        val bundle : Bundle?  = intent.extras

        tasks = bundle?.getSerializable("TaskList") as ArrayList<HashMap<String,Any>>
        var titles : ArrayList<String> = ArrayList()

        tasks.forEach() {t ->
            titles.add(t["name"].toString())
        }

        handleTaskList(titles)

        Log.d("TaskViewActivity", "OnCreate")
    }

    private fun handleTaskList(titles : ArrayList<String>) {
        taskListView = findViewById(R.id.task_list_view)
        taskArrayAdapter = TaskListAdapter(this, titles, tasks)
            //ArrayAdapter(this, R.layout.list_item_routine, R.id.titleTextView, titles)
        taskListView.adapter = taskArrayAdapter
    }

    fun finishActivity(view: View) {
        Log.d("TaskViewActivity", "finishActivity called")
        finish()
    }

    override fun onDestroy() {
        Log.d("TaskViewActivity", "onDestroy Called")
        super.onDestroy()
    }
}


//TO MOVE
