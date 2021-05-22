package com.koleychik.models

import com.koleychik.models.users.User
import com.koleychik.models.users.UserRoot

fun User.asRoot() = UserRoot(
    id,
    name,
    email,
    createdAt,
    isOnline,
    icon,
    background
)