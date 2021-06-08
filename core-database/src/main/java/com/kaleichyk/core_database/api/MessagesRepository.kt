package com.kaleichyk.core_database.api

import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.MessagesResult

interface MessagesRepository {

    suspend fun getMessages(dialogId: Long, start: Int, end: Long): MessagesResult
    suspend fun getMessages(dialogId: Long, page: Int): MessagesResult
    suspend fun addMessage(message: Message): CheckResult
    suspend fun delete(message: Message): CheckResult
//    suspend fun editMessage(message: Message): CheckResult

}