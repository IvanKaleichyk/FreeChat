package plugins.setup

import Versions
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryDefaultConfig : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.configure(BaseExtension::class.java) { extension ->
            extension.run {
                compileSdkVersion(Versions.sdkVersion)
                buildToolsVersion(Versions.buildToolsVersion)
                defaultConfig { config ->
                    config.run {
//                        applicationId = "com.koleychik.freechat"
                        minSdkVersion(Versions.minSdkVersion)
                        targetSdkVersion(Versions.targetSdk)
                        versionCode(Versions.versionCode)
                        versionName(Versions.versionName)

                        testInstrumentationRunner(Versions.testInstrumentationRunner)
                        consumerProguardFiles("consumer-rules.pro")
                    }
                }
            }
        }
    }
}