package com.koleychik.core_authorization.extencions

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.koleychik.models.User

fun createUserFirstTime(uid: String, name: String, email: String) = User(
    id = uid,
    name = name,
    email,
    icon = null,
    background = null
)

fun GoogleSignInAccount.toUser(uid: String) = User(
    id = uid,
    name = displayName.toString(),
    email = email.toString(),
    icon = photoUrl,
    background = null
)