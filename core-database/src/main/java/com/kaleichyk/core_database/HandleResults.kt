package com.kaleichyk.core_database

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

fun <T> QuerySnapshot.getListFromQuerySnapshot(cls: Class<T>): List<T> {
    val result = mutableListOf<T>()
    for (i in this) result.add(i.toObject(cls))
    return result
}

fun <T> DocumentSnapshot.getObject(cls: Class<T>): T {
    val obj = toObject(cls)
    if (obj != null) return obj

    throw FirebaseFirestoreException("cannot find", FirebaseFirestoreException.Code.NOT_FOUND)
}