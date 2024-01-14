// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}

ext {
    // Versions
    val activityVersion = "1.4.0"
    val appCompatVersion = "1.4.0"
    val constraintLayoutVersion = "2.1.2"
    val coreTestingVersion = "2.1.0"
    val coroutines = "1.5.2"
    val lifecycleVersion = "2.4.0"
    val materialVersion = "1.4.0"
    val roomVersion = "2.3.0"

    // Testing
    val junitVersion = "4.13.2"
    val espressoVersion = "3.4.0"
    val androidxJunitVersion = "1.1.3"
}