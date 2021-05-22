package com.koleychik.models.dialog

import com.koleychik.models.Message

data class Dialog(
    val id: Long,
    val users: List<Member>,
    val listUsersIds: List<String>,
    var lastMessage: Message? = null,
    var hasNewMessage: Boolean = false,
    var isFavorite: Boolean = false
) {

    constructor() : this(
        0,
        emptyList(),
        listUsersIds = emptyList(),
        lastMessage = null,
        hasNewMessage = false,
        isFavorite = false
    )

    data class Member(val id: String = "", val name: String = "", val icon: String? = null)

}
