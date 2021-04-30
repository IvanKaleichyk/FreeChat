package com.koleychik.freechat.di.modules

import com.kaleichyk.feature_user_info.di.UserInfoFeatureDestroyer
import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureDestroyer
import com.koleychik.feature_loading_impl.di.LoadingFeatureComponentHolder
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureDestroyer
import com.koleychik.feature_sign.di.SignFeatureDestroyer
import com.koleychik.module_injector.injections.BaseDestroyer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DestroyersModule {

    @Provides
    fun provide() = object : UserInfoFeatureDestroyer {
        override fun destroy() {
            LoadingFeatureComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun provideAllDialogsFeatureDestroyer() = object : AllDialogsFeatureDestroyer {
        override fun destroy() {
            LoadingFeatureComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun providePasswordUtilsFeatureDestroyer() = object : PasswordUtilsFeatureDestroyer {
        override fun destroy() {
            LoadingFeatureComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun provideSignFeatureDestroyer() = object : SignFeatureDestroyer {
        override fun destroy() {
            LoadingFeatureComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun provideBaseDestroyer() = object : BaseDestroyer {}

}