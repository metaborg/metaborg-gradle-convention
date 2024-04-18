package org.metaborg.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*

/**
 * Configures the Java plugin, the Java compiler, the sources and javadoc jar tasks,
 * the repositories (for dependencies), and the publication of the Java component.
 */
@Suppress("unused")
class MetaborgJavaPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configure()
    }

    private fun Project.configure() {
        // Apply the Java plugin
        pluginManager.apply("java")

        // Set default repositories
        repositories {
            mavenCentral()
        }

        // TODO: Replace this by jvmToolchain(11)
        configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        // TODO: Replace this by jvmToolchain(11)
        if (JavaVersion.current().isJava9Compatible) {
            // JDK9 or higher: also set the release flag
            tasks.withType<JavaCompile> {
                options.compilerArgs.addAll(listOf("--release", "11"))
            }
        }

        // Ensure Java files are UTF-8 encoded
        tasks.withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
        }

        // Use JUnit platform by default for testing
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }

        // Enable sources and JavaDoc jars
        configure<JavaPluginExtension> {
            withSourcesJar()
            withJavadocJar()
        }
    }
}