package com.example.routinequestcse5236.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.activities.MainMenuActivity
import com.example.routinequestcse5236.ui.activities.RoutineCreationActivity
import com.example.routinequestcse5236.ui.activities.TaskViewActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import java.util.Objects
import kotlin.math.sqrt

class MainRoutinesFragment : Fragment() {
    private lateinit var addButton: Button
    private lateinit var deleteButton: Button
    private lateinit var routines : ArrayList<HashMap<String,Any>>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore
    private var routineListView: ListView? = null
    private var routineArrayAdapter: ArrayAdapter<String>? = null
    private lateinit var alertDialog: AlertDialog

    //fFor shake detection
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

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
        routineArrayAdapter?.clear()
        routineListView?.adapter = null
        var titles : ArrayList<String> = ArrayList()

        //Log.d("MRF", "arr adap: " + routineArrayAdapter.toString() + "  list view: " + routineListView.toString())

        //Shake detection set-up
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                //if (document != null) {
                    routines = document.data?.get("routines") as ArrayList<HashMap<String,Any>>
                    Log.d("", "DocumentSnapshot data: ${document.data?.get("routines")}")
                routines.forEach() {r ->
                    var tasks = r["tasks"] as ArrayList<HashMap<String,Any>>
                    if (allTasksComplete(tasks)) {
                        r["completed"] = true
                    }
                    Log.d("Titles", r.toString())
                    var numTasks = r["tasks"].toString().split("{").size - 1
                    var titleString =
                        r["title"].toString() +
                                "                # of Tasks: " + numTasks.toString() +
                                "       Completed: " + r["completed"]
                    titles.add(titleString)

                }
                Log.d("docRef", titles.toString())

                routineArrayAdapter = ArrayAdapter(
                    v.context, R.layout.list_item_routine_two, R.id.titleTextView, titles)
                routineListView?.adapter = routineArrayAdapter
                //Log.d("MRF", "after adapter stuff")
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

        val builder = AlertDialog.Builder(context)

        builder.setMessage("Please shake your phone to confirm you want to delete the routine")
        builder.setTitle("Shake to Confirm!")
        builder.setCancelable(false) //when user clicks outside dialog box, do not exit

        builder.setNegativeButton("Cancel") {
                dialog, which -> dialog.cancel()
        }
        alertDialog = builder.create()

        deleteButton = v.findViewById(R.id.deleteTask)
        deleteButton.setOnClickListener {
            Log.d("deleteButton", "Button Pressed")
            Log.d("deleteButton", "current routines: " + routines)

            Objects.requireNonNull(sensorManager)!!
                .registerListener(sensorListener, sensorManager!!
                    .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL)

            alertDialog.show()
        }

        return v
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
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            if (acceleration > 10) {
                if(alertDialog.isShowing) {
                    alertDialog.cancel()
                    sensorManager?.unregisterListener(this);
                    //TO-DO: delete task/mark as done
                        if (routines.size >= 1) {
                            routines.removeLast()
                            routineArrayAdapter?.notifyDataSetChanged()

                            val data = hashMapOf("routines" to routines)
                            databaseRef
                                .collection("users")
                                .document(firebaseAuth.currentUser?.email.toString())
                                .set(data, SetOptions.merge())
                                .addOnSuccessListener {
                                    Log.d("Firebase", "last routine deleted successfully")
                                    Log.d(
                                        "Delete",
                                        "current context: " + parentFragmentManager.fragments.toString()
                                    )
                                    var fragment = parentFragmentManager.fragments.removeFirst()
                                    parentFragmentManager.beginTransaction()
                                        .add(R.id.routines, MainRoutinesFragment()).commit()
                                    val intent = Intent(
                                        activity?.applicationContext,
                                        MainMenuActivity::class.java
                                    )
                                    //createRoutineActivityResult.launch(intent)
                                    startActivity(intent)
                                }
                        }
                        Toast.makeText(context, "Routine Deleted!", Toast.LENGTH_SHORT).show()
                        if(context?.let { isOnline(it) } == false){
                            Toast.makeText(context, "No Connection! Please reconnect to save changes", Toast.LENGTH_LONG).show()
                        }
                    }
                //close an unregister the listener in onStop or onDestroyview.
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
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
        routineListView?.setOnItemClickListener { parent, view, position, id ->
            Log.d("Routine List View Click Listener", position.toString() + " clicked")
            val clickedItem = routines[position]
            val intent = Intent(requireContext(), TaskViewActivity::class.java)
            /*val b : Bundle = Bundle()
            b.putSerializable("TaskList")*/
            intent.putExtra("TaskList",clickedItem["tasks"] as ArrayList<HashMap<String,Any>>)
            Log.d("Routine List View Click Listener",
                intent.getSerializableExtra("TaskList").toString()
            )
            startActivity(intent)
        }
        Log.d("MainRoutinesFragment", "routineAdapter instantiated")
    }

    override fun onPause() {
        Log.d("MainRoutinesFragment", "onPause called")
        super.onPause()
    }

    override fun onResume() {
        Log.d("MainRoutinesFragment", "onResume called")
        super.onResume()
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