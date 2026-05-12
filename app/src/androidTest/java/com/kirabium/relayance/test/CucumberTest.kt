package com.kirabium.relayance.test

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.kirabium.relayance.steps"],
    plugin = ["pretty"]
)
class CucumberTest {
    // Cette classe reste vide, elle sert juste de point d'entrée
}