plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "metaborg-gradle-project"

includeBuild("metaborg-gradle-plugin/")