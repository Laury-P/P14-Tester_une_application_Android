package com.kirabium.relayance

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.ui.activity.MainActivity
import com.kirabium.relayance.util.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun customerListInitialCount_shouldHaveCorrectNumberOfItem() {
        // GIVEN
        val expectedCount = DummyData.customers.size

        onView(withId(R.id.customerRecyclerView))
            .check(RecyclerViewItemCountAssertion.withItemCount(expectedCount))
    }




}