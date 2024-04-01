package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Task

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
        taskListView = findViewById(R.id.task_list_view)
        taskArrayAdapter = ArrayAdapter(this, R.layout.list_item_routine, R.id.titleTextView, titles)
        taskListView.adapter = taskArrayAdapter

        Log.d("TaskViewActivity", "OnCreate")
    }

    fun onCheckClick(view: View) {
        Log.d("onCheckClick", this.javaClass.name)

        //Kate --> This is where you are going to implement your feature
    }


    fun finishActivity(view: View) {
        finish()
    }

}