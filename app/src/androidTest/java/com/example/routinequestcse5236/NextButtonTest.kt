import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.activities.MainMenuActivity
import com.example.routinequestcse5236.ui.activities.AvatarCreationConfirmationActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AvatarCreationConfirmationActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(AvatarCreationConfirmationActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testOkayButtonLaunchesMainMenuActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.okay_button))
            .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(MainMenuActivity::class.java.name))
    }
}
