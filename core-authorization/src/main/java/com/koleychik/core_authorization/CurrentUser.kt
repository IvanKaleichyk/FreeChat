package com.koleychik.core_authorization

import com.koleychik.models.User

object CurrentUser {

    @Volatile
    private var user: User? = null

    fun getCurrentUser(): User {
        if (user == null) synchronized(CurrentUser::class.java) {
            if (user == null) user = TODO("get user from db")
        }
        return user!!
    }

}