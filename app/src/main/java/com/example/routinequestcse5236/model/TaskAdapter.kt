package com.example.routinequestcse5236.model

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.routinequestcse5236.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

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

class TaskListAdapter(context: Context, private val titles: List<String>, private val tasks: ArrayList<HashMap<String,Any>>)
    : ArrayAdapter<String>(context, R.layout.list_item_routine, R.id.titleTextView, titles) {

    private lateinit var databaseRef: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val checkBox = view.findViewById<CheckBox>(R.id.completedCheckbox)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                awardPointsForTask(position)
            }
        }

        return view
    }
    private fun awardPointsForTask(position : Int) {
        Log.d("awardPointsForTask", "Completed Task $position")
        databaseRef = Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()


        var taskCompleted = tasks[position]
        //taskCompleted["completed"] = true
        var taskName = taskCompleted["name"]
        Log.d("awardPointsForTask", taskCompleted.toString())

        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                var routines = document.data?.get("routines") as ArrayList<HashMap<String,Any>>
                routines.forEach() { r ->
                    var tasks = r["tasks"] as ArrayList<HashMap<String,Any>>
                    tasks.forEach() { t ->
                        if (t["name"] == taskName) {
                            t["completed"] = true
                            Log.d("awardPointsForTask", t.toString())
                        }
                    }
                }
                var currentPoints = document.data?.get("points") as Long
                currentPoints++
                val data = hashMapOf("points" to currentPoints, "routines" to routines)
                databaseRef
                    .collection("users")
                    .document(firebaseAuth.currentUser?.email.toString())
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d("task checkbox", "points: " + currentPoints)
                    }
            }
            .addOnFailureListener { exception ->
                Log.d("", "get failed with ", exception)
            }
    }
}

enum class TaskDifficulty {EASY, MEDIUM, HARD, EXPERT}

data class Task(var name: String, var difficulty: TaskDifficulty?, var completed: Boolean = false)
