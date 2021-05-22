package com.koleychik.models.results.user

import com.koleychik.models.states.CheckDataState
import com.koleychik.models.states.DataState
import com.koleychik.models.users.User

sealed class UserResult {
    class Successful(val user: User) : UserResult()
    class Error(val message: String) : UserResult()

    fun toCheckDataState(): CheckDataState {
        return when (this) {
            is Successful -> CheckDataState.Successful
            is Error -> CheckDataState.Error(message)
        }
    }

    fun toDataState(): DataState {
        return when (this) {
            is Successful -> DataState.Result(user)
            is Error -> DataState.Error(message)
        }
    }
}