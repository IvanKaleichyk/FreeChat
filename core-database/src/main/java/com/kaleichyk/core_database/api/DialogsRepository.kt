package com.kaleichyk.core_database.api

import com.koleychik.models.Dialog
import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult

interface DialogsRepository {

    fun getDialogs(listIds: List<Long>, start: Int, end: Long, res: (DialogsResult) -> Unit)
    fun getFavoritesDialogs(listIds: List<Long>, res: (DialogsResult) -> Unit)
    fun addDialog(dialog: Dialog, res: (DialogResult) -> Unit)
    fun delete(dialog: Dialog, res: (CheckResult) -> Unit)
    fun addLastMessage(dialogId: Long, message: Message, res: (CheckResult) -> Unit)
}