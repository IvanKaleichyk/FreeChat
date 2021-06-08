package com.koleychik.models

data class Message(
    val id: Long,
    val dialogId: Long,
    val authorId: String,
    val text: String,
    val createdAt: Long,
    val isRead: Boolean = false
) {
    constructor() : this(0, 0, "", "", 0)
}
