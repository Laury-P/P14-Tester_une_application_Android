import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    jacoco
}

tasks.withType<Test> {
    useJUnitPlatform()
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        // On exclut les classes générées dynamiquement par le JDK qui font planter JaCoCo 0.8.11
        excludes = listOf("jdk.internal.*", "sun.*", "com.sun.*", "jdk.proxy.*")
    }

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }

}
fun getInstrumentationRunner(): String {
    return if (project.hasProperty("cucumber")) {
        "com.kirabium.relayance.test.CustomTestRunner" // Ton runner Cucumber
    } else {
        "androidx.test.runner.AndroidJUnitRunner" // Le runner classique pour tes autres tests
    }
}

android {
    namespace = "com.kirabium.relayance"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kirabium.relayance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = getInstrumentationRunner()
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }
    testOptions {
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        named("androidTest") {
            assets.setSrcDirs(listOf("src/androidTest/assets"))
        }
    }
}




dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.espresso.contrib)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.hilt)

    ksp(libs.hilt.compiler)

    // Test unitaires
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)

    // Test d'instrumentation
    androidTestImplementation(libs.androidx.junit4)
    androidTestImplementation(libs.androidx.junit)

    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.androidx.espresso.intents)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.rules)

    androidTestImplementation(libs.cucumber.android)
    androidTestImplementation(libs.cucumber.hilt)
    androidTestImplementation(libs.cucumber.junit)
    androidTestImplementation(libs.cucumber.java)
    androidTestImplementation(libs.androidx.test.runner)


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}