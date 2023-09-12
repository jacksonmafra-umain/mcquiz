/* Library Specs */

val iosDeploymentTarget: String by project
val libBaseGroup: String by project
val libBaseName: String by project
val libBaseVersion: String by project
val libCocoaPodsSourcePublish: String by project
val libDeveloperOrg: String by project
val libGitUrl: String by project
val libSiteUrl: String by project
val libAndroidNamespace: String by project

plugins {
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.ksp)
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.multiplatform)
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.compose)
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.cocoapods)
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.android.library)
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  android()
  iosX64()
  iosArm64()
  iosSimulatorArm64()

  cocoapods {
    summary = "Some description for the $libBaseName."
    homepage = libSiteUrl
    ios.deploymentTarget = iosDeploymentTarget
    authors = libDeveloperOrg
    version = libBaseVersion
    license = "Unlicensed"
    podfile = project.file("../iosApp/Podfile")
    source = "{ :git => '$libGitUrl', :tag => '$libBaseVersion' }"
    extraSpecAttributes["vendored_frameworks"] = "'$libBaseName.xcframework'"
    xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] =
      org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    framework {
      baseName = "shared"
      isStatic = true
    }
    extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    specRepos {
      url("https://github.com/Kotlin/kotlin-cocoapods-spec.git")
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {

        implementation(libs.napier)
        implementation(libs.koin.core)

        implementation(libs.bundles.ktor)
        implementation(libs.ktor.serialization.json)
        implementation(libs.ktor.content.negotiation)
        implementation(libs.kotlinx.serialization.json)

        // KotlinX
        implementation(libs.coroutines.core)

        implementation(libs.sharedStorage.multiplatform)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.coroutines.core)
        implementation(libs.coroutines.android)
        implementation(libs.ktor.android)
        implementation(libs.ktor.okhttp)
        implementation(libs.koin.core)
        implementation(libs.koin.android)
        implementation(libs.koin.compose)
        implementation(libs.koin.androidx.compose)
        implementation(libs.sharedStorage.crypto)
      }
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
      dependencies {
        implementation(libs.ktor.darwin)
        implementation(libs.ktor.ios)
      }
    }

    val iosX64Test by getting
    val iosArm64Test by getting
    val iosSimulatorArm64Test by getting
    val iosTest by creating {
      dependsOn(commonTest)
      iosX64Test.dependsOn(this)
      iosArm64Test.dependsOn(this)
      iosSimulatorArm64Test.dependsOn(this)
    }
  }
}

android {
  namespace = findProperty("android.internalApplicationId") as String
  compileSdk = (findProperty("android.compileSdk") as String).toInt()

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  sourceSets["main"].res.srcDirs("src/androidMain/res")
  sourceSets["main"].resources.srcDirs("src/commonMain/resources")

  defaultConfig {
    minSdk = (findProperty("android.minSdk") as String).toInt()
    targetSdk = (findProperty("android.targetSdk") as String).toInt()
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlin {
    jvmToolchain(11)
  }
  packagingOptions {
    resources.excludes.add("META-INF/**")
  }
}
