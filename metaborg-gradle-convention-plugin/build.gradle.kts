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
    website.set("https://github.com/metaborg/metaborg-gradle-convention")
    vcsUrl.set("https://github.com/metaborg/metaborg-gradle-convention")
    plugins {
        create("MetaborgCompositeBuildPlugin") {
            id = "org.metaborg.gradle-convention.compositebuild"
            implementationClass = "org.metaborg.convention.MetaborgCompositeBuildPlugin"
        }
        create("MetaborgJavaPlugin") {
            id = "org.metaborg.gradle-convention.java"
            implementationClass = "org.metaborg.convention.MetaborgJavaPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            // TIP: publishToMavenLocal does not create checksum files in ~/.m2/repository. If you want to
            // verify that the checksum files are created correctly, publish to LocalDebug instead.
            name = "LocalDebug"
            url = uri(layout.buildDirectory.dir("repositories/localDebug"))
        }
        maven {
            val stagingRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            name = "OSSRH"
            url = if (project.extra["isReleaseVersion"] as Boolean) stagingRepoUrl else snapshotsRepoUrl
            credentials {
                username = project.findProperty("ossrh.user") as String? ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("ossrh.token") as String? ?: System.getenv("OSSRH_TOKEN")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/metaborg/metaborg-gradle-convention")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            val releasesRepoUrl = uri("https://artifacts.metaborg.org/content/repositories/releases/")
            val snapshotsRepoUrl = uri("https://artifacts.metaborg.org/content/repositories/snapshots/")
            name = "MetaborgArtifacts"
            url = if (project.extra["isReleaseVersion"] as Boolean) releasesRepoUrl else snapshotsRepoUrl
            credentials {
                username = project.findProperty("metaborg-artifacts.username") as String?
                password = project.findProperty("metaborg-artifacts.password") as String?
            }
        }
    }
}



tasks.withType<Sign>().configureEach {
    onlyIf("Signing keyId is set") { project.hasProperty("signing.keyId") }
}
