package com.koleychik.core_notifications.impl

import com.google.firebase.messaging.FirebaseMessaging
import com.koleychik.core_notifications.repositories.CloudMessagingRepository
import com.koleychik.models.results.CheckResult
import javax.inject.Inject

class CloudMessagingRepositoryImpl @Inject constructor() : CloudMessagingRepository {

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    override fun subscribeToTopic(topic: String, res: (CheckResult) -> Unit) {
        firebaseMessaging.subscribeToTopic(topic).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }
}