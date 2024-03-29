package com.example.routinequestcse5236.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.routinequestcse5236.R

class TaskAdapter(private val tasks: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleEditText: EditText = itemView.findViewById(R.id.taskName)
        val difficultyDropdown: Spinner = itemView.findViewById(R.id.difficultyDropdown)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.routine_creation_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d("task adapter", "to be implemented")
        //TODO("Not yet implemented")
    }
}


enum class TaskDifficulty {EASY, MEDIUM, HARD, EXPERT}

data class Task(val name: String, val difficulty: TaskDifficulty?, var completed: Boolean = false)
