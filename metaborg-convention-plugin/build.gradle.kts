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

publishing {
    repositories {
        maven {
            // TIP: publishToMavenLocal does not create checksum files in ~/.m2/repository. If you want to
            // verify that the checksum files are created correctly, publish to LocalDebug instead.
            name = "LocalDebug"
            url = uri(layout.buildDirectory.dir("repositories/localDebug"))
        }
        if (false) {
            maven {
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                name = "MavenCentral"
                url = if (project.extra["isReleaseVersion"] as Boolean) releasesRepoUrl else snapshotsRepoUrl
                credentials {
                    username = project.findProperty("ossrh.user") as String? ?: System.getenv("OSSRH_USERNAME")
                    password = project.findProperty("ossrh.token") as String? ?: System.getenv("OSSRH_TOKEN")
                }
            }
            maven {
                name = "GitHub"
                url = uri("https://maven.pkg.github.com/Virtlink/kode")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
            maven {
                name = "GitLab"
                url = uri("${System.getenv("CI_API_V4_URL")}/projects/6800/packages/maven")
                credentials(HttpHeaderCredentials::class) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
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
