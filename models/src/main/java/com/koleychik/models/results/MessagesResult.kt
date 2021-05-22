package com.koleychik.models.results

import com.koleychik.models.Message
import com.koleychik.models.states.DataState

sealed class MessagesResult {
    class Successful(val list: List<Message>) : MessagesResult()
    class Error(val message: String) : MessagesResult()

    fun toDataState(): DataState {
        return when (this) {
            is Successful -> DataState.Result(list)
            is Error -> DataState.Error(message)
        }
    }
}