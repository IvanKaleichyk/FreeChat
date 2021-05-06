package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kaleichyk.core_database.R
import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.models.Dialog
import com.koleychik.models.Message
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.dialog.DialogResult
import com.koleychik.models.results.dialog.DialogsResult
import com.koleychik.models.users.User
import javax.inject.Inject

internal class DialogsRepositoryImpl @Inject constructor() : DialogsRepository {

    private val store = FirebaseFirestore.getInstance()

    override fun getDialogs(
        listIds: List<Long>,
        start: Int,
        end: Long,
        res: (DialogsResult) -> Unit
    ) {
        if (listIds.isEmpty()) {
            res(DialogsResult.Successful(listOf()))
            return
        }
        store.collection(DialogConstants.ROOT_PATH)
            .whereIn(DialogConstants.ID, listIds)
            .orderBy(DialogConstants.IS_FAVORITE)
            .startAfter(start)
            .limit(end)
            .get()
            .addOnSuccessListener { result ->
                val listDialogs = mutableListOf<Dialog>()
                for (i in result) listDialogs.add(i.toObject(Dialog::class.java))
                res(DialogsResult.Successful(listDialogs))
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(DialogsResult.ServerError(it.localizedMessage!!))
                else res(DialogsResult.ServerError(it.message.toString()))
            }
    }

    override fun getFavoritesDialogs(listIds: List<Long>, res: (DialogsResult) -> Unit) {
        if (listIds.isEmpty()) {
            res(DialogsResult.Successful(listOf()))
            return
        }
        store.collection(DialogConstants.ROOT_PATH)
            .whereIn(DialogConstants.ID, listIds)
            .whereEqualTo(DialogConstants.IS_FAVORITE, true)
            .get()
            .addOnSuccessListener { result ->
                val listDialogs = mutableListOf<Dialog>()
                for (i in result) listDialogs.add(i.toObject(Dialog::class.java))
                res(DialogsResult.Successful(listDialogs))
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(DialogsResult.ServerError(it.localizedMessage!!))
                else res(DialogsResult.ServerError(it.message.toString()))
            }
    }

    override fun addDialog(dialog: Dialog, res: (DialogResult) -> Unit) {
        val collection = store.collection(DialogConstants.ROOT_PATH)
        checkIfDialogExists(collection, dialog.users) { isSuccessful ->
            if (isSuccessful) res(DialogResult.DataError(R.string.dialog_exits))
            else collection.document(dialog.id.toString()).set(dialog)
                .addOnSuccessListener {
                    res(DialogResult.Successful(dialog))
                }
                .addOnFailureListener {
                    if (it.localizedMessage != null) res(DialogResult.ServerError(it.localizedMessage!!))
                    else res(DialogResult.ServerError(it.message.toString()))
                }
        }
    }

    private fun checkIfDialogExists(
        collection: CollectionReference,
        users: List<User>,
        res: (isSuccessful: Boolean) -> Unit
    ) {
        collection
            .whereIn(DialogConstants.USERS, users)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) res(false)
                for (i in it) {
                    val dialog = try {
                        i.toObject(Dialog::class.java)
                    } catch (e: Exception) {
                        null
                    }
                    if (dialog != null) {
                        res(true)
                        return@addOnSuccessListener
                    }
                }
                res(false)
            }
            .addOnFailureListener {
                res(false)
            }
    }

    override fun delete(dialog: Dialog, res: (CheckResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
            .document(dialog.id.toString()).delete()
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun addLastMessage(dialogId: Long, message: Message, res: (CheckResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
            .document(dialogId.toString())
            .update(DialogConstants.LAST_MESSAGE, message)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
            }
    }
}