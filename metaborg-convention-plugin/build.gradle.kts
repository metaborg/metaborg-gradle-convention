plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    alias(libs.plugins.gradle.publish)
}

group = "org.metaborg"
description = "Metaborg convention plugin for Gradle."
extra["isReleaseVersion"] = !version.toString().endsWith("-SNAPSHOT")

dependencies {
    // Testing
    testImplementation(gradleTestKit())
}


gradlePlugin {
    website.set("https://github.com/metaborg/metaborg-gradle")
    vcsUrl.set("https://github.com/metaborg/metaborg-gradle")
    plugins {
        create("MetaborgCompositeBuildPlugin") {
            id = "org.metaborg.convention.compositebuild"
            implementationClass = "org.metaborg.convention.MetaborgCompositeBuildPlugin"
        }
        create("MetaborgJavaPlugin") {
            id = "org.metaborg.convention.java"
            implementationClass = "org.metaborg.convention.MetaborgJavaPlugin"
        }
    }
}
