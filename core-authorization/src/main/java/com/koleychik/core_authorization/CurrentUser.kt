package com.koleychik.core_authorization

import com.koleychik.models.User

object CurrentUser {

    var user: User? = null
    get() = field!!

}