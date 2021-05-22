package com.kaleichyk.core_database.impl

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.kaleichyk.core_database.*
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.utils.TAG
import com.kaleichyk.utils.getDialogResultError
import com.koleychik.models.Message
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.dialog.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class DialogsRepositoryImpl @Inject constructor() : DialogsRepository {

    private val store = FirebaseFirestore.getInstance()

    override suspend fun getAllDialogs(userId: String): DialogsResult {
        val query = store.collection(DialogConstants.ROOT_PATH)
            .whereArrayContains(DialogConstants.LIST_USERS_IDS, userId)

        return try {
            val result = query.get().await().getListFromQuerySnapshot(Dialog::class.java)

            DialogsResult.Successful(result)
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
            DialogsResult.Successful(result.getListFromQuerySnapshot(Dialog::class.java))
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, e.message.toString())
            e.toDialogsResultError()
        }
    }

    override suspend fun addDialog(dialog: Dialog): DialogResult {
        val collection = store.collection(DialogConstants.ROOT_PATH)

        if (checkIfDialogExists(collection, dialog.listUsersIds))
            return getDialogResultError(R.string.dialog_exits)

        val document = collection.document(dialog.id.toString())

        return try {
            document.set(dialog).await()
            DialogResult.Successful(dialog)
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

    override suspend fun delete(dialog: Dialog): CheckResult {
        val document = store.collection(DialogConstants.ROOT_PATH)
            .document(dialog.id.toString())

        return try {
            document.delete().await()
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }

    override suspend fun addLastMessage(dialogId: Long, message: Message): CheckResult {
        val document = store.collection(DialogConstants.ROOT_PATH).document(dialogId.toString())

        return try {
            document.update(DialogConstants.LAST_MESSAGE, message)
            CheckResult.Successful
        } catch (e: FirebaseFirestoreException) {
            e.toCheckResultError()
        }
    }
}