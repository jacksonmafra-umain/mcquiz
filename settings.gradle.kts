pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        google()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
        maven {
            url = uri("https://androidx.dev/storage/compose-compiler/repository/")
        }
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        google()

        // LAST
        mavenLocal()
    }
}

rootProject.name = "McQuiz"
include(":androidApp")
include(":shared")
