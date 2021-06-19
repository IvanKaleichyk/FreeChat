package com.kaleichyk.feature_user_info

import com.kaleichyk.utils.CurrentUser
import com.koleychik.models.dialog.Dialog
import com.koleychik.models.dialog.DialogDTO
import java.util.*

fun createDialog(receiver: DialogDTO.Member) = Dialog(
    getDialogId(listOf(CurrentUser.user!!.toDialogMember(), receiver)),
    CurrentUser.user!!,
    receiver
)

private fun getDialogId(list: List<DialogDTO.Member>): String {
    return buildString {
        append(Date().time)
        for (i in list) append(i.id)
    }
}