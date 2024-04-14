import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.activities.RoutineCreationActivity
import org.junit.Rule
import org.junit.Test

class RoutineCreationActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RoutineCreationActivity::class.java)

    @Test
    fun testAddTaskButton() {
        // Perform click on the add task button
        Espresso.onView(ViewMatchers.withId(R.id.addMoreRoutines))
            .perform(ViewActions.click())

        // Check if the taskList has been updated and TaskAdapter has been notified
        Espresso.onView(ViewMatchers.withId(R.id.taskRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.hasChildCount(1))) // Assuming one task is added
    }
}
