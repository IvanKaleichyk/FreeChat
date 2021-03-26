package com.koleychik.core_files.di

import android.content.Context
import com.koleychik.core_files.FilesCoreApi
import dagger.Component

@Component(modules = [ContextModule::class, CoreModule::class])
interface FilesCoreComponent : FilesCoreApi{

    companion object {
        @Volatile
        private var component: FilesCoreComponent? = null

        fun get(context: Context): FilesCoreComponent {
            if (component == null) synchronized(FilesCoreComponent::class.java) {
                if (component == null) component =
                    DaggerFilesCoreComponent.builder().contextModule(ContextModule(context)).build()
            }
            return component!!
        }
    }

}