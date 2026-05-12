package com.kirabium.relayance.steps

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Rule

@HiltAndroidTest
class AddCustomerSteps {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var scenario: ActivityScenario<AddCustomerActivity>

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown(){
        scenario.close()
    }

    @Given("I am on the add customer screen")
    fun shouldBeOnAddCustomerScreen() {
        scenario = ActivityScenario.launch(AddCustomerActivity::class.java)
        //Verification que l'écran d'ajout est bien présent à l'écran
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()))
    }

    @When("I type {string} in the name field")
    fun typedName(nom: String) {
        onView(withId(R.id.nameEditText)).perform(replaceText(nom), closeSoftKeyboard())
    }

    @When("I type {string} in the email field")
    fun typedEmail(email: String) {
        onView(withId(R.id.emailEditText)).perform(replaceText(email), closeSoftKeyboard())
    }

    @When("I click on the save button")
    fun clickOnSaveButton() {
        onView(withId(R.id.saveFab)).perform(click())
    }

    @Then("The app navigate to the main screen")
    fun shouldNavigateToMainScreen() {
        onView(withId(R.id.customerRecyclerView)).check(matches(isDisplayed()))
    }

    @Then("I should stay on the add customer screen")
    fun shouldStayOnAddCustomerScreen() {
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()))
    }

    @Then("The customer named {string} appear in the list")
    fun customerInList(nom: String) {
        onView(withId(R.id.customerRecyclerView))
            .check(matches(hasDescendant(withText(nom))))
    }

    @Then("I should see the error message {string}")
    fun errorMessage(message: String) {
        onView(withText(message)).check(matches(isDisplayed()))
    }

    @Then("The save button should be disabled")
    fun saveButtonDisabled() {
        onView(withId(R.id.saveFab)).check(matches(not(isEnabled())))
    }

}
