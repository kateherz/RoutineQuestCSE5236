package com.example.routinequestcse5236
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AvatarCreationActivity : AppCompatActivity() {
    private lateinit var nextButton: Button
    private lateinit var displayName : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("AvatarCreationActivity", "onCreate()")
        setContentView(R.layout.avatar_creation)

        displayName = findViewById(R.id.display_name)

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            val intent = Intent(this, AvatarCreationConfirmation::class.java)
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
                Log.d("ButtonClicked", "String Resource: $buttonID")
            }
        }
    }
}