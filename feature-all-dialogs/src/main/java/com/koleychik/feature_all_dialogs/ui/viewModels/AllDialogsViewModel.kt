package com.koleychik.feature_all_dialogs.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.models.results.dialog.DialogsResult
import javax.inject.Inject

internal class AllDialogsViewModel @Inject constructor(
    private val repository: DialogsRepository
) : ViewModel() {

    val serverResponse = MutableLiveData<DialogsResult>(null)


    fun getDialogs(listIds: List<Long>, start: Int, end: Long) {
        repository.getDialogs(listIds, start, end) {
            serverResponse.value = it
        }
    }

    fun getFavoritesDialogs(listIds: List<Long>) {
        repository.getFavoritesDialogs(listIds) {
            serverResponse.value = it
        }
    }
}