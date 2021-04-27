package com.koleychik.freechat.di.modules

import android.content.Context
import com.kaleichyk.feature_searching.di.SearchingFeatureApi
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.di.AuthenticationCoreComponent
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureApi
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureApi
import com.koleychik.feature_sign.di.SignFeatureApi
import com.koleychik.feature_start.di.StartFeatureApi
import com.koleychik.freechat.MainDataSource
import com.koleychik.freechat.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun provideMainDataSource(repository: AccountRepository) = MainDataSource(repository)

    @Singleton
    @Provides
    fun provideContext(): Context = context.applicationContext

    @Singleton
    @Provides
    fun provideAccountRepository(context: Context) =
        AuthenticationCoreComponent.get(context).accountRepository()

    @Singleton
    @Provides
    fun provideNavigator(
        context: Context,
        startFeatureApi: Provider<StartFeatureApi>,
        signFeatureApi: Provider<SignFeatureApi>,
        passwordUtilsFeatureApi: Provider<PasswordUtilsFeatureApi>,
        allDialogsFeatureApi: Provider<AllDialogsFeatureApi>,
        searchingFeatureApi: Provider<SearchingFeatureApi>
    ) = Navigator(
        context,
        startFeatureApi,
        signFeatureApi,
        passwordUtilsFeatureApi,
        allDialogsFeatureApi,
        searchingFeatureApi
    )

}