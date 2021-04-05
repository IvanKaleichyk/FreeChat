package com.koleychik.feature_start

import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.api.DataStoreRepository
import com.koleychik.core_authentication.result.VerificationResult
import com.koleychik.core_authentication.result.user.UserResult
import javax.inject.Inject

class StartRepository @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val dataStoreRepository: DataStoreRepository
) {

    fun checkVerifiedEmail(res: (VerificationResult) -> Unit): Boolean {
        return if (accountRepository.isEmailVerified())
            true
        else {
            accountRepository.verifyEmail(res)
            false
        }
    }

    suspend fun checkUser(res: (UserResult) -> Unit) {
        val user = dataStoreRepository.getUser()
        if (user != null) return res(UserResult.Successful(user))
        authRepository.checkUser(res)
    }

}