package com.koleychik.freechat.di.modules

import android.content.Context
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.core_database.di.DatabaseCoreComponent
import com.kaleichyk.feature_searching.SearchingFeatureNavigationApi
import com.kaleichyk.feature_searching.di.SearchingFeatureDependencies
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.di.AuthenticationCoreComponent
import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureDependencies
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_loading_api.LoadingFeatureApi
import com.koleychik.feature_password_utils.SpecifyEmailNavigationApi
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureDependencies
import com.koleychik.feature_sign.di.SignFeatureDependencies
import com.koleychik.feature_sign.navigation.SignInNavigationApi
import com.koleychik.feature_sign.navigation.SignUpNavigationApi
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
    fun provideSearchingFeatureDependencies(navigator: Navigator) =
        object : SearchingFeatureDependencies {
            override fun usersRepository(): UsersRepository =
                DatabaseCoreComponent.get().usersRepository()

            override fun navigation(): SearchingFeatureNavigationApi = navigator
        }

    @Singleton
    @Provides
    fun provideAllDialogsFeatureDependencies(
        navigator: Navigator,
        loadingFeatureApi: LoadingFeatureApi
    ) = object : AllDialogsFeatureDependencies {
        override fun navigationApi(): AllDialogFeatureNavigationApi = navigator

        override fun repository(): DialogsRepository =
            DatabaseCoreComponent.get().dialogsRepository()

        override fun loadingApi(): LoadingApi = loadingFeatureApi.loadingApi()

    }

    @Singleton
    @Provides
    fun providePasswordUtilsFeatureDependencies(
        context: Context,
        loadingFeatureApi: LoadingFeatureApi,
        navigator: Navigator
    ) =
        object : PasswordUtilsFeatureDependencies {
            override fun loadingApi(): LoadingApi = loadingFeatureApi.loadingApi()

            override fun authRepository() =
                AuthenticationCoreComponent.get(context).authRepository()

            override fun navigationApi(): SpecifyEmailNavigationApi = navigator
        }

    @Singleton
    @Provides
    fun provideStartDependencies(navigator: Navigator, context: Context) =
        object : StartFeatureDependencies {
            val component = AuthenticationCoreComponent.get(context)
            override fun authRepository(): AuthRepository =
                component.authRepository()

            override fun accountRepository(): AccountRepository = component.accountRepository()


            override fun navigation(): StartFeatureNavigation = navigator
        }

    @Singleton
    @Provides
    fun provideSignFeatureDependencies(
        context: Context,
        navigator: Navigator,
        loadingFeatureApi: LoadingFeatureApi
    ) =
        object : SignFeatureDependencies {
            override fun authRepository(): AuthRepository =
                AuthenticationCoreComponent.get(context).authRepository()

            override fun navigationSignUp(): SignUpNavigationApi = navigator

            override fun navigationSignIn(): SignInNavigationApi = navigator

            override fun loadingApi(): LoadingApi = loadingFeatureApi.loadingApi()

        }

    @Singleton
    @Provides
    fun provideBaseDependency() = object : BaseDependencies {}

}