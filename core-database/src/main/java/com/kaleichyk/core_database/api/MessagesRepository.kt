package com.kaleichyk.core_database.api

import com.kaleichyk.core_database.messagesUtils.MessagesPaginationResult
import com.kaleichyk.core_database.messagesUtils.PaginationLastState
import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult

interface MessagesRepository {

    suspend fun getMessages(
        dialogId: String,
        lastState: PaginationLastState
    ): MessagesPaginationResult

    suspend fun addMessage(message: Message): CheckResult
    suspend fun delete(message: Message): CheckResult
//    suspend fun editMessage(message: Message): CheckResult

}