package com.kaleichyk.feature_messages.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.feature_messages.MessageData
import com.kaleichyk.feature_messages.MessagesManager
import com.koleychik.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val manager: MessagesManager
) : ViewModel() {

    private var _fullMessagesList : MutableList<MessageData>? = null
    val fullMessagesList: List<MessageData>? get() = _fullMessagesList

    fun addToList(addList: List<MessageData>) {
        if (_fullMessagesList == null) _fullMessagesList = mutableListOf()
        _fullMessagesList?.addAll(addList)
    }

    fun sendMessage(message: Message, topic: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.sendMessage(message, topic)
//        withContext(Dispatchers.Main) {
//            _sendMessageRequest.value = result
//        }
    }

    fun deleteMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.deleteMessage(message)
//        withContext(Dispatchers.Main) {
//            _serverRequest.value = result
//        }
    }
}