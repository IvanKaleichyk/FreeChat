package com.kaleichyk.core_database.impl

import android.util.Log
import com.google.firebase.firestore.*
import com.kaleichyk.core_database.*
import com.kaleichyk.core_database.R
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.utils.*
import com.koleychik.models.Message
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.dialog.Dialog
import com.koleychik.models.dialog.DialogDTO
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class DialogsRepositoryImpl @Inject constructor() : DialogsRepository {

    private val store = FirebaseFirestore.getInstance()

    private val mapListenersRegistrations = mutableMapOf<String, ListenerRegistration>()

    override suspend fun getAllDialogs(userId: String): DialogsResult {
        val query = store.collection(DialogConstants.ROOT_PATH)
            .whereArrayContains(DialogConstants.LIST_USERS_IDS, userId)

        return try {
            val result = query.get().await().toListFromQuerySnapshot(DialogDTO::class.java)

            DialogsResult.Successful(result.toListDialogs())
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, e.message.toString())
            e.toDialogsResultError()
        }
    }

    override suspend fun getFavoritesDialogs(userId: String): DialogsResult {
        val query = store
            .collection(DialogConstants.ROOT_PATH)
            .whereArrayContains(DialogConstants.LIST_USERS_IDS, userId)
            .whereEqualTo(DialogConstants.IS_FAVORITE, true)

        return try {
            val result = query.get().await()
            DialogsResult.Successful(
                result.toListFromQuerySnapshot(DialogDTO::class.java).toListDialogs()
            )
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, e.message.toString())
            e.toDialogsResultError()
        }
    }

    override suspend fun addDialog(dialog: DialogDTO): DialogResult {
        val collection = store.collection(DialogConstants.ROOT_PATH)

        if (checkIfDialogExists(collection, dialog.listUsersIds))
            return getDialogResultError(R.string.dialog_exits)

        val document = collection.document(dialog.id)

        return try {
            document.set(dialog).await()
            DialogResult.Successful(dialog.toDialog())
        } catch (e: FirebaseFirestoreException) {
            e.toDialogResultError()
        }
    }

    private suspend fun checkIfDialogExists(
        collection: CollectionReference,
        usersIds: List<String>
    ): Boolean {

        val query = collection.whereIn(DialogConstants.USERS, usersIds)

        val result: QuerySnapshot
        try {
            result = query.get().await()
        } catch (e: FirebaseFirestoreException) {
            if (e.code == FirebaseFirestoreException.Code.NOT_FOUND) return false
            else throw e
        }

        if (result.isEmpty) return false

        return try {
            for (i in result) i.toObject(Dialog::class.java)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun delete(dialog: DialogDTO): CheckResult {
        val document = store.collection(DialogConstants.ROOT_PATH)
            .document(dialog.id)

        return try {
            document.delete().await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override suspend fun addLastMessage(dialogId: String, message: Message): CheckResult {
        val document = store.collection(DialogConstants.ROOT_PATH).document(dialogId)

        return try {
            document.update(DialogConstants.LAST_MESSAGE, message)
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override fun subscribeToNewMessages(
        dialogId: String,
        onCameNewLetters: (newMessage: Message) -> Unit
    ) {
        val document = store.collection(DialogConstants.ROOT_PATH).document(dialogId)
        val listenerRegistration = document.addSnapshotListener { value, error ->
            when {
                error != null -> showLog(error.message.toString())
                value == null -> showLog("subscribeToNewMessages value = $value")
                else -> {
                    value.toObject(DialogDTO::class.java)?.lastMessage?.let { newMessage ->
                        onCameNewLetters(newMessage)
                    }
                }
            }
        }

        mapListenersRegistrations[dialogId] = listenerRegistration
    }

    override fun unsubscribeToNewMessages(dialogId: String) {
        mapListenersRegistrations[dialogId]?.remove()
        mapListenersRegistrations.remove(dialogId)
    }
}