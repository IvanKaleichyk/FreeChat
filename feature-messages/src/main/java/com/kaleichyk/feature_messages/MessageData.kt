package com.kaleichyk.feature_messages

import com.kaleichyk.pagination.models.PaginationData
import com.koleychik.models.Message
import java.util.*

data class MessageData(
    val id: String,
    val dialogId: String,
    val authorId: String,
    val text: String?,
    val createdAt: Long,
    val image: String?,
    val isRead: Boolean,
) : PaginationData() {

    companion object {
        private fun createId(dialogId: String): String = Date().time.toString() + dialogId

        fun createMessage(dialogId: String, userId: String, text: String?, image: String?) = MessageData(
            createId(dialogId),
            dialogId,
            userId,
            text,
            Date().time,
            image,
            false
        )
    }

    fun toMessage() = Message(id, dialogId, authorId, text, createdAt, image, isRead)
}

fun List<Message>.toMessagesData() = mutableListOf<MessageData>().apply {
    for (i in this@toMessagesData) add(i.toMessageData())
}