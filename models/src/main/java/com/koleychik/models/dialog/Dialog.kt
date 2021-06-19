package com.koleychik.models.dialog

import android.os.Parcelable
import com.koleychik.models.Message
import com.koleychik.models.users.User
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Dialog(
    val id: String,
    val currentUser: @RawValue User,
    val receiver: @RawValue DialogDTO.Member,
    var lastMessage: @RawValue Message? = null,
    var hasNewMessage: Boolean = false,
    var isFavorite: Boolean = false
) : Parcelable{

    constructor() : this(
        id = "0",
        currentUser = User(),
        receiver = DialogDTO.Member(),
        lastMessage = null,
        hasNewMessage = false,
        isFavorite = false
    )

    fun toDialogDTO() = DialogDTO(
        id,
        listOf(currentUser.toDialogMember(), receiver),
        listOf(currentUser.id, receiver.id),
        lastMessage,
        hasNewMessage,
        isFavorite
    )

}