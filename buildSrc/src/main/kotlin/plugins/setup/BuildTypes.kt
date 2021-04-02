package plugins.setup

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildTypes : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.configure(BaseExtension::class.java) { extension ->
            extension.buildTypes { buildType ->
                buildType.getByName("release") { release ->
                    release.run {
                        isMinifyEnabled = false
                        proguardFiles(
                            extension.getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }
    }
}