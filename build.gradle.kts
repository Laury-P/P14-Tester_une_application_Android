// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    id("jacoco")
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.12"
}

buildscript {
    dependencies {
        classpath("org.jacoco:org.jacoco.core:0.8.12")
    }
}

val fileFilter = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    "**/androidx/**/*.*",
    "**/*\$*.*",
    "**/*Binding.class",
    "**/*Module.class",
    "**/*Component.class",
    "**/*Factory.class",
    "**/*_MembersInjector.class",
    "**/*_Provide*Factory.class",
    "**/*MapperImpl.class",
    "**/*\$Lambda\$*.*",
    "**/*\$Serializer.*",
    "**/*_Companion*.*",
    "**/*Hilt*.*",
    "**/Hilt_*.*",
    "**/*_HiltModules*",
    "**/Dagger*.*"
)

// --- 1. RAPPORT POUR LES TESTS UNITAIRES (JUnit) ---
tasks.register<JacocoReport>("jacocoUnitTestReport") {
    group = "Reporting"
    description = "Génère le rapport de couverture pour les tests unitaires JUnit."

    dependsOn(":app:testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/unitTests"))
    }

    classDirectories.setFrom(files(subprojects.map { proj ->
        val buildDir = proj.layout.buildDirectory
        listOf(
            fileTree(buildDir.dir("intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes")) { exclude(fileFilter) },
            fileTree(buildDir.dir("intermediates/javac/debug/compileDebugJavaWithJavac/classes")) { exclude(fileFilter) }
        )
    }))

    sourceDirectories.setFrom(files(subprojects.map { proj ->
        listOf(proj.file("src/main/java"), proj.file("src/main/kotlin"))
    }))

    executionData.setFrom(files(subprojects.map { proj ->
        fileTree(proj.layout.buildDirectory) {
            include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
        }
    }))
}

// --- 2. RAPPORT POUR LES TESTS D'INTÉGRATION (Cucumber / Espresso) ---
// Ajouter -Pcucumber à la commande ./gradlew jacocoIntegrationTestReport pour générer le rapport des
// test cucumber pour générer le rapport spécifique pour ces test puis relancer la commande pour les
// autre test d'integration et avoir le raport global pour les test d'integration
tasks.register<JacocoReport>("jacocoIntegrationTestReport") {
    group = "Reporting"
    description = "Génère le rapport de couverture pour les tests d'intégration (UI/Cucumber)."

    dependsOn(":app:createDebugCoverageReport")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/integrationTests"))
    }

    classDirectories.setFrom(files(subprojects.map { proj ->
        val buildDir = proj.layout.buildDirectory
        listOf(
            fileTree(buildDir.dir("intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes")) { exclude(fileFilter) },
            fileTree(buildDir.dir("intermediates/javac/debug/compileDebugJavaWithJavac/classes")) { exclude(fileFilter) }
        )
    }))

    sourceDirectories.setFrom(files(subprojects.map { proj ->
        listOf(proj.file("src/main/java"), proj.file("src/main/kotlin"))
    }))

    executionData.setFrom(files(subprojects.map { proj ->
        fileTree(proj.layout.buildDirectory) {
            include("outputs/code_coverage/debugAndroidTest/connected/**/*.ec")
        }
    }))
}

