package com.kaleichyk.feature_searching.di

import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object SearchingFeatureComponentHolder :
    ComponentHolder<SearchingFeatureApi, SearchingFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: SearchingFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(dependencies: SearchingFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(SearchingFeatureComponentHolder::class.java) {
            if (component == null) component =
                DaggerSearchingFeatureComponent.builder()
                    .searchingFeatureDependencies(dependencies)
                    .build()
        }
    }

    override fun get(): SearchingFeatureApi = component!!

    override fun reset() {
        component = null
    }
}