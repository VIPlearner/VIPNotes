pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    versionCatalogs {
        create("libs") { from(files("gradle/dependencies/app_dependencies.toml")) }
    }
}

rootProject.name = "notes"
include(":app")
include(":common")
include(":common:data")
include(":common:domain")
include(":common:presentation")
include(":feature")
include(":navigation")
include(":feature:home")
