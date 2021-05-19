package com.koleychik.models.states

sealed class CheckDataState{
    object Checking: CheckDataState()
    object Successful: CheckDataState()
    class Error(val message: String): CheckDataState()
}
