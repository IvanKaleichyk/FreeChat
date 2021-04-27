package com.koleychik.freechat.di.modules

import com.koleychik.feature_all_dialogs.di.AllDialogsFeatureDestroyer
import com.koleychik.feature_loading_impl.di.LoadingFeatureComponentHolder
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureDestroyer
import com.koleychik.feature_sign.di.SignFeatureDestroyer
import com.koleychik.module_injector.injections.BaseDestroyer
import dagger.Module
import dagger.Provides

@Module
class DestroyersModule {

    @Provides
    fun provideAllDialogsFeatureDestroyer() = object : AllDialogsFeatureDestroyer {
        override fun destroy() {
            LoadingFeatureComponentHolder.reset()
        }
    }

    @Provides
    fun providePasswordUtilsFeatureDestroyer() = object : PasswordUtilsFeatureDestroyer {
        override fun destroy() {
            LoadingFeatureComponentHolder.reset()
        }
    }

    @Provides
    fun provideSignFeatureDestroyer() = object : SignFeatureDestroyer {
        override fun destroy() {
            LoadingFeatureComponentHolder.reset()
        }
    }

    @Provides
    fun provideBaseDestroyer() = object : BaseDestroyer {}

}