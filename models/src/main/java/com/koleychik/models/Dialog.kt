package com.koleychik.models

import com.koleychik.models.users.User

data class Dialog(
    val id: Long,
    val users: List<User>,
    var lastMessage: Message? = null,
    var hasNewMessage: Boolean = false,
    var isFavorite: Boolean = false
) {
    constructor() : this(0, emptyList(), null, false, false)
}
