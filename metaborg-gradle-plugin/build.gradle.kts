plugins {
    `maven-publish`
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.publish)
}

group = "org.metaborg"
description = "Metaborg shared configuration Gradle plugin."

dependencies {
    // Testing
    testImplementation(libs.kotest)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.property)
    testImplementation(gradleTestKit())
}


gradlePlugin {
    plugins {
        create("MetaborgCompositeBuildPlugin") {
            id = "org.metaborg.metaborg-gradle.compositebuild"
            implementationClass = "org.metaborg.gradle.MetaborgCompositeBuildPlugin"
        }
        create("MetaborgJavaPlugin") {
            id = "org.metaborg.metaborg-gradle.java"
            implementationClass = "org.metaborg.gradle.MetaborgJavaPlugin"
        }
        create("MetaborgKotlinPlugin") {
            id = "org.metaborg.metaborg-gradle.kotlin"
            implementationClass = "org.metaborg.gradle.MetaborgKotlinPlugin"
        }
        create("MetaborgJUnitPlugin") {
            id = "org.metaborg.metaborg-gradle.junit"
            implementationClass = "org.metaborg.gradle.MetaborgJUnitPlugin"
        }
        create("MetaborgPublishPlugin") {
            id = "org.metaborg.metaborg-gradle.publish"
            implementationClass = "org.metaborg.gradle.MetaborgPublishPlugin"
        }
    }
}
