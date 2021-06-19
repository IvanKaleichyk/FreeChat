package com.kaleichyk.core_database

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.kaleichyk.core_database.messagesUtils.MessagesPaginationResult
import com.kaleichyk.core_database.messagesUtils.PaginationLastState
import com.koleychik.models.Message

fun <T> QuerySnapshot.toListFromQuerySnapshot(cls: Class<T>): List<T> {
    val result = mutableListOf<T>()
    for (i in this) result.add(i.toObject(cls))
    return result
}

fun QuerySnapshot.toMessagesPaginationResult(): MessagesPaginationResult.Successful {
    val result = mutableListOf<Message>()
    for (i in this) result.add(i.toObject(Message::class.java))

    val paginationLastState = try {
        PaginationLastState.Data(this.documents[size() - 1])
    } catch (e: ArrayIndexOutOfBoundsException) {
        PaginationLastState.End
    }

    return MessagesPaginationResult.Successful(result, paginationLastState)
}

fun <T> DocumentSnapshot.getObject(cls: Class<T>): T {
    val obj = toObject(cls)
    if (obj != null) return obj

    throw FirebaseFirestoreException("cannot find", FirebaseFirestoreException.Code.NOT_FOUND)
}