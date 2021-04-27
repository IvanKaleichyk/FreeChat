package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.models.Dialog
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.DialogsResult
import javax.inject.Inject

internal class DialogsRepositoryImpl @Inject constructor() : DialogsRepository {

    private val store = FirebaseFirestore.getInstance()

    override fun getDialogs(start: Int, end: Long, res: (DialogsResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
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

    override fun getFavoritesDialogs(res: (DialogsResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
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

    override fun addDialog(dialog: Dialog, res: (CheckResult) -> Unit) {
        store.collection("${DialogConstants.ROOT_PATH}/${dialog.id}")
            .add(dialog)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                if (it.localizedMessage != null) res(CheckResult.ServerError(it.localizedMessage!!))
                else res(CheckResult.ServerError(it.message.toString()))
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
}