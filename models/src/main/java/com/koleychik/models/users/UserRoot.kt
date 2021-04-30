package com.koleychik.models.users

data class UserRoot(
    override val id: String,
    override var name: String,
    override var email: String,
    override val createdAt: Long,
    override var isOnline: Boolean,
    override val listDialogsId: MutableList<Long> = mutableListOf(),
    override var icon: String? = null,
    override var background: String? = null,
) : User() {

    constructor(id: String, name: String, email: String, createdAt: Long, isOnline: Boolean) : this(
        id = id,
        name = name,
        email = email,
        createdAt = createdAt,
        isOnline = isOnline,
        listDialogsId = mutableListOf()
    )

    constructor() : this(
        id = "null",
        name = "null",
        email = "null",
        createdAt = 0,
        isOnline = false,
        listDialogsId = mutableListOf()
    )

}