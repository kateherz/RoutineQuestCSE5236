package com.example.routinequestcse5236.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.fragments.MainRoutinesFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainMenuActivity: AppCompatActivity() {
    private lateinit var createButton: Button
    private lateinit var avatarDisplay: ImageView
    private lateinit var welcomeMessage: TextView
    private lateinit var levelsText: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore
    //declare levels, xp of user, and total xp needed
    //declare name of user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainMenuActivity", "onCreate()")
        databaseRef = Firebase.firestore
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_main_menu)
        avatarDisplay = findViewById(R.id.profilePicture)
        welcomeMessage = findViewById(R.id.welcome)
        levelsText = findViewById(R.id.levelTextView)

        val docRef = databaseRef.collection("users").document(firebaseAuth.currentUser?.email.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var chosenAvatar = document.data?.get("avatar") as String
                    var username = document.data?.get("username") as String
                    var points = document.data?.get("points") as Long
                    val avatarId = resources.getIdentifier(chosenAvatar, "drawable", packageName)
                    avatarDisplay.setImageResource(avatarId)
                    var welcome = "Welcome, " + username + "! Let's tackle today!"
                    welcomeMessage.setText(welcome)
                    var levelStr = "Total Points: " + points.toString()
                    levelsText.setText(levelStr)
                }
            }

        setUpCreateTasks()
        loadTaskFragments()
    }

    private fun setUpCreateTasks() {
        Log.d("main menu activity", "set up create tasks")
        createButton = findViewById(R.id.addMoreTasks)
        createButton.setOnClickListener {
            Log.d("setUpCreateTasks", "Create task button created")
        }
    }

    private fun loadTaskFragments() {
        Log.d("loadTaskFragments", "loading task fragments")
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.routines)
        if (fragment == null) {
            fragment = MainRoutinesFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.routines, fragment).commit()
        }
        //TODO: figure this out
    }

}