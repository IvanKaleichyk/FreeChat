package com.kaleichyk.core_database.api

import com.koleychik.models.Dialog
import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult

interface DialogsRepository {

    suspend fun getDialogs(listIds: List<Long>, start: Int, end: Long): DialogsResult
    suspend fun getFavoritesDialogs(listIds: List<Long>): DialogsResult
    suspend fun addDialog(dialog: Dialog): DialogResult
    suspend fun delete(dialog: Dialog): CheckResult
    suspend fun addLastMessage(dialogId: Long, message: Message): CheckResult
}