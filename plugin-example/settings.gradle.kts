pluginManagement {
    includeBuild("../metaborg-convention-plugin/")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "plugin-example"

dependencyResolutionManagement {
    repositories.gradlePluginPortal()
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
