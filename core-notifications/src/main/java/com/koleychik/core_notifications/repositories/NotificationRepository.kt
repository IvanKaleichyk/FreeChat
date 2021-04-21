package com.koleychik.core_notifications.repositories

import android.app.Notification
import android.app.NotificationManager
import com.koleychik.core_notifications.data.MessageNotificationModel

internal interface NotificationRepository {
    fun createNotificationChannel(
        notificationManager: NotificationManager,
        id: String,
        name: String
    )

    fun createMessageNotification(
        model: MessageNotificationModel,
        channelId: String,
    ): Notification
}