package com.koleychik.models.results

import com.koleychik.models.Message

sealed class MessagesResult {
    class Successful(list: List<Message>) : MessagesResult()
    class DataError(message: Int) : MessagesResult()
    class ServerError(message: String) : MessagesResult()
}