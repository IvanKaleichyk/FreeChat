package com.koleychik.feature_sing.di

import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object SignFeatureComponentHolder :
    ComponentHolder<SignFeatureApi, SignFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: SignFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(dependencies: SignFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(SignFeatureComponentHolder::class.java) {
            if (component == null) component = SignFeatureComponent.initAndGet(dependencies)
        }
    }

    override fun get(): SignFeatureApi = component!!

    override fun reset() {
        component = null
    }
}