pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Fleet Tracker Test"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":core:utils")
include(":feature:maps")
include(":feature:dashboard")
include(":core:navigation")
include(":feature:common")
