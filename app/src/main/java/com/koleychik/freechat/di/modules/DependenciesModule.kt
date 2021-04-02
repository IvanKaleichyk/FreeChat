package com.koleychik.freechat.di.modules

import com.koleychik.core_authorization.di.AuthorizationCoreComponent
import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_loading_api.LoadingFeatureApi
import com.koleychik.feature_sign.SignFeatureNavigation
import com.koleychik.feature_sign.di.SignFeatureDependencies
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.feature_start.di.StartFeatureDependencies
import com.koleychik.freechat.Navigator
import com.koleychik.module_injector.injections.BaseDependencies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DependenciesModule {

    @Singleton
    @Provides
    fun provideStartDependencies(navigator: Navigator) = object : StartFeatureDependencies {
        override fun authRepository(): AuthRepository =
            AuthorizationCoreComponent.get().authRepository()

        override fun navigation(): StartFeatureNavigation = navigator
    }

    @Singleton
    @Provides
    fun provideSignFeatureDependencies(navigator: Navigator, loadingFeatureApi: LoadingFeatureApi) =
        object : SignFeatureDependencies {
            override fun authRepository(): AuthRepository =
                AuthorizationCoreComponent.get().authRepository()

            override fun navigation(): SignFeatureNavigation = navigator
            override fun loadingApi(): LoadingApi = loadingFeatureApi.loadingApi()

        }

    @Singleton
    @Provides
    fun provideBaseDependency() = object : BaseDependencies {}

}