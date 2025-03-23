// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Root-level build.gradle.kts — do NOT apply android plugins here

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }
}