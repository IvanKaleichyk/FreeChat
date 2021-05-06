package com.kaleichyk.feature_user_info

import com.koleychik.models.Dialog
import com.koleychik.models.users.User
import java.util.*

fun createDialog(listUsers: List<User>) = Dialog(
    getDialogId(listUsers[0].id, listUsers[1].id),
    listUsers
)

private fun getDialogId(userId1: String, userId2: String): Long = Date().time

