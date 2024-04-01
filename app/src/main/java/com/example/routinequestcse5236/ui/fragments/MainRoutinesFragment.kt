package com.example.routinequestcse5236.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
import java.util.Objects
import kotlin.math.sqrt

class MainRoutinesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var routineAdapter: RoutineAdapter
    private lateinit var addButton: Button
    private lateinit var deleteButton: Button
    private lateinit var popUpButton: Button
    private lateinit var routines : ArrayList<Routine>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore
    private lateinit var alertDialog: AlertDialog

    //for shake detection
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f
    //private var userShake: Boolean = false

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

        //Shake detection set-up
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)!!
            .registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL)

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

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

            //make late init or class variable
            alertDialog = builder.create()
            alertDialog.show()
//
//            if(userShake){
//                //reset variable
//                userShake = false
//                //TO-DO: delete task/mark as done
//                alertDialog.cancel()
//                //notify user of success
//                Toast.makeText(context, "Task Complete!", Toast.LENGTH_LONG).show()
//            }
        }

        return v
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            if(acceleration>1 || acceleration<-1) {
                Log.d("Sensor", "current acceleration: $currentAcceleration")
            }
                val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if(acceleration>1 || acceleration<-1) {
                Log.d("Sensor", "acceleration: $acceleration")
            }
            if (acceleration > 10) {
                if(alertDialog.isShowing) {
                    alertDialog.cancel()
                    //TO-DO: delete task/mark as done
                    Toast.makeText(context, "Task Complete!", Toast.LENGTH_SHORT).show()
                }
                //close an unregister the listener in onStop or onDestroyview.
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
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