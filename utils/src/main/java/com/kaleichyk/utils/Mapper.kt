package com.kaleichyk.utils

import com.koleychik.models.dialog.Dialog
import com.koleychik.models.dialog.DialogDTO

fun DialogDTO.toDialog() = Dialog(
    id = id,
    currentUser = CurrentUser.user!!,
    receiver = getReceiver(users),
    lastMessage = lastMessage,
    hasNewMessage = hasNewMessage,
    isFavorite = isFavorite
)

fun List<DialogDTO>.toListDialogs(): List<Dialog> {
    val mutableList = mutableListOf<Dialog>()
    this.forEach { mutableList.add(it.toDialog()) }
    return mutableList
}

private fun getReceiver(list: List<DialogDTO.Member>): DialogDTO.Member {
    for (i in list) if (i.id != CurrentUser.user!!.id) return i
    return list[0]
}