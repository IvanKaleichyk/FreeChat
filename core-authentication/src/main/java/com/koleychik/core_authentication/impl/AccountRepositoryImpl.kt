package com.koleychik.core_authentication.impl

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.kaleichyk.data.CurrentUser
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthDbDataSource
import com.koleychik.core_authentication.api.AuthFirebaseDataSource
import com.koleychik.core_authentication.toCheckResultError
import com.koleychik.models.results.CheckResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
    private val dbDataSource: AuthDbDataSource,
    private val authFirebaseDataSource: AuthFirebaseDataSource
) : AccountRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun isEmailVerified(): Boolean = auth.currentUser?.isEmailVerified ?: false

    override fun setOnlineStatus(isOnline: Boolean) {
        CurrentUser.user!!.isOnline = isOnline
        dbDataSource.isUserOnline(isOnline)
    }

    override suspend fun sendVerificationEmail(): CheckResult {
        val fbUser = auth.currentUser
        return if (fbUser == null) CheckResult.DataError(R.string.cannot_find_user)
        else authFirebaseDataSource.sendVerificationEmail()
    }

    override suspend fun updateEmail(email: String): CheckResult {
        return if (CurrentUser.user == null) CheckResult.DataError(R.string.cannot_find_user)
        else {
            var result = dbDataSource.updateUserEmail(email)
            if (result is CheckResult.Successful) result = updateEmailInFirebaseService(email)
            result
        }
    }

    override suspend fun updatePassword(password: String): CheckResult {
        return try {
            auth.currentUser!!.updatePassword(password).await()
            CheckResult.Successful
        } catch (e: FirebaseAuthException) {
            e.toCheckResultError()
        }
    }

    override suspend fun updateName(name: String): CheckResult {
        return if (CurrentUser.user == null) CheckResult.DataError(R.string.cannot_find_user)
        else dbDataSource.updateName(name)
    }

    override suspend fun updateIcon(uri: Uri): CheckResult {
        return if (CurrentUser.user == null) CheckResult.DataError(R.string.cannot_find_user)
        else dbDataSource.updateIcon(uri)
    }

    override suspend fun updateBackground(uri: Uri): CheckResult {
        return if (CurrentUser.user == null) CheckResult.DataError(R.string.cannot_find_user)
        else dbDataSource.updateBackground(uri)
    }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteUser(): CheckResult {
        val user = auth.currentUser ?: return CheckResult.DataError(R.string.cannot_find_user)

        return try {
            user.delete().await()
            CurrentUser.user = null
            CheckResult.Successful
        } catch (e: FirebaseException) {
            e.toCheckResultError()
        }

    }

    override fun subscribeToUserChanges() {
        dbDataSource.subscribeToUserChanges()
    }

    override fun unSubscribeToUserChanges() {
        dbDataSource.unSubscribeToUserChanges()
    }

    private suspend fun updateEmailInFirebaseService(email: String): CheckResult {
        return try {
            auth.currentUser!!.updateEmail(email).await()
            CheckResult.Successful
        } catch (e: FirebaseAuthException) {
            e.toCheckResultError()
        }
    }

}