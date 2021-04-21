package com.koleychik.core_notifications.repositories

import com.koleychik.models.results.CheckResult

interface CloudMessagingRepository {

    fun subscribeToTopic(topic: String, res: (CheckResult) -> Unit)
}
