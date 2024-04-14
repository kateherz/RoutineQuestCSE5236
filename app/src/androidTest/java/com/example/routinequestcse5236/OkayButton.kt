package com.example.routinequestcse5236

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.routinequestcse5236.ui.activities.AvatarCreationConfirmationActivity
import org.junit.Test

class AvatarCreationConfirmationActivityTest {
    val activityScenarioRule = ActivityScenarioRule(AvatarCreationConfirmationActivity::class.java)
    @Test
    fun testOkayButtonNavigation() {
        Espresso.onView(ViewMatchers.withId(R.id.okay_button))
            .perform(ViewActions.click())
    }
}