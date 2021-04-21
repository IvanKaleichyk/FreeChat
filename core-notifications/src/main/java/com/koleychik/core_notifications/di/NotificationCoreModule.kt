package com.koleychik.core_notifications.di

import android.content.Context
import com.koleychik.core_notifications.network.RetrofitInstance
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class NotificationCoreModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideNotificationServiceApi() = RetrofitInstance.api

}