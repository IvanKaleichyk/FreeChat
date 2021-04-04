package com.koleychik.core_authentication.di

import android.content.Context
import com.koleychik.module_injector.annotations.general.PerFeature
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    @PerFeature
    fun provideContext() = context.applicationContext

}