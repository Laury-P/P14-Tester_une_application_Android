package com.kirabium.relayance.test.steps

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import com.kirabium.relayance.ui.activity.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.Matchers
import io.cucumber.java.After

@HiltAndroidTest
class AddCustomerSteps {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        Intents.init()
        scenario = ActivityScenario.launch(MainActivity::class.java)

    }

    @After
    fun tearDown(){
        scenario.close()
        Intents.release()
    }

    @Given("I am on the add customer screen")
    fun shouldBeOnAddCustomerScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.addCustomerFab)).perform(ViewActions.click())
        Intents.intended(Matchers.allOf(IntentMatchers.hasComponent(AddCustomerActivity::class.java.name)))
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @When("I type {string} in the name field")
    fun typedName(nom: String) {
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText))
            .perform(ViewActions.replaceText(nom), ViewActions.closeSoftKeyboard())
    }

    @When("I type {string} in the email field")
    fun typedEmail(email: String) {
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText))
            .perform(ViewActions.replaceText(email), ViewActions.closeSoftKeyboard())
    }

    @When("I click on the save button")
    fun clickOnSaveButton() {
        Espresso.onView(ViewMatchers.withId(R.id.saveFab)).perform(ViewActions.click())
    }

    @Then("The app navigate to the main screen")
    fun shouldNavigateToMainScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.customerRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Then("I should stay on the add customer screen")
    fun shouldStayOnAddCustomerScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Then("The customer named {string} appear in the list")
    fun customerInList(nom: String) {
        Espresso.onView(ViewMatchers.withId(R.id.customerRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(nom))))
    }

    @Then("I should see the error message {string}")
    fun errorMessage(message: String) {
        Espresso.onView(ViewMatchers.withText(message))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Then("The save button should be disabled")
    fun saveButtonDisabled() {
        Espresso.onView(ViewMatchers.withId(R.id.saveFab))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())))
    }

}