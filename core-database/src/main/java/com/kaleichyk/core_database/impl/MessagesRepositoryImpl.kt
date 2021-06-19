package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.kaleichyk.core_database.api.MessagesRepository
import com.kaleichyk.core_database.messagesUtils.MessagesPaginationResult
import com.kaleichyk.core_database.messagesUtils.PaginationLastState
import com.kaleichyk.core_database.messagesUtils.toMessagesPaginationResultError
import com.kaleichyk.core_database.toCheckResultError
import com.kaleichyk.core_database.toMessagesPaginationResult
import com.kaleichyk.core_database.toMessagesResultError
import com.koleychik.models.Message
import com.koleychik.models.constants.MessageConstants
import com.koleychik.models.results.CheckResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class MessagesRepositoryImpl @Inject constructor() : MessagesRepository {

    private val store = FirebaseFirestore.getInstance()

    companion object {
        const val PAGE_SIZE = 30L
    }

    override suspend fun getMessages(
        dialogId: String,
        lastState: PaginationLastState
    ): MessagesPaginationResult {

        if (lastState is PaginationLastState.End)
            return MessagesPaginationResult.Successful(emptyList(), lastState)

        val orderBy = store.collection(MessageConstants.getRootPath(dialogId))
            .orderBy(MessageConstants.CREATED_AT, Query.Direction.DESCENDING)


        return when (lastState) {
            is PaginationLastState.End ->
                MessagesPaginationResult.Successful(emptyList(), lastState)
            is PaginationLastState.FirstTime -> getFirstTimeMessages(orderBy)
            is PaginationLastState.Data -> getMessages(orderBy, lastState)
        }
    }

    override suspend fun addMessage(message: Message): CheckResult {
        val document = store.collection(MessageConstants.getRootPath(message.dialogId))
            .document(message.id)

        return try {
            document.set(message).await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override suspend fun delete(message: Message): CheckResult {
        val document = store.collection(MessageConstants.getRootPath(message.dialogId))
            .document(message.id)

        return try {
            document.delete()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    private suspend fun getMessages(
        orderBy: Query,
        paginationLastState: PaginationLastState.Data
    ): MessagesPaginationResult {
        return try {
            val response = orderBy
                .startAfter(paginationLastState.lastVisible)
                .limit(PAGE_SIZE)
                .get().await()

            response.toMessagesPaginationResult()
        } catch (e: FirebaseFirestoreException) {
            e.toMessagesResultError().toMessagesPaginationResultError(PaginationLastState.FirstTime)
        }
    }

    private suspend fun getFirstTimeMessages(orderBy: Query): MessagesPaginationResult {
        return try {
            val response = orderBy
                .limit(PAGE_SIZE)
                .get().await()
            response.toMessagesPaginationResult()
        } catch (e: FirebaseFirestoreException) {
            e.toMessagesResultError().toMessagesPaginationResultError(PaginationLastState.FirstTime)
        }
    }
}