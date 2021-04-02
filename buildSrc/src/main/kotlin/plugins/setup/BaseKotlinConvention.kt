package plugins.setup

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BaseKotlinConvention : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.withType(KotlinCompile::class.java) {
            it.kotlinOptions.jvmTarget = "1.8"
        }
    }
}