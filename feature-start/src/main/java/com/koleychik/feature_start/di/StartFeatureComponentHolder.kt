package com.koleychik.feature_start.di

import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object StartFeatureComponentHolder :
    ComponentHolder<StartFeatureApi, StartFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: StartFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(dependencies: StartFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(StartFeatureComponentHolder::class.java) {
            if (component == null) component = StartFeatureComponent.initAndGet(dependencies)
        }
    }

    override fun get(): StartFeatureApi = component!!

    override fun reset() {
        component = null
    }
}