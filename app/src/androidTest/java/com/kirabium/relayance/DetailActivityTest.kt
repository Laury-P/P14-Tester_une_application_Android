package com.kirabium.relayance

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.ui.activity.DetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    private fun launchDetailWithID(targetID: Int, assert: () -> Unit) {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            DetailActivity::class.java
        ).apply {
            putExtra(DetailActivity.EXTRA_CUSTOMER_ID, targetID)
        }

        ActivityScenario.launch<DetailActivity>(intent).use {
            assert()
        }
    }

    @Test
    fun detailActivity_withAliceId_shouldDisplayAliceInfo_withoutNewCustomerTag() {
        // GIVEN
        val targetID = 1
        val targetCustomer = DummyData.customers.value.find { it.id == targetID }
            ?: throw Exception("Client non trouvé dans DummyData")

        // WHEN
        launchDetailWithID(targetID) {
            // THEN
            composeTestRule.onNodeWithText(targetCustomer.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(targetCustomer.email).assertIsDisplayed()
            composeTestRule.onNodeWithText("New").assertDoesNotExist()
        }
    }

    @Test
    fun detailActivity_withNewClient_shouldDisplayNewTag() {
        val targetID = 5
        val targetCustomer = DummyData.customers.value.find { it.id == targetID }
            ?: throw Exception("Client non trouvé dans DummyData")

        // WHEN
        launchDetailWithID(targetID) {
            // THEN
            composeTestRule.onNodeWithText(targetCustomer.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(targetCustomer.email).assertIsDisplayed()
            composeTestRule.onNodeWithText("New").assertIsDisplayed()
        }

    }

}

