package com.example.routinequestcse5236.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
        holder.titleEditText.setText(tasks[position].name)

        val dropdownAdapter : ArrayAdapter<String> = ArrayAdapter(holder.itemView.context,
            android.R.layout.simple_spinner_dropdown_item,
            holder.itemView.resources.getStringArray(R.array.dropdown_items))
        Log.d("task adapter", "instantiated dropdown adapter")
        holder.difficultyDropdown.adapter=dropdownAdapter
        //TODO("Not yet implemented")
    }
}


enum class TaskDifficulty {EASY, MEDIUM, HARD, EXPERT}

data class Task(var name: String, var difficulty: TaskDifficulty?, var completed: Boolean = false)
