package com.example.routinequestcse5236.ui.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Routine
import com.example.routinequestcse5236.model.Task
import com.example.routinequestcse5236.model.TaskAdapter
import com.example.routinequestcse5236.model.TaskDifficulty
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

class RoutineCreationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var questName : EditText? = null //persist in firebase
    private var taskList: ArrayList<Task> = arrayListOf() //persist in firebase
    private lateinit var addTaskButton: Button
    private lateinit var saveButton : Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        //why are no views being interacted with?
        super.onCreate(savedInstanceState)
        Log.d("RoutineCreationActivity", "OnCreate called")
        setContentView(R.layout.routine_creation)
        questName = findViewById(R.id.routine_name)
        addTaskButton = findViewById(R.id.addMoreRoutines)
        saveButton = findViewById(R.id.saveButton)
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList)
        recyclerView.adapter = taskAdapter
        databaseRef = Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()
        Log.d("RoutineCreationActivity", "OnCreate Views Finished")
        addTaskButton.setOnClickListener {
            Log.d("TaskaddButton", taskAdapter.itemCount.toString())
            taskList.add(Task("",null)) //TODO: Figure out how to update once editTexts are written down
            taskAdapter.notifyDataSetChanged()
        }
        saveButton.setOnClickListener {v->
            Log.d("SaveButton", "Save button pressed")
            if(isOnline(this)==false){
                Toast.makeText(this, "No Connection! Please reconnect to save new routine!", Toast.LENGTH_LONG).show()
            }
            for (i in 0 until taskList.size) {
                val taskView = recyclerView.layoutManager?.findViewByPosition(i)
                val taskName = taskView?.findViewById<EditText>(R.id.taskName)?.text.toString()
                val spinner = taskView?.findViewById<Spinner>(R.id.difficultyDropdown)
                val taskDifficultySelected = spinner?.selectedItem.toString()

                taskList[i].name = taskName
                taskList[i].difficulty = enumValueOf<TaskDifficulty>(taskDifficultySelected)
            }

            var routine = Routine(questName?.text.toString(), taskList)
            val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        var currentRoutines = document.data?.get("routines") as ArrayList<Routine>
                        currentRoutines.add(routine)
                        val data = hashMapOf("routines" to currentRoutines)
                        databaseRef
                            .collection("users")
                            .document(firebaseAuth.currentUser?.email.toString())
                            .set(data, SetOptions.merge())
                            .addOnSuccessListener {
                                Log.d("Firebase", "routine added successfully")
                                val intent = Intent(v.context, MainMenuActivity::class.java)
                                //createRoutineActivityResult.launch(intent)
                                startActivity(intent)
                            }
                        Log.d("routCreatActiv", "updated routines: ${currentRoutines}")
                    }
                }

            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("RoutineCreationActivity", "onDestroy called")
    }

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
}