package com.koleychik.core_authentication.extencions

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.koleychik.models.users.User
import java.util.*

fun createUserFirstTime(uid: String, name: String, email: String) =
    User(
        uid,
        name,
        email,
        Date().time,
        false
    )

fun GoogleSignInAccount.toUser(uid: String) =
    User(
        uid,
        displayName.toString(),
        email.toString(),
        Date().time,
        false,
        listOf(),
        if (photoUrl != null) photoUrl.toString() else null
    )