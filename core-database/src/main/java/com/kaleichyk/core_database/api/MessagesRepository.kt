package com.kaleichyk.core_database.api

import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.MessagesResult

interface MessagesRepository {

    fun getMessages(dialogId: String, start: Int, end: Long, res: (MessagesResult) -> Unit)
    fun addMessage(message: Message, res: (CheckResult) -> Unit)
    fun delete(message: Message, res: (CheckResult) -> Unit)
    fun editMessage(message: Message, res: (CheckResult) -> Unit)

}