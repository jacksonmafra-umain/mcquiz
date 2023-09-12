@Suppress("DSL_SCOPE_VIOLATION") // because of https://youtrack.jetbrains.com/issue/KTIJ-19369

plugins {
  //trick: for the same plugin versions in all sub-modules
  alias(libs.plugins.android.application).apply(false)
  alias(libs.plugins.kotlin.native.cocoapods).apply(false)
  alias(libs.plugins.kotlin.parcelize).apply(false)
  alias(libs.plugins.multiplatform).apply(false)
  alias(libs.plugins.kotlin.android).apply(false)
  alias(libs.plugins.android.library).apply(false)
  alias(libs.plugins.kotlin.serialization).apply(false)
}

val javaVersion by extra { JavaVersion.VERSION_17 }

buildscript {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
