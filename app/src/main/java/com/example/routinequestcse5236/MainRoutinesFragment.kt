package com.example.routinequestcse5236
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainRoutinesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var routineAdapter: RoutineAdapter
    private lateinit var addButton: Button
    private lateinit var routines : ArrayList<Routine>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MainRoutinesFragment", "OnCreateView called")
        var v: View = inflater.inflate(R.layout.fragment_routines, container, false)

        routines = ArrayList()

        val addButton= v.findViewById<Button>(R.id.addMoreTasks)
        addButton.setOnClickListener {
            Log.d("addButton", "Button Pressed")
            routines.add(Routine("task",9))
            routineAdapter.notifyDataSetChanged()
            Log.d("addButton", routineAdapter.itemCount.toString())
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