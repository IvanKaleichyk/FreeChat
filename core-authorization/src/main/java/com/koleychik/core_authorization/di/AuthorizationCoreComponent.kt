package com.koleychik.core_authorization.di

import com.koleychik.module_injector.annotations.general.PerFeature
import dagger.Component

@Component(modules = [CoreModule::class])
@PerFeature
interface AuthorizationCoreComponent : AuthorizationCoreApi {

    companion object {
        @Volatile
        private var component: AuthorizationCoreComponent? = null
        fun get(): AuthorizationCoreApi {
            if (component == null) synchronized(AuthorizationCoreComponent::class.java) {
                if (component == null) component =
                    DaggerAuthorizationCoreComponent.builder().build()
            }
            return component!!
        }
    }

}