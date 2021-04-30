package com.koleychik.core_authentication.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.kaleichyk.data.CurrentUser
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthDbDataSource
import com.koleychik.core_authentication.api.AuthFirebaseDataSource
import com.koleychik.models.results.CheckResult
import com.koleychik.module_injector.Constants.TAG
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

    override fun sendVerificationEmail(res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start verifyEmail")
        val fbUser = auth.currentUser
        if (fbUser == null) {
            res(CheckResult.DataError(R.string.cannot_find_user))
        } else authFirebaseDataSource.sendVerificationEmail(res)
    }

    override fun updateEmail(email: String, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateEmail")
        if (CurrentUser.user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else dbDataSource.updateUserEmail(email) {
            auth.currentUser!!.updateEmail(email)
            res(it)
        }
    }

    override fun updatePassword(password: String, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updatePassword")
        auth.currentUser!!.updatePassword(password)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun updateName(name: String, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateName")
        if (CurrentUser.user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else dbDataSource.updateName(name, res)
    }

    override fun updateIcon(uri: Uri, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateIcon")
        if (CurrentUser.user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else dbDataSource.updateIcon(uri, res)
    }

    override fun updateBackground(uri: Uri, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateBackground")
        if (CurrentUser.user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else dbDataSource.updateBackground(uri, res)
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun deleteUser() {
        auth.currentUser?.delete()
        CurrentUser.user = null
    }

    override fun subscribeToUserChanges() {
        dbDataSource.subscribeToUserChanges()
    }

    override fun unSubscribeToUserChanges() {
        dbDataSource.unSubscribeToUserChanges()
    }

}