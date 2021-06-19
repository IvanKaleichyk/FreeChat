package com.kaleichyk.feature_messages.di

import android.content.Context
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.MessagesRepository
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.module_injector.injections.BaseDependencies

interface MessagesFeatureDependencies: BaseDependencies{
    fun context(): Context
    fun messageNotificationRepository(): MessageNotificationRepository
    fun messagesRepository(): MessagesRepository
    fun dialogsRepository(): DialogsRepository
}