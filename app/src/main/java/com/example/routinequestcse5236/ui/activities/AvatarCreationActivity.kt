package com.example.routinequestcse5236.ui.activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.model.Routine
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

class AvatarCreationActivity : AppCompatActivity() {
    private lateinit var nextButton: Button
    private lateinit var displayName : EditText
    private lateinit var databaseRef: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("AvatarCreationActivity", "onCreate()")
        setContentView(R.layout.activity_avatar_creation)


        displayName = findViewById(R.id.display_name)

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            val intent = Intent(this, AvatarCreationConfirmationActivity::class.java)
            startActivity(intent)
        }
        setupIconGrid()
    }

    override fun onPause() {
        super.onPause()
        Log.d("method call","pause method called")
    }

    private fun setupIconGrid() {
        Log.d("AvatarCreationActivity", "setupIconGrid()")
        val gridLayout: GridLayout = findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount) {
            val button: ImageButton = gridLayout.getChildAt(i) as ImageButton
            val buttonID = resources.getResourceEntryName(button.id)
            //set up view model and repo classes for later checkpoints
            button.setOnClickListener {
                databaseRef = Firebase.firestore
                firebaseAuth = FirebaseAuth.getInstance()
                Log.d("ButtonClicked", "String Resource: $buttonID")
                val data = hashMapOf(
                    "avatar" to buttonID,
                    "username" to displayName.text.toString(),
                    "routines" to ArrayList<Routine>()
                )
                databaseRef
                    .collection("users")
                    .document(firebaseAuth.currentUser?.email.toString())
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d("Firebase", "avatar and username added to user successfully")
                    }
            }
        }
    }
}