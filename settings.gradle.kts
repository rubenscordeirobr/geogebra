pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    includeBuild("source/build-logic")
}
includeBuild("source/shared")
includeBuild("source/desktop")
includeBuild("source/web")
