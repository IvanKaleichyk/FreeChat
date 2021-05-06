package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kaleichyk.core_database.api.MessagesRepository
import com.koleychik.models.Message
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.constants.MessageConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.MessagesResult
import javax.inject.Inject

internal class MessagesRepositoryImpl @Inject constructor() : MessagesRepository {

    private val store = FirebaseFirestore.getInstance()

    override fun getMessages(
        dialogId: Long,
        start: Int,
        end: Long,
        res: (MessagesResult) -> Unit
    ) {
        store.collection(MessageConstants.ROOT_PATH)
            .document()
            .collection(dialogId.toString())
            .startAfter(start)
            .limit(end)
            .get()
            .addOnSuccessListener { result ->
                val listMessages = mutableListOf<Message>()
                for (i in result) listMessages.add(i.toObject(Message::class.java))
                res(MessagesResult.Successful(listMessages))
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(MessagesResult.ServerError(it.localizedMessage!!))
                else res(MessagesResult.ServerError(it.message.toString()))
                res(MessagesResult.ServerError(it.message.toString()))
            }
    }

    override fun addMessage(message: Message, res: (CheckResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
            .document()
            .collection(message.dialogId.toString())
            .document(message.id.toString())
            .set(message)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun delete(message: Message, res: (CheckResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
            .document()
            .collection(message.dialogId.toString())
            .document(message.id.toString())
            .delete()
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun editMessage(message: Message, res: (CheckResult) -> Unit) {
        store.collection("${DialogConstants.ROOT_PATH}/${message.dialogId}/${MessageConstants.ROOT_PATH}/${message.id}")
            .add(message)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }
}