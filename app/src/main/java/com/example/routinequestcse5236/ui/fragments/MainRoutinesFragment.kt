package com.example.routinequestcse5236.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Routine
import com.example.routinequestcse5236.model.RoutineAdapter
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
    private lateinit var popUpButton: Button
    private lateinit var routines : ArrayList<Routine>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore

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

        addButton = v.findViewById<Button>(R.id.addMoreTasks)
        addButton.setOnClickListener {
            Log.d("addButton", "Button Pressed")
            routines.add(Routine("task",9))
            routineAdapter.notifyDataSetChanged()
            Log.d("addButton", routineAdapter.itemCount.toString())
            val data = hashMapOf("routines" to routines)
            databaseRef
                .collection("users")
                .document(firebaseAuth.currentUser?.email.toString())
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("Firebase", "routines added to user successfully")
                }
        }

        deleteButton = v.findViewById(R.id.deleteTask)
        deleteButton.setOnClickListener {
            Log.d("deleteButton", "Button Pressed")
            if (routines.size >= 1) {
                routines.removeLast()
                routineAdapter.notifyDataSetChanged()

                if (routines.size == 0) {
                    val updates = hashMapOf<String, Any>(
                        "routines" to FieldValue.delete(),
                    )
                    databaseRef
                        .collection("users")
                        .document(firebaseAuth.currentUser?.email.toString())
                        .update(updates)
                        .addOnSuccessListener {
                            Log.d("Firebase", "routines field deleted successfully")
                        }
                }
                else {
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
        }

        popUpButton = v.findViewById(R.id.popUp)
        popUpButton.setOnClickListener {
            Log.d("popUpButton", "PopUpButton Pressed")
            val builder = AlertDialog.Builder(context)

            builder.setMessage("Please shake your phone to confirm you have completed the task")
            builder.setTitle("Shake to Confirm!")
            builder.setCancelable(false) //when user clicks outside dialog box, do not exit

            builder.setNegativeButton("Cancel") {
                    dialog, which -> dialog.cancel()
            }
            //if phone shaken, dismiss dialog and set flag to indicate task is done.

            val alertDialog = builder.create()
            alertDialog.show()
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MainRoutinesFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.routinesRecyclerView)
        routineAdapter = RoutineAdapter(routines)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = routineAdapter
        Log.d("MainRoutinesFragment", "routineAdapter instantiated")
    }
}