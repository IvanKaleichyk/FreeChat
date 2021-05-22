package com.kaleichyk.core_database.api

import com.koleychik.models.Message
import com.koleychik.models.dialog.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult

interface DialogsRepository {

    suspend fun getAllDialogs(userId: String): DialogsResult
    suspend fun getFavoritesDialogs(userId: String): DialogsResult
    suspend fun addDialog(dialog: Dialog): DialogResult
    suspend fun delete(dialog: Dialog): CheckResult
    suspend fun addLastMessage(dialogId: Long, message: Message): CheckResult
}