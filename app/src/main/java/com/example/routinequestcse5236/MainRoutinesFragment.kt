package com.example.routinequestcse5236
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MainRoutinesFragment : Fragment() {
    private val routineNames = mutableListOf<String>() //FOR TESTING ONLY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MainRoutinesFragment", "OnCreateView called")
        var v: View = inflater.inflate(R.layout.fragment_routines, container, false)
        return v
    }
}