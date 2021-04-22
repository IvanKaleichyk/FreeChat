package com.koleychik.core_authentication.extencions

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.koleychik.models.users.User

fun createUserFirstTime(uid: String, name: String, email: String) =
    User(
        uid,
        name,
        email,
        listOf()
    )

fun GoogleSignInAccount.toUser(uid: String) =
    User(
        uid,
        displayName.toString(),
        email.toString(),
        listOf(),
        photoUrl
    )