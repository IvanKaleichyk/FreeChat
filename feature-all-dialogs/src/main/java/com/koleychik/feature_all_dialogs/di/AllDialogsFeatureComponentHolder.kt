package com.koleychik.feature_all_dialogs.di

import com.koleychik.module_injector.injections.ComponentHolder

object AllDialogsFeatureComponentHolder :
    ComponentHolder<AllDialogsFeatureApi, AllDialogsFeatureDependencies, AllDialogsFeatureDestroyer> {

    private var destroyer: AllDialogsFeatureDestroyer? = null

    @Volatile
    private var component: AllDialogsFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(
        dependencies: AllDialogsFeatureDependencies,
        destroyer: AllDialogsFeatureDestroyer
    ) {
        if (component == null) synchronized(AllDialogsFeatureComponentHolder::class.java) {
            if (component == null) component = getComponent(dependencies)
        }
        this.destroyer = destroyer
    }

    override fun get(): AllDialogsFeatureApi = component!!

    override fun reset() {
        component = null
        destroyer?.destroy()
    }

    private fun getComponent(dependencies: AllDialogsFeatureDependencies) =
        DaggerAllDialogsFeatureComponent.builder()
            .allDialogsFeatureDependencies(dependencies)
            .build()
}