package com.koleychik.freechat.di.modules

import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureDestroyer
import com.koleychik.feature_sign.di.SignFeatureDestroyer
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import com.koleychik.module_injector.injections.BaseDestroyer
import dagger.Module
import dagger.Provides

@Module
class DestroyersModule {

    @Provides
    fun providePasswordUtilsFeatureDestroyer() = object : PasswordUtilsFeatureDestroyer {
        override fun destroy() {
            StartFeatureComponentHolder.reset()
        }
    }

    @Provides
    fun provideSignFeatureDestroyer() = object : SignFeatureDestroyer {
        override fun destroy() {
            StartFeatureComponentHolder.reset()
        }
    }

    @Provides
    fun provideBaseDestroyer() = object : BaseDestroyer {}

}