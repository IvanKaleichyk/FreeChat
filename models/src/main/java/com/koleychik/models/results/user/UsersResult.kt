package com.koleychik.models.results.user

import com.koleychik.models.states.DataState
import com.koleychik.models.users.User

sealed class UsersResult {
    class Successful(val list: List<User>) : UsersResult()
    class Error(val message: String) : UsersResult()
}

fun UsersResult.toDataState(): DataState {
    return when (this) {
        is UsersResult.Successful -> DataState.Result(list)
        is UsersResult.Error -> DataState.Error(message)
    }
}
