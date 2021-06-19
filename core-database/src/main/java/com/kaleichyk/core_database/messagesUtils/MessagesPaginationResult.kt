package com.kaleichyk.core_database.messagesUtils

import com.koleychik.models.Message
import com.koleychik.models.results.messages.MessagesResult
import com.koleychik.models.states.DataState

sealed class MessagesPaginationResult {
    class Successful(
        val list: List<Message>,
        val state: PaginationLastState
    ) : MessagesPaginationResult()

    class Error(
        val message: String,
        val state: PaginationLastState
    ) : MessagesPaginationResult()

    fun toDataState(): DataState {
        return when (this) {
            is Successful -> DataState.Result(list)
            is Error -> DataState.Error(message)
        }
    }
}

fun MessagesResult.Error.toMessagesPaginationResultError(lastState: PaginationLastState) =
    MessagesPaginationResult.Error(message, lastState)