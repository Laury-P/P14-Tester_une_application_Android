package com.kirabium.relayance.test

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.testing.HiltTestApplication
import io.cucumber.android.runner.CucumberAndroidJUnitRunner


class CustomTestRunner : CucumberAndroidJUnitRunner() {

    override fun onCreate(bundle: Bundle) {
        Log.e("CUCUMBER_CHECK", "LE RUNNER EST BIEN LANCE !")

        Log.e("CUCUMBER", "features = ${bundle.getString("features")}")
        Log.e("CUCUMBER", "glue = ${bundle.getString("glue")}")

        bundle.putString("features", "assets/features")
        bundle.putString("glue", "com.kirabium.relayance.steps")
        bundle.putString("plugin", "pretty")

        super.onCreate(bundle)
    }

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

}