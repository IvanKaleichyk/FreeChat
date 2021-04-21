package com.koleychik.core_notifications.data

data class NetworkMessageNotificationModel(
    override val title: String,
    override val body: String,
    override val image: String?,
    val topic: String
) : MessageNotificationModel(title, body, image)