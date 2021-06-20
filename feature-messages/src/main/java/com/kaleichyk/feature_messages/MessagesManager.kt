package com.kaleichyk.feature_messages

import android.content.Context
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.MessagesRepository
import com.kaleichyk.core_database.messagesUtils.PaginationLastState
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessagesManager @Inject constructor(
    private val context: Context,
    private val notificationRepository: MessageNotificationRepository,
    private val messagesRepository: MessagesRepository,
    private val dialogsRepository: DialogsRepository
) {

    suspend fun getMessages(dialogId: String, lastState: PaginationLastState) =
        messagesRepository.getMessages(dialogId, lastState)


    suspend fun sendMessage(message: Message, topic: String): CheckResult {
        var result = messagesRepository.addMessage(message)
        if (result is CheckResult.Successful) {
            result = dialogsRepository.addLastMessage(message.dialogId, message)
//            sendMessageNotification(message, topic)
        }
        return result
    }

    suspend fun deleteMessage(message: Message, newLastMessage: Message?): CheckResult {
        val result = messagesRepository.delete(message)
        if (result !is CheckResult.Successful || newLastMessage == null) return result
        return dialogsRepository.addLastMessage(newLastMessage.dialogId, newLastMessage)
    }

    fun subscribeToNewMessages(
        dialogId: String,
        onCameNewLetters: (newMessage: Message) -> Unit
    ) {
        dialogsRepository.subscribeToNewMessages(dialogId, onCameNewLetters)
    }

    fun unsubscribeToNewMessages(dialogId: String) {
        dialogsRepository.unsubscribeToNewMessages(dialogId)
    }

    private fun sendMessageNotification(message: Message, topic: String) =
        CoroutineScope(Dispatchers.IO).launch {
            notificationRepository.sendMessageNotification(
                message,
                context.getString(R.string.message),
                null,
                topic
            )
        }
}