package com.kirabium.relayance

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import com.kirabium.relayance.ui.activity.DetailActivity
import com.kirabium.relayance.ui.activity.MainActivity
import com.kirabium.relayance.util.RecyclerViewItemCountAssertion
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.java

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp(){
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun customerListInitialCount_shouldHaveCorrectNumberOfItem() {
        // GIVEN
        val expectedCount = DummyData.customers.value.size

        onView(withId(R.id.customerRecyclerView))
            .check(RecyclerViewItemCountAssertion.withItemCount(expectedCount))
    }

    @Test
    fun clickOnCustomer_shouldSendCorrectIntentWithId() {
        // GIVEN
        val targetCustomer = DummyData.customers.value[0]

        // WHEN
        onView(withId(R.id.customerRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // THEN
        intended(
            allOf(
                hasComponent(DetailActivity::class.java.name),
                hasExtra(DetailActivity.EXTRA_CUSTOMER_ID, targetCustomer.id)
            )
        )
    }

    @Test
    fun clickOnFabButton_shouldRedirectToAddCustomer() {
        // WHEN
        onView(withId(R.id.addCustomerFab)).perform(click())

        //THEN
        intended(
            allOf(
                hasComponent(AddCustomerActivity::class.java.name)
            )
        )
    }

}