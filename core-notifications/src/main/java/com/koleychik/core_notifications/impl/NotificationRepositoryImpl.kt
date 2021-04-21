package com.koleychik.core_notifications.impl

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.koleychik.core_notifications.R
import com.koleychik.core_notifications.data.MessageNotificationModel
import com.koleychik.core_notifications.repositories.NotificationRepository
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(private val context: Context) : NotificationRepository {

    override fun createNotificationChannel(
        notificationManager: NotificationManager,
        id: String,
        name: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val important = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, important)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun createMessageNotification(
        model: MessageNotificationModel,
        channelId: String,
    ): Notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle(model.title)
        .setContentText(model.body)
        .setSmallIcon(R.drawable.message_icon_32)
        .setStyle(NotificationCompat.BigTextStyle().bigText(model.image))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

}