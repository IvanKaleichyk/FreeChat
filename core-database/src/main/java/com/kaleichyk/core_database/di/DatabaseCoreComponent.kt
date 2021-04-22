package com.kaleichyk.core_database.di

import dagger.Component

@Component(modules = [DatabaseCoreModule::class])
interface DatabaseCoreComponent : DatabaseCoreApi {

    companion object {
        fun get() = DaggerDatabaseCoreComponent.builder().build()
    }

}