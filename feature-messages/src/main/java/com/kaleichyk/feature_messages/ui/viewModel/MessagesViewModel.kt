package com.kaleichyk.feature_messages.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaleichyk.core_database.messagesUtils.MessagesPaginationResult
import com.kaleichyk.core_database.messagesUtils.PaginationLastState
import com.kaleichyk.feature_messages.MessageData
import com.kaleichyk.feature_messages.MessagesManager
import com.kaleichyk.feature_messages.toMessagesData
import com.kaleichyk.utils.showLog
import com.koleychik.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessagesViewModel @Inject constructor(
    private val manager: MessagesManager
) : ViewModel() {

    private var _fullMessagesList: MutableList<MessageData>? = null
    val fullMessagesList: List<MessageData>? get() = _fullMessagesList

    var selectedImageUri: String? = null

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    var lastState: PaginationLastState = PaginationLastState.FirstTime
    private set

    fun getMessages(dialogId: String) = flow<List<MessageData>> {
        _isLoading.value = true

        val result: MessagesPaginationResult
        withContext(Dispatchers.IO) {
            result = manager.getMessages(dialogId, lastState)
        }

        if (result !is MessagesPaginationResult.Successful) return@flow

        lastState = result.state

        val list = result.list.toMessagesData()
        showLog("dialogId = $dialogId")
        showLog("viewModel list = $list")

        if (_fullMessagesList == null) _fullMessagesList = mutableListOf()
        _fullMessagesList!!.addAll(list)

        emit(list)
        _isLoading.value = false
    }

    fun sendMessage(message: Message, topic: String) = CoroutineScope(Dispatchers.Main).launch {
        manager.sendMessage(message, topic)
    }

    fun deleteMessage(message: Message) = CoroutineScope(Dispatchers.Main).launch {
        manager.deleteMessage(message)
    }

    fun subscribeToNewMessages(
        dialogId: String,
        onCameNewLetters: (newMessage: Message) -> Unit
    ) {
        manager.subscribeToNewMessages(dialogId, onCameNewLetters)
    }

    fun unsubscribeToNewMessages(dialogId: String) {
        manager.unsubscribeToNewMessages(dialogId)
    }
}