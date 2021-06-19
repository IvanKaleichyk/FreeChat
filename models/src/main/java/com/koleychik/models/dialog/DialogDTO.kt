package com.koleychik.models.dialog

import android.os.Parcelable
import com.koleychik.models.Message
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DialogDTO(
    val id: String,
    val users: @RawValue List<Member>,
    val listUsersIds: List<String>,
    var lastMessage: @RawValue Message? = null,
    var hasNewMessage: Boolean = false,
    var isFavorite: Boolean = false
) : Parcelable {

    constructor() : this(
        "0",
        emptyList(),
        listUsersIds = emptyList(),
        lastMessage = null,
        hasNewMessage = false,
        isFavorite = false
    )

    data class Member(val id: String = "", val name: String = "", val icon: String? = null)
}