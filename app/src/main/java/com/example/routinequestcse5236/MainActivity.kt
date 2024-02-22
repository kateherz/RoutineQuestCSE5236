package com.example.routinequestcse5236

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupIconGrid()
    }
    private fun setupIconGrid() {
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