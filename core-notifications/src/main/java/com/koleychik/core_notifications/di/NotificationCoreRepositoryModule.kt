package com.koleychik.core_notifications.di

import com.koleychik.core_notifications.impl.CloudMessagingRepositoryImpl
import com.koleychik.core_notifications.impl.MessageNotificationRepositoryImpl
import com.koleychik.core_notifications.impl.NotificationRepositoryImpl
import com.koleychik.core_notifications.repositories.CloudMessagingRepository
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.core_notifications.repositories.NotificationRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class NotificationCoreRepositoryModule {

    @Binds
    abstract fun provideCloudMessageRepository(impl: CloudMessagingRepositoryImpl): CloudMessagingRepository

    @Binds
    abstract fun provideMessageNotificationRepository(impl: MessageNotificationRepositoryImpl): MessageNotificationRepository

    @Binds
    abstract fun provideNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

}