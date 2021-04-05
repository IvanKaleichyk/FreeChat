package com.koleychik.freechat.di.modules

import android.content.Context
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureApi
import com.koleychik.feature_sign.di.SignFeatureApi
import com.koleychik.feature_start.di.StartFeatureApi
import com.koleychik.freechat.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun provideContext() = context.applicationContext

    @Singleton
    @Provides
    fun provideNavigator(
        startFeatureApi: Provider<StartFeatureApi>,
        signFeatureApi: Provider<SignFeatureApi>,
        passwordUtilsFeatureApi: Provider<PasswordUtilsFeatureApi>
    ) = Navigator(startFeatureApi, signFeatureApi, passwordUtilsFeatureApi)

}