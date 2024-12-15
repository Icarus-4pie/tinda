// File: build.gradle.kts (project-level)
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    alias(libs.plugins.google.gms.google.services) apply false
    // Note: Use id() for the google-services plugin
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Required classpath dependencies
        classpath("com.android.tools.build:gradle:8.7.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("com.google.gms:google-services:4.3.15")
    }
}