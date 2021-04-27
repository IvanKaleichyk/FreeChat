package com.koleychik.core_authentication.impl

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AuthFirebaseDataSource
import com.koleychik.models.results.CheckResult
import com.koleychik.module_injector.Constants
import javax.inject.Inject

class AuthFirebaseDataSourceImpl @Inject constructor() : AuthFirebaseDataSource {

    private val auth = FirebaseAuth.getInstance()

    override fun sendVerificationEmail(res: (CheckResult) -> Unit) {
        auth.currentUser!!.sendEmailVerification()
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun createFirebaseUser(email: String, password: String, res: (CheckResult) -> Unit) {
        Log.d(Constants.TAG, "AuthFirebaseDataSourceImpl start createFirebaseUser")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                sendVerificationEmail(res)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun login(email: String, password: String, res: (CheckResult) -> Unit) {
        Log.d(Constants.TAG, "AuthFirebaseDataSourceImpl start login")
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun loginFirebaseUserByCredential(
        credential: AuthCredential,
        res: (CheckResult) -> Unit
    ) {
        Log.d(Constants.TAG, "AuthFirebaseDataSourceImpl start loginFirebaseUserByCredential")
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }


    override fun checkUser(res: (CheckResult) -> Unit) {
        Log.d(Constants.TAG, "AuthFirebaseDataSourceImpl start checkUser")
        if (auth.currentUser != null) res(CheckResult.Successful)
        else res(CheckResult.DataError(R.string.cannot_find_user))
    }

    override fun resetPassword(email: String, res: (CheckResult) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }
}