package com.koleychik.models.results

import com.koleychik.models.Message

sealed class MessagesResult {
    class Successful(val list: List<Message>) : MessagesResult()
    abstract class Error : MessagesResult()
    class DataError(val message: Int) : Error()
    class ServerError(val message: String) : Error()
}