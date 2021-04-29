package com.koleychik.models.users

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class User(
    open val id: String,
    open var name: String,
    open val email: String,
    open val createdAt: Long,
    open val isOnline: Boolean,
    open val listDialogsId: List<Long> = emptyList(),
    open val icon: Uri? = null,
    open val background: Uri? = null,
) : Parcelable {

    constructor(id: String, name: String, email: String, createdAt: Long, isOnline: Boolean) : this(
        id = id,
        name = name,
        email = email,
        createdAt = createdAt,
        isOnline = isOnline,
        listDialogsId = emptyList()
    )

    constructor() : this(
        id = "null",
        name = "null",
        email = "null",
        createdAt = 0,
        isOnline = false,
        listDialogsId = emptyList()
    )

}