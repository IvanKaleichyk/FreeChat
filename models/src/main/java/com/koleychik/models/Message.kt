package com.koleychik.models

data class Message(
    val id: String,
    val dialogId: String,
    val authorId: String,
    val text: String?,
    val createdAt: Long,
    val image: String? = null,
    val isRead: Boolean = false
) {
    constructor() : this("0", "0", "", null, 0, null)
}