package com.koleychik.core_notifications

import com.koleychik.core_notifications.repositories.CloudMessagingRepository
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.module_injector.injections.BaseApi

interface NotificationCoreApi: BaseApi {

    fun messageNotificationRepository(): MessageNotificationRepository
    fun cloudMessagingRepository(): CloudMessagingRepository

}