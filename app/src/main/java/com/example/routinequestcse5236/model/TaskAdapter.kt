package com.example.routinequestcse5236.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
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

//https://stackoverflow.com/questions/51141970/check-internet-connectivity-android-in-kotlin
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}
class TaskListAdapter(context: Context, private val titles: List<String>, private val tasks: ArrayList<HashMap<String,Any>>)
    : ArrayAdapter<String>(context, R.layout.list_item_routine, R.id.titleTextView, titles) {

    private lateinit var databaseRef: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val checkBox = view.findViewById<CheckBox>(R.id.completedCheckbox)
        databaseRef = Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()

        var task = tasks[position]
        var taskName = task["name"]

        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                var routines = document.data?.get("routines") as ArrayList<HashMap<String, Any>>
                routines.forEach() { r ->

                        var tasks = r["tasks"] as ArrayList<HashMap<String, Any>>
                        tasks.forEach() { t ->
                            if (t["name"] == taskName) {
                                if (t["completed"] == true) {
                                    checkBox.isChecked = true
                                    checkBox.isClickable = false
                                }
                            }
                        }

                }
            }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if(isOnline(context)== false) {
                Toast.makeText(context, "No Connection! Please reconnect to save changes", Toast.LENGTH_LONG).show()
            }
            if (isChecked) {
                awardPointsForTask(position)
            } else {
                checkBox.isChecked = true
                checkBox.isClickable = false
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
                var currentPoints = document.data?.get("points") as Long
                routines.forEach() { r ->
                    var tasks = r["tasks"] as ArrayList<HashMap<String,Any>>
                    tasks.forEach() { t ->
                        if (t["name"] == taskName && t["completed"] == false) {
                            t["completed"] = true
                            Log.d("awardPointsForTask", t.toString())
                            currentPoints++
                        }
                    }
                    if (allTasksComplete(tasks)) {
                        r["completed"] = true
                    }
                }
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

    private fun allTasksComplete(tasks: ArrayList<HashMap<String, Any>>): Boolean {
        var allComplete = true
        tasks.forEach() {t ->
            if (t["completed"] == false) {
                allComplete = false
            }
        }
        return allComplete
    }
}

enum class TaskDifficulty {EASY, MEDIUM, HARD, EXPERT}

data class Task(var name: String, var difficulty: TaskDifficulty?, var completed: Boolean = false)
