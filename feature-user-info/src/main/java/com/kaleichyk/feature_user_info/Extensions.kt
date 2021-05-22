package com.kaleichyk.feature_user_info

import com.koleychik.models.dialog.Dialog
import com.koleychik.models.users.User
import java.util.*

fun createDialog(listUsers: List<User>) = Dialog(
    getDialogId(),
    getListMembers(listUsers),
    listOf(listUsers[0].id, listUsers[1].id),
)

private fun getDialogId(): Long = Date().time

private fun getListMembers(listUsers: List<User>): List<Dialog.Member>{
    return listUsers.map { it.toDialogMember() }
}

