package com.kaleichyk.core_database

import com.google.firebase.firestore.FirebaseFirestoreException
import com.kaleichyk.utils.getDialogResultError
import com.kaleichyk.utils.getUserResultError
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult
import com.koleychik.models.results.messages.MessagesResult
import com.koleychik.models.results.user.UserResult
import com.koleychik.models.results.user.UsersResult

fun FirebaseFirestoreException.toUserResultError(): UserResult.Error {
    val message: String
    when (code) {
        FirebaseFirestoreException.Code.NOT_FOUND -> return getUserResultError(R.string.cannot_find_user)

        else -> message = localizedMessage ?: this.message.toString()
    }
    return UserResult.Error(message)
}

fun FirebaseFirestoreException.toUsersResultError(): UsersResult.Error {
    val message = localizedMessage ?: message.toString()
    return UsersResult.Error(message)
}

fun FirebaseFirestoreException.toMessagesResultError(): MessagesResult.Error {
    val message = localizedMessage ?: message.toString()
    return MessagesResult.Error(message)
}

fun FirebaseFirestoreException.toDialogsResultError(): DialogsResult.Error {
    val message = localizedMessage ?: message.toString()
    return DialogsResult.Error(message)
}

fun FirebaseFirestoreException.toDialogResultError(): DialogResult.Error {
    val message: String
    when (code) {
        FirebaseFirestoreException.Code.ALREADY_EXISTS -> return getDialogResultError(R.string.dialog_exits)
        else -> message = localizedMessage ?: this.message.toString()
    }
    return DialogResult.Error(message)
}

fun FirebaseFirestoreException.toCheckResultError(): CheckResult.Error {
    val message = localizedMessage ?: message.toString()
    return CheckResult.Error(message)
}