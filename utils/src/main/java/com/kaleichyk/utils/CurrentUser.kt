package com.kaleichyk.utils

import com.koleychik.models.users.UserRoot

object CurrentUser {

    var user: UserRoot? = null
        set(value) {
            whenSetUser?.let {
                it(field, value)
            }
            field = value
        }

    var whenSetUser: ((lastUser: UserRoot?, newUser: UserRoot?) -> Unit)? = null

}