package com.kaleichyk.feature_messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.MessagesResult
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val dataSource: MessageDataSource
) : ViewModel() {

    private val messagesRequest = MutableLiveData<MessagesResult>(null)

    private val _fullMessagesList = MutableLiveData<List<Message>>(null)
    val fullMessagesList: LiveData<List<Message>> get() = _fullMessagesList

    private val _error = MutableLiveData<String>(null)
    val error: LiveData<String> get() = _error

    private val _serverRequest = MutableLiveData<CheckResult>(null)
    val serverRequest: LiveData<CheckResult> get() = _serverRequest


    fun getMessages(dialogId: Long, startAt: Int, endAt: Long) {
        dataSource.getMessages(dialogId, startAt, endAt) {

        }
    }

    fun sendMessage(message: Message) {
        dataSource.sendMessage(message) {
            _serverRequest.value = it
        }
    }

    fun deleteMessage(message: Message) {
        dataSource.deleteMessage(message) {
            _serverRequest.value = it
        }
    }

    fun editMessage(message: Message) {
        dataSource.editMessage(message) {
            _serverRequest.value = it
        }
    }

}