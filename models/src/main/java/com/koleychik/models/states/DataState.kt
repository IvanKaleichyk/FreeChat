package com.koleychik.models.states

sealed class DataState {
    object WaitingForStart : DataState()
    object Loading : DataState()
    class Result<T>(val body: T) : DataState()
    class Error(val message: String) : DataState()
}
