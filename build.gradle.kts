plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gitVersion)          // Set gitVersion() from last Git repository tag
    alias(libs.plugins.benmanesVersions)    // Check for dependency updates
    alias(libs.plugins.testlogger)          // Pretty-print test results live to console
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "com.palantir.git-version")
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "com.adarshr.test-logger")

    val gitVersion: groovy.lang.Closure<String> by extra
    version = gitVersion()

    repositories {
        mavenCentral()
    }

    tasks.test {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(11)
    }

    configure<JavaPluginExtension> {
        withSourcesJar()
        withJavadocJar()
    }
}