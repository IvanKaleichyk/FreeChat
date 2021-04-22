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
        dialogId: String,
        startAt: Int,
        endAt: Int,
        res: (MessagesResult) -> Unit
    ) {
        store.collection("${DialogConstants.ROOT_PATH}/$dialogId/${MessageConstants.ROOT_PATH}")
            .startAt(startAt)
            .endAt(endAt)
            .get()
            .addOnSuccessListener { result ->
                val listMessages = mutableListOf<Message>()
                for (i in result) listMessages.add(i.toObject(Message::class.java))
                res(MessagesResult.Successful(listMessages))
            }
            .addOnFailureListener {
                res(MessagesResult.ServerError(it.message.toString()))
            }
    }

    override fun addMessage(message: Message, res: (CheckResult) -> Unit) {
        store.collection("${DialogConstants.ROOT_PATH}/${message.dialogId}/${MessageConstants.ROOT_PATH}/${message.id}")
            .add(message)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun delete(message: Message, res: (CheckResult) -> Unit) {
        store.collection("${DialogConstants.ROOT_PATH}/${message.dialogId}/${MessageConstants.ROOT_PATH}")
            .document(message.id.toString()).delete()
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun editMessage(message: Message, res: (CheckResult) -> Unit) {
        store.collection("${DialogConstants.ROOT_PATH}/${message.dialogId}/${MessageConstants.ROOT_PATH}/${message.id}")
            .add(message)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                res(CheckResult.ServerError(it.message.toString()))
            }
    }
}