package com.koleychik.models.results

import com.koleychik.models.Message
import com.koleychik.models.states.DataState

sealed class MessagesResult {
    class Successful(val list: List<Message>) : MessagesResult()
    class Error(val message: String) : MessagesResult()
}

fun MessagesResult.toDataState(): DataState {
    return when (this) {
        is MessagesResult.Successful -> DataState.Result(list)
        is MessagesResult.Error -> DataState.Error(message)
    }
}
