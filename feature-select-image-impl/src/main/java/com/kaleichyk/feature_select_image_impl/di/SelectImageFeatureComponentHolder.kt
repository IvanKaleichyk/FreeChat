package com.kaleichyk.feature_select_image_impl.di

import com.koleychik.module_injector.injections.BaseDependencies
import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object SelectImageFeatureComponentHolder :
    ComponentHolder<SelectImageFeatureApi, BaseDependencies, BaseDestroyer> {

    @Volatile
    private var component: SelectImageFeatureComponent? = null

    override fun init(dependencies: BaseDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(SelectImageFeatureComponentHolder::class.java){
            if (component == null) component = DaggerSelectImageFeatureComponent.builder().build()
        }
    }

    override fun get(): SelectImageFeatureApi = component!!

    override fun reset() {
        component = null
    }
}