package com.koleychik.core_notifications.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.koleychik.core_notifications.impl.MessageNotificationRepositoryImpl
import com.koleychik.core_notifications.impl.NotificationRepositoryImpl
import com.koleychik.core_notifications.network.RetrofitInstance
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.core_notifications.showToast
import com.koleychik.core_notifications.toMessageNotificationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirebaseCloudMessageNotificationService : FirebaseMessagingService() {

    private val api = RetrofitInstance.api

    private val repository: MessageNotificationRepository =
        MessageNotificationRepositoryImpl(
            applicationContext,
            NotificationRepositoryImpl(applicationContext),
            api
        )

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            repository.showMessageNotification(it.toMessageNotificationModel())
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.addToken(token)
            withContext(Dispatchers.Main) {
                if (!response.isSuccessful) applicationContext.showToast(response.message())
            }
        }
    }

}