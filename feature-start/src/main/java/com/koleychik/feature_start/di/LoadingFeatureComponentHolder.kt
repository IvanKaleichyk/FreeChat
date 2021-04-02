package com.koleychik.feature_start.di

import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object LoadingFeatureComponentHolder :
    ComponentHolder<LoadingFeatureApi, LoadingFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: LoadingFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(dependencies: LoadingFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(LoadingFeatureComponentHolder::class.java) {
            if (component == null) component = LoadingFeatureComponent.initAndGet(dependencies)
        }
    }

    override fun get(): LoadingFeatureApi = component!!

    override fun reset() {
        component = null
    }
}