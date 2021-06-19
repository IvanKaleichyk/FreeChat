package com.kaleichyk.core_database.messagesUtils

import com.google.firebase.firestore.DocumentSnapshot

sealed class PaginationLastState {
    object FirstTime : PaginationLastState()
    class Data(val lastVisible: DocumentSnapshot) : PaginationLastState()
    object End : PaginationLastState()
}
