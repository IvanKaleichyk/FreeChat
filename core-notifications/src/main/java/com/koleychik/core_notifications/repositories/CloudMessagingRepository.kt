package com.koleychik.core_notifications.repositories

import com.koleychik.models.results.CheckResult

interface CloudMessagingRepository {

    suspend fun subscribeToTopic(topic: String): CheckResult
}
