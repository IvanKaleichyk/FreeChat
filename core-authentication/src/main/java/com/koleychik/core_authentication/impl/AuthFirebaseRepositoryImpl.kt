package com.koleychik.core_authentication.impl

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_authentication.R
import com.koleychik.core_authentication.api.AuthFirebaseRepository
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.module_injector.Constants
import javax.inject.Inject

class AuthFirebaseRepositoryImpl @Inject constructor() : AuthFirebaseRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun createFirebaseUser(email: String, password: String, res: (CheckResult) -> Unit) {
        Log.d(Constants.TAG, "AuthFirebaseRepositoryImpl start createFirebaseUser")
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

    override fun login(email: String, password: String, res: (CheckResult) -> Unit) {
        Log.d(Constants.TAG, "AuthFirebaseRepositoryImpl start login")
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

    override fun loginFirebaseUserByCredential(
        credential: AuthCredential,
        res: (CheckResult) -> Unit
    ) {
        Log.d(Constants.TAG, "AuthFirebaseRepositoryImpl start loginFirebaseUserByCredential")
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }


    override fun checkUser(res: (CheckResult) -> Unit) {
        Log.d(Constants.TAG, "AuthFirebaseRepositoryImpl start checkUser")
        if (auth.currentUser != null) res(CheckResult.Successful)
        else res(CheckResult.DataError(R.string.cannot_find_user))
    }
}