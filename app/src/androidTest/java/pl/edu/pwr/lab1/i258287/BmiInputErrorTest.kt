package pl.edu.pwr.lab1.i258287

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BmiInputErrorTest {

    private lateinit var zeroString: String

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun emptyMassInput_clickCountBtn_displayEmptyErrorMessage() {
        onView(withId(R.id.bmiBtn))
            .perform(click())

        onView(withId(R.id.massError))
            .check(matches(withText(R.string.error_mass_empty)))
    }

    @Test
    fun emptyHeightInput_clickCountBtn_displayEmptyErrorMessage() {
        onView(withId(R.id.bmiBtn))
            .perform(click())

        onView(withId(R.id.heightError))
            .check(matches(withText(R.string.error_height_empty)))
    }

    @Before
    fun initInputString() {
        zeroString = "0"
    }

    @Test
    fun typeZeroMassString_clickCountBtn_displayZeroErrorMessage() {
        onView(withId(R.id.massInput))
            .perform(typeText(zeroString), closeSoftKeyboard())

        onView(withId(R.id.bmiBtn))
            .perform(click())

        onView(withId(R.id.massError))
            .check(matches(withText(R.string.error_mass_zero)))
    }

    @Test
    fun typeZeroHeightString_clickCountBtn_displayZeroErrorMessage() {
        onView(withId(R.id.heightInput))
            .perform(typeText(zeroString), closeSoftKeyboard())

        onView(withId(R.id.bmiBtn))
            .perform(click())

        onView(withId(R.id.heightError))
            .check(matches(withText(R.string.error_height_zero)))
    }
}