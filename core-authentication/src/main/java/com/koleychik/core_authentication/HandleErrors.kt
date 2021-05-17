package com.koleychik.core_authentication

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.results.user.UsersResult

fun FirebaseFirestoreException.toUserResultError(): UserResult.Error {
    val message: String
    when (code) {
        FirebaseFirestoreException.Code.NOT_FOUND -> return UserResult.DataError(R.string.cannot_find_user)
        else -> message = localizedMessage ?: this.message.toString()
    }
    return UserResult.ServerError(message)
}

fun FirebaseAuthException.toUserResultError(): UserResult.Error {
    val message = localizedMessage ?: message.toString()
    return UserResult.ServerError(message)
}

fun FirebaseFirestoreException.toUsersResultError(): UsersResult.Error {
    val message = localizedMessage ?: message.toString()
    return UsersResult.ServerError(message)
}

fun FirebaseFirestoreException.toCheckResultError(): CheckResult.Error {
    val message = localizedMessage ?: message.toString()
    return CheckResult.ServerError(message)
}

fun FirebaseAuthException.toCheckResultError(): CheckResult.Error {
    val message = localizedMessage ?: message.toString()
    return CheckResult.ServerError(message)
}

fun FirebaseException.toCheckResultError(): CheckResult.Error {
    val message = localizedMessage ?: message.toString()
    return CheckResult.ServerError(message)
}