package com.koleychik.feature_start

import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthRepository
import javax.inject.Inject

class StartManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
) {

    suspend fun checkVerifiedEmail(): Boolean {
        return if (accountRepository.isEmailVerified())
            true
        else {
            accountRepository.sendVerificationEmail()
            false
        }
    }

    suspend fun checkUser() = authRepository.checkUser()

}