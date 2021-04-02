package com.koleychik.feature_sign_up.di

import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object SignUpFeatureComponentHolder :
    ComponentHolder<SignUpFeatureApi, SignUpFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: SignUpFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(dependenciesUp: SignUpFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(SignUpFeatureComponentHolder::class.java) {
            if (component == null) component = SignUpFeatureComponent.initAndGet(dependenciesUp)
        }
    }

    override fun get(): SignUpFeatureApi = component!!

    override fun reset() {
        component = null
    }
}