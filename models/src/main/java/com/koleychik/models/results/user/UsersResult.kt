package com.koleychik.models.results.user

import com.koleychik.models.states.DataState
import com.koleychik.models.users.User

sealed class UsersResult {
    class Successful(val list: List<User>) : UsersResult()
    class Error(val message: String) : UsersResult()

    fun toDataState(): DataState {
        return when (this) {
            is Successful -> DataState.Result(list)
            is Error -> DataState.Error(message)
        }
    }
}
