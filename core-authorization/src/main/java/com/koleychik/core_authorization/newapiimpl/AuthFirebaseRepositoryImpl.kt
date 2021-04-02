package com.koleychik.core_authorization.newapiimpl

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_authorization.R
import com.koleychik.core_authorization.newapi.AuthFirebaseRepository
import com.koleychik.core_authorization.result.CheckResult

class AuthFirebaseRepositoryImpl : AuthFirebaseRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun createFirebaseUser(email: String, password: String, res: (CheckResult) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

    override fun login(email: String, password: String, res: (CheckResult) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

    override fun loginFirebaseUserByCredential(
        credential: AuthCredential,
        res: (CheckResult) -> Unit
    ) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }


    override fun checkUser(res: (CheckResult) -> Unit) {
        if (auth.currentUser == null) res(CheckResult.Successful)
        else res(CheckResult.DataError(R.string.cannot_find_user))
    }
}