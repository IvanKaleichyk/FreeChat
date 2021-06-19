package com.kaleichyk.feature_messages.di

import android.content.Context
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.MessagesRepository
import com.kaleichyk.feature_messages.MessagesManager
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.module_injector.annotations.general.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class FeatureModule {

    @Provides
    @PerFeature
    fun provideMessagesManager(
        context: Context,
        notificationRepository: MessageNotificationRepository,
        messagesRepository: MessagesRepository,
        dialogsRepository: DialogsRepository
    ) = MessagesManager(context, notificationRepository, messagesRepository, dialogsRepository)

}