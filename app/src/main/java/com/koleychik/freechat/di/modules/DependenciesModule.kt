package com.koleychik.freechat.di.modules

import android.content.Context
import com.kaleichyk.core_cloud_storage.di.CloudStorageCoreComponent
import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.MessagesRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.core_database.di.DatabaseCoreComponent
import com.kaleichyk.feature_messages.di.MessagesFeatureDependencies
import com.kaleichyk.feature_searching.SearchingFeatureNavigationApi
import com.kaleichyk.feature_searching.di.SearchingFeatureDependencies
import com.kaleichyk.feature_user_info.UserInfoNavigationApi
import com.kaleichyk.feature_user_info.di.UserInfoFeatureDependencies
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.di.AuthenticationCoreComponent
import com.koleychik.core_notifications.di.NotificationCoreComponent
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
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
    fun provideMessagesFeatureDependencies(context: Context) =
        object : MessagesFeatureDependencies {
            override fun context(): Context = context

            override fun messageNotificationRepository(): MessageNotificationRepository =
                NotificationCoreComponent.get(context).messageNotificationRepository()

            override fun messagesRepository(): MessagesRepository =
                DatabaseCoreComponent.get().messagesRepository()

            override fun dialogsRepository(): DialogsRepository =
                DatabaseCoreComponent.get().dialogsRepository()

        }

    @Singleton
    @Provides
    fun provide(context: Context, loadingFeatureApi: LoadingFeatureApi, navigator: Navigator) =
        object : UserInfoFeatureDependencies {
            val databaseCore = DatabaseCoreComponent.get()
            override fun usersRepository(): UsersRepository = databaseCore.usersRepository()

            override fun dialogsRepository(): DialogsRepository = databaseCore.dialogsRepository()

            override fun accountRepository(): AccountRepository =
                AuthenticationCoreComponent.get(context).accountRepository()

            override fun cloudStorageRepository(): CloudStorageRepository =
                CloudStorageCoreComponent.get(context).cloudStorageRepository()

            override fun loadingApi(): LoadingApi = loadingFeatureApi.loadingApi()

            override fun userInfoNavigationApi(): UserInfoNavigationApi = navigator

        }

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