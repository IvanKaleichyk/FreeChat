package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.kaleichyk.core_database.api.MessagesRepository
import com.kaleichyk.core_database.getListFromQuerySnapshot
import com.kaleichyk.core_database.toCheckResultError
import com.kaleichyk.core_database.toMessagesResultError
import com.koleychik.models.Message
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.constants.MessageConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.MessagesResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class MessagesRepositoryImpl @Inject constructor() : MessagesRepository {

    private val store = FirebaseFirestore.getInstance()

    companion object {
        const val PAGE_SIZE = 30
    }

    override suspend fun getMessages(dialogId: Long, start: Int, end: Long): MessagesResult {
        val collection = store.collection(MessageConstants.ROOT_PATH)
            .document()
            .collection(dialogId.toString())
            .startAfter(start)
            .limit(end)

        return try {
            val result = collection.get().await()
            MessagesResult.Successful(result.getListFromQuerySnapshot(Message::class.java))
        } catch (e: FirebaseFirestoreException) {
            e.toMessagesResultError()
        }
    }

    override suspend fun getMessages(dialogId: Long, page: Int): MessagesResult {
        val start = (page - 1) * PAGE_SIZE

        val collection = store.collection(MessageConstants.ROOT_PATH)
            .startAt(start)
            .limit(PAGE_SIZE.toLong())

        return try {
            val result = collection.get().await()
            MessagesResult.Successful(result.getListFromQuerySnapshot(Message::class.java))
        } catch (e: FirebaseFirestoreException) {
            e.toMessagesResultError()
        }
    }

    override suspend fun addMessage(message: Message): CheckResult {
        val document = store.collection(DialogConstants.ROOT_PATH)
            .document()
            .collection(message.dialogId.toString())
            .document(message.id.toString())

        return try {
            document.set(message).await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override suspend fun delete(message: Message): CheckResult {
        val document = store.collection(DialogConstants.ROOT_PATH)
            .document()
            .collection(message.dialogId.toString())
            .document(message.id.toString())

        return try {
            document.delete()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

//    override suspend fun editMessage(message: Message): CheckResult {
//        val document = store.collection(DialogConstants.ROOT_PATH)
//            .document()
//            .collection(message.dialogId.toString())
//            .document(message.id.toString())
//
//        try {
//            document.up(message).
//        }
//
//        store.collection("${DialogConstants.ROOT_PATH}/${message.dialogId}/${MessageConstants.ROOT_PATH}/${message.id}")
//            .add(message)
//            .addOnSuccessListener {
//                res(CheckResult.Successful)
//            }
//            .addOnFailureListener {
//                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
//                else res(CheckResult.ServerError(it.message.toString()))
//            }
//    }
}