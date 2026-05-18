package com.kirabium.relayance.test

import android.app.Application
import android.content.Context
import android.os.Bundle
import dagger.hilt.android.testing.HiltTestApplication
import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.CucumberOptions

@CucumberOptions(
    features = ["features"],
    glue = ["com.kirabium.relayance.steps"],
    plugin = ["pretty"]
)
class CustomTestRunner : CucumberAndroidJUnitRunner() {
    override fun onCreate(bundle: Bundle) {
        bundle.putString("objectFactory","io.cucumber.android.hilt.HiltObjectFactory")
        super.onCreate(bundle)
    }

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}