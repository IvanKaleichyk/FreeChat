package plugins.setup

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class CompileOptions : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.configure(BaseExtension::class.java) {
            it.compileOptions { compile ->
                compile.run {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }
        }
    }
}