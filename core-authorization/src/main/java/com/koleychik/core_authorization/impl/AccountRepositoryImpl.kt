package com.koleychik.core_authorization.impl

import com.koleychik.core_authorization.FirebaseAuthorization
import com.koleychik.core_authorization.api.AccountRepository
import com.koleychik.core_authorization.api.FirebaseDatabaseRepository
import com.koleychik.core_authorization.constants.UserConstants.BACKGROUND
import com.koleychik.core_authorization.constants.UserConstants.EMAIL
import com.koleychik.core_authorization.constants.UserConstants.ICON
import com.koleychik.core_authorization.constants.UserConstants.ROOT_PATH
import com.koleychik.core_authorization.result.CheckResult
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
    private val firebaseAuthorization: FirebaseAuthorization,
    private val firebaseDatabaseRepository: FirebaseDatabaseRepository
) : AccountRepository {

    override fun updateEmail(email: String, res: (CheckResult) -> Unit) {
        firebaseAuthorization.updateEmail(email) {
            when (it) {
                is CheckResult.Successful -> firebaseDatabaseRepository.putValue(
                    "$ROOT_PATH/$EMAIL",
                    email,
                    res
                )
                else -> res(it)
            }
        }
    }

    override fun updatePassword(password: String, res: (CheckResult) -> Unit) {
        firebaseAuthorization.updateEmail(password, res)
    }

    override fun updateIcon(url: String, res: (CheckResult) -> Unit) {
        firebaseDatabaseRepository.putValue("$ROOT_PATH/$ICON", url, res)
    }

    override fun updateBackground(url: String, res: (CheckResult) -> Unit) {
        firebaseDatabaseRepository.putValue("$ROOT_PATH/$BACKGROUND", url, res)
    }
}