package com.example.routinequestcse5236.ui.fragments
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Routine
import com.example.routinequestcse5236.model.RoutineAdapter
import com.example.routinequestcse5236.model.Task
import com.example.routinequestcse5236.ui.activities.RoutineCreationActivity
import com.example.routinequestcse5236.ui.activities.TaskViewActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

class MainRoutinesFragment : Fragment() {
    private lateinit var addButton: Button
    private lateinit var deleteButton: Button
    private lateinit var routines : ArrayList<HashMap<String,Any>>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore
    private lateinit var routineListView: ListView
    private lateinit var routineArrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MainRoutinesFragment", "OnCreateView called")
        var v: View = inflater.inflate(R.layout.fragment_routines, container, false)


        databaseRef = Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()
        routines = ArrayList<HashMap<String,Any>>()
        routineListView = v.findViewById(R.id.routine_list_view)
        var titles : ArrayList<String> = ArrayList()

        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                //if (document != null) {
                    routines = document.data?.get("routines") as ArrayList<HashMap<String,Any>>
                    Log.d("", "DocumentSnapshot data: ${document.data?.get("routines")}")
                routines.forEach() {r ->
                    Log.d("Titles", r.toString())
                    var numTasks = r["tasks"].toString().split("{").size - 1
                    var titleString = r["title"].toString() + "                # of Tasks: " + numTasks.toString()
                    titles.add(titleString)
                }
                Log.d("docRef", titles.toString())

                routineArrayAdapter = ArrayAdapter(
                    v.context, R.layout.list_item_routine, R.id.titleTextView, titles)
                routineListView.adapter = routineArrayAdapter
            }
            .addOnFailureListener { exception ->
                Log.d("", "get failed with ", exception)
            }
        //routines = docRef.get().result.data?.get("routines") as ArrayList<Routine>

        addButton = v.findViewById<Button>(R.id.addMoreTasks)
        addButton.setOnClickListener {
            Log.d("addButton", "Button Pressed")
            val intent = Intent(v.context, RoutineCreationActivity::class.java)
            startActivity(intent)
            Log.d("addButton", "intent not launched")
        }

        deleteButton = v.findViewById(R.id.deleteTask)
        deleteButton.setOnClickListener {
            Log.d("deleteButton", "Button Pressed")
            Log.d("deleteButton", "current routines: " + routines)
            if (routines.size >= 1) {
                routines.removeLast()
                routineArrayAdapter.notifyDataSetChanged()

                    val data = hashMapOf("routines" to routines)
                    databaseRef
                        .collection("users")
                        .document(firebaseAuth.currentUser?.email.toString())
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d("Firebase", "last routine deleted successfully")
                        }
            }
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MainRoutinesFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                routines = document.data?.get("routines") as ArrayList<HashMap<String,Any>>
                Log.d("main routines fragment on view created", "DocumentSnapshot data: ${routines}")
            }
            .addOnFailureListener { exception ->
                Log.d("", "get failed with ", exception)
            }

        //routineListView = view.findViewById(R.id.routine_list_view)
        routineListView.setOnItemClickListener { parent, view, position, id ->
            Log.d("Routine List View Click Listener", position.toString() + " clicked")
            val clickedItem = routines[position]
            val intent = Intent(requireContext(), TaskViewActivity::class.java)
            /*val b : Bundle = Bundle()
            b.putSerializable("TaskList")*/
            intent.putExtra("TaskList",clickedItem["tasks"] as ArrayList<Task>)
            Log.d("Routine List View Click Listener",
                intent.getSerializableExtra("TaskList").toString()
            )
            startActivity(intent)
        }
        Log.d("MainRoutinesFragment", "routineAdapter instantiated")
    }
}