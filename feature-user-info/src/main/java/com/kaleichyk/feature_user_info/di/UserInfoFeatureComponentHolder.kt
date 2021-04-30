package com.kaleichyk.feature_user_info.di

import com.koleychik.module_injector.injections.ComponentHolder

object UserInfoFeatureComponentHolder :
    ComponentHolder<UserInfoFeatureApi, UserInfoFeatureDependencies, UserInfoFeatureDestroyer> {

    @Volatile
    private var component: UserInfoFeatureComponent? = null

    internal fun getComponent() = component!!

    private lateinit var destroyer: UserInfoFeatureDestroyer

    override fun init(
        dependencies: UserInfoFeatureDependencies,
        destroyer: UserInfoFeatureDestroyer
    ) {
        if (component == null) synchronized(UserInfoFeatureComponentHolder::class.java) {
            if (component == null) component =
                DaggerUserInfoFeatureComponent.builder()
                    .userInfoFeatureDependencies(dependencies)
                    .build()
        }
        this.destroyer = destroyer
    }

    override fun get(): UserInfoFeatureApi = component!!

    override fun reset() {
        destroyer.destroy()
    }
}