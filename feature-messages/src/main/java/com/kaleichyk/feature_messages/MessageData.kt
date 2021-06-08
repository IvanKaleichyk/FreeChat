package com.kaleichyk.feature_messages

import com.kaleichyk.pagination.models.PaginationData
import com.koleychik.models.Message

data class MessageData(
    val id: Long,
    val dialogId: Long,
    val authorId: String,
    val text: String,
    val createdAt: Long,
    val isRead: Boolean
) : PaginationData() {
    fun toMessage() = Message(id, dialogId, authorId, text, createdAt, isRead)
}

fun List<Message>.toMessagesData() = mutableListOf<MessageData>().apply {
    for (i in this@toMessagesData) add(i.toMessageData())
}