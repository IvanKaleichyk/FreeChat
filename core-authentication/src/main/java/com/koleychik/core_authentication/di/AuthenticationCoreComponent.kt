package com.koleychik.core_authentication.di

import android.content.Context
import com.koleychik.module_injector.annotations.general.PerFeature
import dagger.Component

@Component(modules = [CoreModule::class, ContextModule::class])
@PerFeature
interface AuthenticationCoreComponent : AuthenticationCoreApi {

    companion object {
        @Volatile
        private var component: AuthenticationCoreComponent? = null
        fun get(context: Context): AuthenticationCoreApi {
            if (component == null) synchronized(AuthenticationCoreComponent::class.java) {
                if (component == null) component =
                    DaggerAuthenticationCoreComponent.builder().contextModule(ContextModule(context))
                        .build()
            }
            return component!!
        }
    }

}