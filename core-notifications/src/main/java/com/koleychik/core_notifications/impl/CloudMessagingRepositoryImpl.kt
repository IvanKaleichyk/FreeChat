package com.koleychik.core_notifications.impl

import com.google.firebase.messaging.FirebaseMessaging
import com.koleychik.core_notifications.repositories.CloudMessagingRepository
import com.koleychik.core_notifications.toCheckResultError
import com.koleychik.models.results.CheckResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudMessagingRepositoryImpl @Inject constructor() : CloudMessagingRepository {

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    override suspend fun subscribeToTopic(topic: String): CheckResult {

        return try {
            firebaseMessaging.subscribeToTopic(topic).await()
            CheckResult.Successful
        } catch (e: Exception) {
            e.toCheckResultError()
        }
    }
}