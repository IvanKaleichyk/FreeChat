package com.koleychik.freechat.managers

import com.koleychik.core_authentication.api.AccountRepository
import javax.inject.Inject

class MainManager @Inject constructor(private val repository: AccountRepository) {

    fun checkVerification() = repository.isEmailVerified()

    fun subscribeToUserChanges() {
        repository.subscribeToUserChanges()
    }

    fun unSubscribeToUserChanges(){
        repository.unSubscribeToUserChanges()
    }

    fun isUserOnline(isOnline: Boolean) {
        repository.setOnlineStatus(isOnline)
    }

}