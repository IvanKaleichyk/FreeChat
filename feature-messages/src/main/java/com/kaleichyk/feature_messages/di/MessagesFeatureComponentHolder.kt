package com.kaleichyk.feature_messages.di

import com.koleychik.module_injector.injections.BaseDestroyer
import com.koleychik.module_injector.injections.ComponentHolder

object MessagesFeatureComponentHolder :
    ComponentHolder<MessagesFeatureApi, MessagesFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: MessagesFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(dependencies: MessagesFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(MessagesFeatureComponentHolder::class.java) {
            if (component == null) component = createComponent(dependencies)
        }
    }

    override fun get(): MessagesFeatureApi = component!!

    override fun reset() {
        component = null
    }

    private fun createComponent(dependencies: MessagesFeatureDependencies) =
        DaggerMessagesFeatureComponent
            .builder()
            .messagesFeatureDependencies(dependencies)
            .build()
}