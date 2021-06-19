package com.kaleichyk.feature_messages

import com.koleychik.models.Message

fun Message.toMessageData() = MessageData(id, dialogId, authorId, text, createdAt, image, isRead)