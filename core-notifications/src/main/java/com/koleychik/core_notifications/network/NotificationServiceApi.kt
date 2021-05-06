package com.koleychik.core_notifications.network

import com.koleychik.core_notifications.data.NetworkMessageNotificationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface NotificationServiceApi {

    @POST("send")
    suspend fun sendMessageNotification(@Body model: NetworkMessageNotificationModel)

    @POST("addToken")
    suspend fun addToken(@Body token: String): Response<Boolean>
}