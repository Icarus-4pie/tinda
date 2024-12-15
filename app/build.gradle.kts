// File: build.gradle.kts (app-level)
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.tinda"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tinda"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}


dependencies {
    implementation(libs.appcompat)  // Version catalog example
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    // Firestore dependency
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.google.firebase:firebase-auth:21.0.8")
    implementation ("com.google.firebase:firebase-analytics")
    implementation(libs.firebase.analytics)  // Realtime Database dependency
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    releaseImplementation("com.example.library:1.0.0")

}

apply(plugin = "com.google.gms.google-services")