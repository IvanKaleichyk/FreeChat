package com.kaleichyk.core_database.di

import dagger.Component
import javax.inject.Singleton

@Component(modules = [DatabaseCoreModule::class])
@Singleton
interface DatabaseCoreComponent : DatabaseCoreApi {

    companion object {
        @Volatile
        private var component: DatabaseCoreComponent? = null

        fun get(): DatabaseCoreApi {
            if (component == null) synchronized(DatabaseCoreComponent::class.java) {
                if (component == null) component = DaggerDatabaseCoreComponent.builder().build()
            }
            return component!!
        }
    }

}