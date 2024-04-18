package org.metaborg.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.invoke

/** Registers composite build tasks that invoke tasks from subprojects and included builds. */
@Suppress("unused")
class MetaborgCompositeBuildPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Create composite build tasks after all projects have been evaluated,
        // to ensure that all tasks in all projects and included builds have been registered.
        project.gradle.projectsEvaluated {
            project.tasks {
                // Build tasks
                registerCompositeBuildTask(
                    "build",
                    project,
                    "buildAll",
                    "Build",
                    "Assembles and tests all projects in the composite build."
                )
                registerCompositeBuildTask(
                    "assemble",
                    project,
                    "assembleAll",
                    "Build",
                    "Assembles the outputs for all projects in the composite build."
                )
                registerCompositeBuildTask(
                    "clean",
                    project,
                    "cleanAll",
                    "Build",
                    "Deletes the build directory for all projects in the composite build."
                )

                // Publishing tasks
                registerCompositeBuildTask(
                    "publish",
                    project,
                    "publishAll",
                    "Publishing",
                    "Publishes all publications produced by all projects in the composite build."
                )
                registerCompositeBuildTask(
                    "publishToMavenLocal",
                    project,
                    "publishAllToMavenLocal",
                    "Publishing",
                    "Publishes all publications produced by all projects in the composite build to Maven local."
                )

                // Verification tasks
                registerCompositeBuildTask(
                    "check",
                    project,
                    "checkAll",
                    "Verification",
                    "Runs all checks for all projects in the composite build."
                )
                registerCompositeBuildTask(
                    "test",
                    project,
                    "testAll",
                    "Verification",
                    "Runs all test for all projects in the composite build."
                )

                // Help tasks
                registerCompositeBuildTask(
                    "tasks",
                    project,
                    "allTasks",
                    "Help",
                    "Displays all tasks of all projects in the composite build."
                )
            }
        }
    }


    /**
     * Registers a composite build task.
     *
     * @param targetName The name of the task in this project, subprojects, and included builds to invoke.
     * @param project The project to which the task is registered.
     * @param name The name of the composite build task.
     * @param description The description of the composite build task.
     */
    private fun TaskContainerScope.registerCompositeBuildTask(
        targetName: String,
        project: Project,
        name: String,
        group: String,
        description: String,
    ) {
        register(name) {
            this.group = group
            this.description = description

            // Depend on the task in this project (if it exists)
            project.tasks.findByName(targetName)?.let {
                this.dependsOn(it)
                project.logger.debug("Composite build task '{}' delegates to '{}' in this project {}", name, it, project)
            }

            // Depend on the tasks in subprojects
            project.subprojects.mapNotNull { it.tasks.findByName(targetName) }.forEach {
                this.dependsOn(it)
                project.logger.debug("Composite build task '{}' delegates to '{}' in subproject {}", name, it, it.project)
            }

            // Depend on the tasks in included builds (if they exist)
            project.gradle.includedBuilds.mapNotNull { try { it to it.task(":$targetName") } catch (e: Throwable) { null } }.forEach { (includedBuild, it) ->
                this.dependsOn(it)
                project.logger.debug("Composite build task '{}' delegates to '{}' in included build {}", name, it, includedBuild)
            }
        }
    }

}