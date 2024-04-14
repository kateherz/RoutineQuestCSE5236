import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.activities.AvatarCreationActivity
import org.junit.Rule
import org.junit.Test

class AvatarCreationActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(AvatarCreationActivity::class.java)

    @Test
    fun testNextButtonNavigation() {
        Espresso.onView(ViewMatchers.withId(R.id.next_button))
            .perform(ViewActions.click())

        // CheckS if AvatarCreationConfirmationActivity was launched
        Espresso.onView(ViewMatchers.withText("LET'S GO!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}