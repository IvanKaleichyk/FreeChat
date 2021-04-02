package com.koleychik.feature_sign.di

import com.koleychik.module_injector.injections.ComponentHolder

object SignFeatureComponentHolder :
    ComponentHolder<SignFeatureApi, SignFeatureDependencies, SignFeatureDestroyer> {

    @Volatile
    private var component: SignFeatureComponent? = null

    private var destroyer: SignFeatureDestroyer? = null

    internal fun getComponent() = component!!

    override fun init(dependencies: SignFeatureDependencies, destroyer: SignFeatureDestroyer) {
        if (component == null) synchronized(SignFeatureComponentHolder::class.java) {
            if (component == null) component = SignFeatureComponent.initAndGet(dependencies)
        }
        this.destroyer = destroyer
    }

    override fun get(): SignFeatureApi = component!!

    override fun reset() {
        component = null
        destroyer?.destroy()
    }
}