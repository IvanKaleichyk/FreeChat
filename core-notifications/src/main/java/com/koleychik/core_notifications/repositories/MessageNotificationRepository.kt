package com.koleychik.core_notifications.repositories

import com.koleychik.core_notifications.data.MessageNotificationModel
import com.koleychik.models.Message

interface MessageNotificationRepository {

    suspend fun sendMessageNotification(
        message: Message,
        title: String,
        image: String?,
        topic: String
    )

    fun showMessageNotification(message: MessageNotificationModel)

}