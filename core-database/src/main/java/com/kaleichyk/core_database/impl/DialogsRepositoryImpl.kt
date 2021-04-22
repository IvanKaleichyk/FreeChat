package com.kaleichyk.core_database.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.models.Dialog
import com.koleychik.models.constants.DialogConstants
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.DialogsResult
import javax.inject.Inject

internal class DialogsRepositoryImpl @Inject constructor(): DialogsRepository {

    private val store = FirebaseFirestore.getInstance()

    override fun getDialogs(startAt: Int, endAt: Int?, res: (DialogsResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
            .startAt(startAt)
            .endAt(endAt)
            .get()
            .addOnSuccessListener { result ->
                val listDialogs = mutableListOf<Dialog>()
                for (i in result) listDialogs.add(i.toObject(Dialog::class.java))
                res(DialogsResult.Successful(listDialogs))
            }
            .addOnFailureListener {
                res(DialogsResult.ServerError(it.message.toString()))
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
                res(DialogsResult.ServerError(it.message.toString()))
            }
    }

    override fun addDialog(dialog: Dialog, res: (CheckResult) -> Unit) {
        store.collection("${DialogConstants.ROOT_PATH}/${dialog.id}")
            .add(dialog)
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                res(CheckResult.ServerError(it.message.toString()))
            }
    }

    override fun delete(dialog: Dialog, res: (CheckResult) -> Unit) {
        store.collection(DialogConstants.ROOT_PATH)
            .document(dialog.id.toString()).delete()
            .addOnSuccessListener {
                res(CheckResult.Successful)
            }
            .addOnFailureListener {
                res(CheckResult.ServerError(it.message.toString()))
            }
    }
}