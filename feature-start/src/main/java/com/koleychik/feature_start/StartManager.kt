package com.koleychik.feature_start

import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import javax.inject.Inject

class StartManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
) {

    fun checkVerifiedEmail(res: (CheckResult) -> Unit): Boolean {
        return if (accountRepository.isEmailVerified())
            true
        else {
            accountRepository.sendVerificationEmail(res)
            false
        }
    }

    fun checkUser(res: (UserResult) -> Unit) {
        authRepository.checkUser(res)
    }

}