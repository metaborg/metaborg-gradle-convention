plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "metaborg-convention-project"

includeBuild("metaborg-gradle-convention-plugin/")
includeBuild("plugin-example/")