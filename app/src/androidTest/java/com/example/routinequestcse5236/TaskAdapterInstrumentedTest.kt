package com.example.routinequestcse5236

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.routinequestcse5236.model.Task
import com.example.routinequestcse5236.model.TaskAdapter
import com.example.routinequestcse5236.model.TaskDifficulty
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


// From https://github.com/codepath/android-testing-demo/blob/master/app/src/test/java/com.codepath.testingdemo/adapters/PostsAdapterTest.java
@RunWith(AndroidJUnit4::class)
class TasksAdapterTest {
    private var context: Context? = null
    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun tasksAdapterViewRecycling() {

        //setup input
        val tasks: ArrayList<Task> = java.util.ArrayList()
        tasks.add( Task("brush teeth", TaskDifficulty.EASY, false))
        tasks.add(Task("brush hair", TaskDifficulty.MEDIUM, false))
        tasks.add(Task("Finish project", TaskDifficulty.EXPERT, false))

        val adapter = TaskAdapter(tasks)

        val rvParent = RecyclerView(context!!)
        rvParent.layoutManager = LinearLayoutManager(context)

        // Run test
        val viewHolder: TaskAdapter.TaskViewHolder= adapter.onCreateViewHolder(rvParent, 0)
        adapter.onBindViewHolder(viewHolder, 0)

        // JUnit Assertion
        assertEquals("brush teeth", viewHolder.titleEditText.text.toString())

        assertEquals(3, adapter.itemCount)
    }
}
