package com.koleychik.models.results.user

import com.koleychik.models.states.CheckDataState
import com.koleychik.models.states.DataState
import com.koleychik.models.users.User

sealed class UserResult {
    class Successful(val user: User) : UserResult()
    class Error(val message: String) : UserResult()
}

fun UserResult.toDataState(): DataState {
    return when (this) {
        is UserResult.Successful -> DataState.Result(user)
        is UserResult.Error -> DataState.Error(message)
    }
}

fun UserResult.toCheckDataState(): CheckDataState {
    return when (this) {
        is UserResult.Successful -> CheckDataState.Successful
        is UserResult.Error -> CheckDataState.Error(message)
    }
}