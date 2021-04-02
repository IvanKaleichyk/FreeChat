package plugins.project

import org.gradle.api.Plugin
import org.gradle.api.Project
import plugins.setup.AppDefaultConfig
import plugins.setup.BaseKotlinConvention
import plugins.setup.BuildTypes
import plugins.setup.CompileOptions

class AndroidAppConvention : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.run {
            apply("com.android.application")
            apply("kotlin-android")
            apply(AppDefaultConfig::class.java)
            apply(BuildTypes::class.java)
            apply(CompileOptions::class.java)
            apply(BaseKotlinConvention::class.java)
        }
    }
}