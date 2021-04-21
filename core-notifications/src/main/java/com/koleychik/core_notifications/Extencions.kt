package com.koleychik.core_notifications

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.RemoteMessage
import com.koleychik.core_notifications.data.MessageNotificationModel
import com.koleychik.core_notifications.data.NetworkMessageNotificationModel
import com.koleychik.models.Message

const val BASE_URL = "https://feribase-message-test.herokuapp.com/message/"

fun RemoteMessage.Notification.toMessageNotificationModel()= MessageNotificationModel(
    title.toString(),
    body.toString(),
    if (imageUrl != null) imageUrl.toString() else null
)

fun Message.toNetworkMessageNotificationModel(title: String, image: String?) = NetworkMessageNotificationModel(
    title = title,
    body = text,
    image = image,
    topic = topic
)

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    Log.d("MAIN_APP_TAG", "showToast - $text")
}