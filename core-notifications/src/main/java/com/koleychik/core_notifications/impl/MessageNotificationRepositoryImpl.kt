package com.koleychik.core_notifications.impl

import android.app.NotificationManager
import android.content.Context
import com.koleychik.core_notifications.R
import com.koleychik.core_notifications.data.MessageNotificationModel
import com.koleychik.core_notifications.network.NotificationServiceApi
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.core_notifications.repositories.NotificationRepository
import com.koleychik.core_notifications.toNetworkMessageNotificationModel
import com.koleychik.models.Message
import java.util.*
import javax.inject.Inject

internal class MessageNotificationRepositoryImpl @Inject constructor(
    private val context: Context,
    private val notificationRepository: NotificationRepository,
    private val networkApi: NotificationServiceApi
) : MessageNotificationRepository {

    private val channelId = "FREE CHAT MESSAGE NOTIFICATION CHANNEL ID"

    override suspend fun sendMessageNotification(message: Message, title: String, image: String?) {
        networkApi.sendMessageNotification(message.toNetworkMessageNotificationModel(title, image))
    }

    override fun showMessageNotification(message: MessageNotificationModel) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationRepository.createNotificationChannel(
            notificationManager,
            channelId,
            context.getString(R.string.message_notification_channel_name)
        )
        notificationManager.notify(
            getUniqueNotificationId(message.title),
            notificationRepository.createMessageNotification(message, channelId)
        )
    }

    private fun getUniqueNotificationId(title: String): Int {
        val dateTime = (Date().time / 2).toInt()
        return dateTime + title[0].toInt() + title[title.lastIndex].toInt()
    }

}