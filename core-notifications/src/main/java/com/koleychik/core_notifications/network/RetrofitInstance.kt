package com.koleychik.core_notifications.network

import com.google.gson.GsonBuilder
import com.koleychik.core_notifications.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitInstance {

    val api: NotificationServiceApi by lazy {
        retrofit.create(NotificationServiceApi::class.java)
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

}