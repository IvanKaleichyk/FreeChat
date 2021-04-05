package com.koleychik.feature_password_utils.di

import com.koleychik.module_injector.injections.ComponentHolder

object PasswordUtilsFeatureComponentHolder :
    ComponentHolder<PasswordUtilsFeatureApi, PasswordUtilsFeatureDependencies, PasswordUtilsFeatureDestroyer> {

    @Volatile
    private var component: PasswordUtilsFeatureComponent? = null

    internal fun getComponent() = component!!

    private lateinit var destroyer: PasswordUtilsFeatureDestroyer

    override fun init(
        dependencies: PasswordUtilsFeatureDependencies,
        destroyer: PasswordUtilsFeatureDestroyer
    ) {
        if (component == null) synchronized(PasswordUtilsFeatureComponentHolder::class.java) {
            if (component == null) component =
                PasswordUtilsFeatureComponent.initAndGet(dependencies)
        }
        this.destroyer = destroyer
    }

    override fun get(): PasswordUtilsFeatureApi = component!!

    override fun reset() {
        destroyer.destroy()
        component = null
    }
}