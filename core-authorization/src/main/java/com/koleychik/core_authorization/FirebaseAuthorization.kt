package com.koleychik.core_authorization

import com.google.firebase.auth.FirebaseAuth
import com.koleychik.core_authorization.result.CheckResult

class FirebaseAuthorization {

    private val auth = FirebaseAuth.getInstance()

    fun updateEmail(email: String, res: (CheckResult) -> Unit){
        auth.currentUser?.updateEmail(email)?.addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

    fun updatePassword(password: String, res: (CheckResult) -> Unit){
        auth.currentUser?.updatePassword(password)?.addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

    fun login(email: String, password: String, res: (CheckResult) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

    fun createAccount(email: String, password: String, res: (CheckResult) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) res(CheckResult.Successful)
            else if (it.isCanceled) res(CheckResult.ServerError(it.exception?.message.toString()))
        }
    }

}