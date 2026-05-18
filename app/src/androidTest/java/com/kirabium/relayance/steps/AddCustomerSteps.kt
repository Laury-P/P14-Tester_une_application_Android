package com.kirabium.relayance.steps

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import com.kirabium.relayance.ui.activity.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf

@HiltAndroidTest
class AddCustomerSteps {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        Intents.init()
        scenario = ActivityScenario.launch(MainActivity::class.java)

    }

    @After
    fun tearDown() {
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
            .check(matches(isDisplayed()))
    }

    @Then("I should stay on the add customer screen")
    fun shouldStayOnAddCustomerScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.nameEditText))
            .check(matches(isDisplayed()))
    }

    @Then("The customer named {string} appear in the list")
    fun customerInList(nom: String) {
        Espresso.onView(ViewMatchers.withId(R.id.customerRecyclerView))
            .check(matches(ViewMatchers.hasDescendant(ViewMatchers.withText(nom))))
    }

    @Then("I should see the error message {string}")
    fun errorMessage(message: String) {
        try {
            // Étape 1 : On cherche d'abord l'erreur uniquement sur le champ Nom
            Espresso.onView(withId(R.id.nameEditText))
                .check(matches(hasErrorText(message)))
        } catch (e: AssertionError) {
            // Étape 2 : Si ce n'était pas sur le Nom, on cherche sur le champ Email
            Espresso.onView(withId(R.id.emailEditText))
                .check(matches(hasErrorText(message)))
        }
    }
}