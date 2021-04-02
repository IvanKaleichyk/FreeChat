package com.koleychik.feature_loading_impl.di

import com.koleychik.feature_loading_api.LoadingFeatureApi
import com.koleychik.module_injector.injections.BaseDependencies
import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object LoadingFeatureComponentHolder :
    ComponentHolder<LoadingFeatureApi, BaseDependencies, BaseDestroyer> {

    @Volatile
    private var component: LoadingFeatureComponent? = null

    override fun init(dependencies: BaseDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(LoadingFeatureComponentHolder::class.java) {
            if (component == null) component = LoadingFeatureComponent.initAndGet()
        }
    }

    override fun get(): LoadingFeatureApi = component!!

    override fun reset() {
        component = null
    }
}