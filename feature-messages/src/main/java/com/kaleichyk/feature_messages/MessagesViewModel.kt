package com.kaleichyk.feature_messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.MessagesResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val manager: MessageManager
) : ViewModel() {

    private val messagesRequest = MutableLiveData<MessagesResult>(null)

    private val _fullMessagesList = MutableLiveData<List<Message>>(null)
    val fullMessagesList: LiveData<List<Message>> get() = _fullMessagesList

    private val _error = MutableLiveData<String>(null)
    val error: LiveData<String> get() = _error

    private val _serverRequest = MutableLiveData<CheckResult>(null)
    val serverRequest: LiveData<CheckResult> get() = _serverRequest


    fun getMessages(dialogId: Long, startAt: Int, endAt: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = manager.getMessages(dialogId, startAt, endAt)
            withContext(Dispatchers.IO) {
                messagesRequest.value = result
            }

        }

    fun sendMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.sendMessage(message)
        withContext(Dispatchers.Main) {
            _serverRequest.value = result
        }
    }

    fun deleteMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.deleteMessage(message)
        withContext(Dispatchers.Main) {
            _serverRequest.value = result
        }
    }
}