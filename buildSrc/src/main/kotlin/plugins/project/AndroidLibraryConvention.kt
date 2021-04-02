package plugins.project

import org.gradle.api.Plugin
import org.gradle.api.Project
import plugins.setup.BaseKotlinConvention
import plugins.setup.BuildTypes
import plugins.setup.CompileOptions
import plugins.setup.LibraryDefaultConfig

class AndroidLibraryConvention: Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.run {
            apply("com.android.library")
            apply("kotlin-android")
            apply(LibraryDefaultConfig::class.java)
            apply(BuildTypes::class.java)
            apply(CompileOptions::class.java)
            apply(BaseKotlinConvention::class.java)
        }
    }
}