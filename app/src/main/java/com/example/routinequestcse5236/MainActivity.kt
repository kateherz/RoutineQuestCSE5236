package com.example.routinequestcse5236

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var nextButton: Button
    //function onCreate called when an instance of the activity subclass is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {view: View ->
            //do something in response to the click here
            Toast.makeText(
                this,
                R.string.input_name_toast,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}