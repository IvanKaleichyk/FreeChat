package com.kaleichyk.core_database.api

import com.koleychik.models.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.DialogsResult

interface DialogsRepository {

    fun getDialogs(start: Int, end: Long, res: (DialogsResult) -> Unit)
    fun getFavoritesDialogs(res: (DialogsResult) -> Unit)
    fun addDialog(dialog: Dialog, res: (CheckResult) -> Unit)
    fun delete(dialog: Dialog, res: (CheckResult) -> Unit)
}