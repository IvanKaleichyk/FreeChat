package com.koleychik.freechat.di.modules

import android.content.Context
import com.kaleichyk.feature_messages.di.MessagesFeatureApi
import com.kaleichyk.feature_searching.di.SearchingFeatureApi
import com.kaleichyk.feature_user_info.di.UserInfoFeatureApi
import com.koleychik.core_authentication.di.AuthenticationCoreComponent
import com.koleychik.core_notifications.di.NotificationCoreComponent
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureApi
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureApi
import com.koleychik.feature_sign.di.SignFeatureApi
import com.koleychik.feature_start.di.StartFeatureApi
import com.koleychik.freechat.Navigator
import com.koleychik.freechat.managers.MainManager
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun provideMainManager() = MainManager(
        AuthenticationCoreComponent.get(context).accountRepository(),
        NotificationCoreComponent.get(context).cloudMessagingRepository()
    )

    @Singleton
    @Provides
    fun provideContext(): Context = context.applicationContext

    @Singleton
    @Provides
    fun provideNavigator(
        context: Context,
        startFeatureApi: Provider<StartFeatureApi>,
        signFeatureApi: Provider<SignFeatureApi>,
        passwordUtilsFeatureApi: Provider<PasswordUtilsFeatureApi>,
        allDialogsFeatureApi: Provider<AllDialogsFeatureApi>,
        searchingFeatureApi: Provider<SearchingFeatureApi>,
        userInfoFeatureApi: Provider<UserInfoFeatureApi>,
        messagesFeatureApi: Provider<MessagesFeatureApi>
    ) = Navigator(
        context,
        startFeatureApi,
        signFeatureApi,
        passwordUtilsFeatureApi,
        allDialogsFeatureApi,
        searchingFeatureApi,
        userInfoFeatureApi,
        messagesFeatureApi
    )

}