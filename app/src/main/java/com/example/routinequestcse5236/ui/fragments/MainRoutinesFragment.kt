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
import com.example.routinequestcse5236.ui.activities.RoutineCreationActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

class MainRoutinesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var routineAdapter: RoutineAdapter
    private lateinit var addButton: Button
    private lateinit var deleteButton: Button
    private lateinit var routines : ArrayList<Routine>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore
    private lateinit var routineListView: ListView
    private lateinit var routineArrayAdapter: ArrayAdapter<Routine>

    private val createRoutineActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                /*routines.add(Routine("task",9))
                routineAdapter.notifyDataSetChanged()
                Log.d("addButton", routineAdapter.itemCount.toString())
                val data = hashMapOf("routines" to routines)
                databaseRef
                    .collection("users")
                    .document(firebaseAuth.currentUser?.email.toString())
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d("Firebase", "routines added to user successfully")
    }*/
                //val routineData = data?.getParcelableExtra<Routine>("routineData")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MainRoutinesFragment", "OnCreateView called")
        var v: View = inflater.inflate(R.layout.fragment_routines, container, false)


        databaseRef = Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()
        routines = ArrayList()

        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                //if (document != null) {
                    routines = document.data?.get("routines") as ArrayList<Routine>
                    Log.d("", "DocumentSnapshot data: ${document.data?.get("routines")}")
                //} else {
                    Log.d("", "No such document")
                //}
                routineListView = v.findViewById(R.id.routine_list_view)
                routineArrayAdapter = ArrayAdapter(
                    v.context, android.R.layout.simple_list_item_1, routines)
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
            //createRoutineActivityResult.launch(intent)
            startActivity(intent)
            Log.d("addButton", "intent not launched")
        }

        deleteButton = v.findViewById(R.id.deleteTask)
        deleteButton.setOnClickListener {
            Log.d("deleteButton", "Button Pressed")
            Log.d("deleteButton", "current routines: " + routines)
            if (routines.size >= 1) {
                routines.removeLast()
                routineAdapter.notifyDataSetChanged()

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



//        recyclerView = v.findViewById(R.id.routinesRecyclerView)
//        routineAdapter = RoutineAdapter(routines)
//        recyclerView.layoutManager = LinearLayoutManager(v.context)
//        recyclerView.adapter = routineAdapter

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MainRoutinesFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                routines = document.data?.get("routines") as ArrayList<Routine>
                Log.d("main routines fragment on view created", "DocumentSnapshot data: ${routines}")
            }
            .addOnFailureListener { exception ->
                Log.d("", "get failed with ", exception)
            }

//        recyclerView = view.findViewById(R.id.routinesRecyclerView)
//        routineAdapter = RoutineAdapter(routines)
//        recyclerView.layoutManager = LinearLayoutManager(view.context)
//        recyclerView.adapter = routineAdapter
        Log.d("MainRoutinesFragment", "routineAdapter instantiated")
    }
}