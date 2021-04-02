package plugins.project

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
class AndroidFeature : Plugin<Project>{
    override fun apply(project: Project) {
        project.plugins.run {
            apply(AndroidLibraryConvention::class.java)
            apply("kotlin-kapt")
        }
        project.extensions.configure(BaseExtension::class.java){
            it.buildFeatures.viewBinding = true
        }
    }
}