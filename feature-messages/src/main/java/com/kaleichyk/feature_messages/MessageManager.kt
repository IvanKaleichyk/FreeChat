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

class MessageManager @Inject constructor(
    private val context: Context,
    private val notificationRepository: MessageNotificationRepository,
    private val messagesRepository: MessagesRepository,
    private val dialogsRepository: DialogsRepository
) {

    suspend fun getMessages(dialogId: Long, startAt: Int, endAt: Long): MessagesResult =
        messagesRepository.getMessages(dialogId, startAt, endAt)


    suspend fun sendMessage(message: Message): CheckResult {
        var result = messagesRepository.addMessage(message)
        if (result is CheckResult.Successful) {
            result = dialogsRepository.addLastMessage(message.dialogId, message)
            sendMessageNotification(message)
        }
        return result
    }

    suspend fun deleteMessage(message: Message): CheckResult = messagesRepository.delete(message)

//    fun editMessage(message: Message): CheckResult {
//        messagesRepository.editMessage(message, res)


    private fun sendMessageNotification(message: Message) = CoroutineScope(Dispatchers.IO).launch {
        notificationRepository.sendMessageNotification(
            message,
            context.getString(R.string.message),
            null
        )
    }

}