package com.koleychik.core_authentication.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.result.VerificationResult
import com.koleychik.models.constants.UserConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.users.User
import com.koleychik.module_injector.Constants.TAG
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor() : AccountRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override var user: User? = null

    override fun isEmailVerified(): Boolean = auth.currentUser?.isEmailVerified ?: false

    override fun verifyEmail(res: (VerificationResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start verifyEmail")
        val fbUser = auth.currentUser
        if (fbUser == null) {
            res(VerificationResult.DataError(R.string.cannot_find_user))
        } else {
            fbUser.sendEmailVerification()
                .addOnCompleteListener {
                    if (it.isSuccessful) res(VerificationResult.Waiting)
                    else res(VerificationResult.ServerError(it.exception?.message.toString()))
                }
        }
    }

    override fun updateEmail(email: String, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateEmail")
        if (user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else {
            db
                .collection("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.EMAIL}")
                .add(email)
                .addOnSuccessListener {
                    auth.currentUser!!.updateEmail(email)
                    user?.email = email
                    res(CheckResult.Successful)
                }
                .addOnFailureListener { res(CheckResult.ServerError(it.message.toString())) }
        }
    }

    override fun updatePassword(password: String, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updatePassword")
        auth.currentUser!!.updatePassword(password)
            .addOnFailureListener {
                res(CheckResult.ServerError(it.message.toString()))
            }.addOnCompleteListener {
                if (it.isSuccessful) res(CheckResult.Successful)
                else res(CheckResult.DataError(R.string.cannot_update_password))
            }
    }

    override fun updateName(name: String, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateName")
        if (user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else {
            db.collection("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.NAME}")
                .add(name)
                .addOnSuccessListener {
                    user?.name = name
                    res(CheckResult.Successful)
                }
                .addOnFailureListener { res(CheckResult.ServerError(it.message.toString())) }
        }
    }

    override fun updateIcon(uri: Uri, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateIcon")
        if (user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else {
            db.collection("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.ICON}")
                .add(uri)
                .addOnSuccessListener {
                    user?.icon = uri
                    res(CheckResult.Successful)
                }
                .addOnFailureListener { res(CheckResult.ServerError(it.message.toString())) }
        }
    }

    override fun updateBackground(uri: Uri, res: (CheckResult) -> Unit) {
        Log.d(TAG, "AccountRepositoryImpl start updateBackground")
        if (user == null) res(CheckResult.DataError(R.string.cannot_find_user))
        else {
            db.collection("${UserConstants.ROOT_PATH}/${user!!.id}/${UserConstants.BACKGROUND}")
                .add(uri)
                .addOnSuccessListener {
                    user?.background = uri
                    res(CheckResult.Successful)
                }
                .addOnFailureListener { res(CheckResult.ServerError(it.message.toString())) }
        }
    }

}