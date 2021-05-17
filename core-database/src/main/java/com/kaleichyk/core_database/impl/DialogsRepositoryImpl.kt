package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.getListFromQuerySnapshot
import com.kaleichyk.core_database.toCheckResultError
import com.kaleichyk.core_database.toDialogResultError
import com.kaleichyk.core_database.toDialogsResultError
import com.koleychik.models.Dialog
import com.koleychik.models.Message
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult
import com.koleychik.models.users.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class DialogsRepositoryImpl @Inject constructor() : DialogsRepository {

    private val store = FirebaseFirestore.getInstance()

    override suspend fun getDialogs(listIds: List<Long>, start: Int, end: Long): DialogsResult {
        if (listIds.isEmpty()) {
            return DialogsResult.Successful(listOf())
        }
        return try {
            val result = store.collection(DialogConstants.ROOT_PATH)
                .whereIn(DialogConstants.ID, listIds)
                .orderBy(DialogConstants.IS_FAVORITE)
                .startAfter(start)
                .limit(end)
                .get()
                .await()

            DialogsResult.Successful(result.getListFromQuerySnapshot(Dialog::class.java))
        } catch (e: FirebaseFirestoreException) {
            e.toDialogsResultError()
        }
    }

    override suspend fun getFavoritesDialogs(listIds: List<Long>): DialogsResult {
        if (listIds.isEmpty()) return DialogsResult.Successful(listOf())

        return try {
            val result = store.collection(DialogConstants.ROOT_PATH)
                .whereIn(DialogConstants.ID, listIds)
                .whereEqualTo(DialogConstants.IS_FAVORITE, true)
                .get()
                .await()

            DialogsResult.Successful(result.getListFromQuerySnapshot(Dialog::class.java))
        } catch (e: FirebaseFirestoreException) {
            e.toDialogsResultError()
        }
    }

    override suspend fun addDialog(dialog: Dialog): DialogResult {
        val document = store.collection(DialogConstants.ROOT_PATH).document(dialog.id.toString())

        return try {
            document.set(dialog).await()
            DialogResult.Successful(dialog)
        } catch (e: FirebaseFirestoreException) {
            e.toDialogResultError()
        }
    }

    private suspend fun checkIfDialogExists(
        collection: CollectionReference,
        users: List<User>
    ): Boolean {

        val query = collection.whereIn(DialogConstants.USERS, users)

        val result: QuerySnapshot
        try {
            result = query.get().await()
        } catch (e: FirebaseFirestoreException) {
            if (e.code == FirebaseFirestoreException.Code.NOT_FOUND) return false
            else throw e
        }

        return try {
            result.toObjects(Dialog::class.java)
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