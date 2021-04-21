package com.koleychik.core_notifications.di

import android.content.Context
import com.koleychik.core_notifications.NotificationCoreApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NotificationCoreModule::class, NotificationCoreRepositoryModule::class])
@Singleton
interface NotificationCoreComponent : NotificationCoreApi {
    companion object {
        fun get(context: Context) = DaggerNotificationCoreComponent.builder()
            .notificationCoreModule(NotificationCoreModule(context))
            .build()
    }
}