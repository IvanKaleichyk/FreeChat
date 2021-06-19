package com.kaleichyk.core_database.api

import com.koleychik.models.Message
import com.koleychik.models.dialog.DialogDTO
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult

interface DialogsRepository {

    suspend fun getAllDialogs(userId: String): DialogsResult
    suspend fun getFavoritesDialogs(userId: String): DialogsResult
    suspend fun addDialog(dialog: DialogDTO): DialogResult
    suspend fun delete(dialog: DialogDTO): CheckResult
    suspend fun addLastMessage(dialogId: String, message: Message): CheckResult
    fun subscribeToNewMessages(
        dialogId: String,
        onCameNewLetters: (newMessage: Message) -> Unit
    )

    fun unsubscribeToNewMessages(dialogId: String)

}