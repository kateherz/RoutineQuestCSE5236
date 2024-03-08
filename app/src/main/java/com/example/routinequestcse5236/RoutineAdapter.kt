package com.example.routinequestcse5236
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoutineAdapter(private val routines: ArrayList<Routine>) :
    RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val tasksTextView: TextView = itemView.findViewById(R.id.tasksTextView)
        val completedCheckbox: CheckBox = itemView.findViewById(R.id.completedCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.routine_item, parent, false)
        return RoutineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val currentRoutine = routines[position]
        holder.titleTextView.text = currentRoutine.title
        holder.tasksTextView.text = "Tasks: ${currentRoutine.tasks}"
        holder.completedCheckbox.isChecked = currentRoutine.completed
    }

    override fun getItemCount(): Int {
        return routines.size
    }
}

data class Routine(val title: String, val tasks: Int, var completed: Boolean = false)
