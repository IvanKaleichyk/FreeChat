package com.koleychik.core_authentication.impl

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.kaleichyk.utils.getCheckResultError
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AuthFirebaseDataSource
import com.koleychik.core_authentication.toCheckResultError
import com.koleychik.models.results.CheckResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthFirebaseDataSourceImpl @Inject constructor() : AuthFirebaseDataSource {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun sendVerificationEmail(): CheckResult {
        return try {
            auth.currentUser!!.sendEmailVerification().await()
            CheckResult.Successful
        } catch (e: FirebaseAuthException) {
            e.toCheckResultError()
        }
    }

    override suspend fun createFirebaseUser(email: String, password: String): CheckResult {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            return e.toCheckResultError()
        }
        return sendVerificationEmail()
    }

    override suspend fun login(email: String, password: String): CheckResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            CheckResult.Successful
        } catch (e: FirebaseAuthException) {
            e.toCheckResultError()
        }
    }

    override fun loginFirebaseUserByCredential(
        credential: AuthCredential,
        res: (CheckResult) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                res((it as FirebaseAuthException).toCheckResultError())
            }
    }


    override suspend fun checkUser(): CheckResult {
        return if (auth.currentUser != null) CheckResult.Successful
        else getCheckResultError(R.string.cannot_find_user)
    }

    override suspend fun resetPassword(email: String): CheckResult {
        return try {
            auth.sendPasswordResetEmail(email).await()
            CheckResult.Successful
        } catch (e: FirebaseAuthException) {
            e.toCheckResultError()
        }
    }
}