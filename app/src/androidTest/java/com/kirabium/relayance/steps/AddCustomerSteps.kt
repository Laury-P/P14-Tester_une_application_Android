package com.kirabium.relayance.steps

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
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.AddCustomerActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
class AddCustomerSteps {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(AddCustomerActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Given("Je suis l'écran d'ajout d'un client")
    fun je_suis_l_écran_d_ajout_d_un_client() {
        //Verification que l'écran d'ajout est bien présent à l'écran
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()))
    }

    @When("Je saisis {string} dans le champs nom")
    fun je_saisis_dans_le_champs_nom(nom: String) {
        onView(withId(R.id.nameEditText)).perform(replaceText(nom), closeSoftKeyboard())
    }

    @When("Je saisis {string} dans le champs email")
    fun je_saisis_dans_le_champs_email(email: String) {
        onView(withId(R.id.emailEditText)).perform(replaceText(email), closeSoftKeyboard())
    }

    @When("Je clique sur le bouton de sauvegarde")
    fun je_clique_sur_le_bouton_de_sauvegarde() {
        onView(withId(R.id.saveFab)).perform(click())
    }

    @Then("L'application navigue vers l'ecran d'acceuil")
    fun l_application_navigue_vers_l_ecran_d_acceuil() {
        onView(withId(R.id.customerRecyclerView)).check(matches(isDisplayed()))
    }

    @Then("Je devrait rester sur l'écran d'ajout")
    fun je_devrait_rester_sur_l_écran_d_ajout() {
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()))
    }

    @Then("Le client {string} est présent dans la liste")
    fun le_client_est_présent_dans_la_liste(nom: String) {
        onView(withId(R.id.customerRecyclerView))
            .check(matches(hasDescendant(withText(nom))))
    }

    @Then("Je devrait voir le message d'erreur {string}")
    fun je_devrait_voir_le_message_d_erreur(message: String) {
        onView(withText(message)).check(matches(isDisplayed()))
    }

    @Then("Le bouton de sauvegarde doit être désactivé")
    fun le_bouton_de_sauvegarde_doit_être_désactivé(){
        onView(withId(R.id.saveFab)).check(matches(not(isEnabled())))
    }

}