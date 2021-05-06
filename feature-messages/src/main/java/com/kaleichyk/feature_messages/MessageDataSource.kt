package com.kaleichyk.feature_messages

import android.content.Context
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.MessagesRepository
import com.koleychik.core_notifications.repositories.MessageNotificationRepository
import com.koleychik.models.Message
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.MessagesResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageDataSource @Inject constructor(
    private val context: Context,
    private val notificationRepository: MessageNotificationRepository,
    private val messagesRepository: MessagesRepository,
    private val dialogsRepository: DialogsRepository
) {

    fun getMessages(dialogId: Long, startAt: Int, endAt: Long, res: (MessagesResult) -> Unit) {
        messagesRepository.getMessages(dialogId, startAt, endAt, res)
    }

    fun sendMessage(message: Message, res: (CheckResult) -> Unit) {
        messagesRepository.addMessage(message) { addMessageResult ->
            if (addMessageResult !is CheckResult.Successful) res(addMessageResult)
            else dialogsRepository.addLastMessage(message.dialogId, message) {
                res(it)
                sendMessageNotification(message)
            }

        }
    }

    fun deleteMessage(message: Message, res: (CheckResult) -> Unit) {
        messagesRepository.delete(message, res)
    }

    fun editMessage(message: Message, res: (CheckResult) -> Unit) {
        messagesRepository.editMessage(message, res)
    }

    private fun sendMessageNotification(message: Message) = CoroutineScope(Dispatchers.IO).launch {
        notificationRepository.sendMessageNotification(
            message,
            context.getString(R.string.message),
            null
        )
    }

}