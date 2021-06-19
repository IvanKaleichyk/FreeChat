package com.koleychik.freechat.managers

import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_notifications.repositories.CloudMessagingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainManager @Inject constructor(
    private val repository: AccountRepository,
    private val cloudMessagingRepository: CloudMessagingRepository
) {

    fun checkVerification() = repository.isEmailVerified()

    fun subscribeToUserNotification(topic: String) = CoroutineScope(Dispatchers.IO).launch {
        cloudMessagingRepository.subscribeToTopic(topic)
    }

    fun unsubscribeFromUserNotification(topic: String) = CoroutineScope(Dispatchers.IO).launch {
        cloudMessagingRepository.unsubscribeFromTopic(topic)
    }

    fun subscribeToUserChanges() {
        repository.subscribeToUserChanges()
    }

    fun unSubscribeToUserChanges() {
        repository.unSubscribeToUserChanges()
    }

    fun isUserOnline(isOnline: Boolean) {
        repository.setOnlineStatus(isOnline)
    }

}