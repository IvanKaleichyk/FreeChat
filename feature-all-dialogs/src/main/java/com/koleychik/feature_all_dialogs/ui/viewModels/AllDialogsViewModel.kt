package com.koleychik.feature_all_dialogs.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.models.results.DialogsResult
import javax.inject.Inject

internal class AllDialogsViewModel @Inject constructor(
    private val repository: DialogsRepository
) : ViewModel() {

    val serverResponse = MutableLiveData<DialogsResult>(null)


    fun getDialogs(start: Int, end: Long) {
        repository.getDialogs(start, end) {
            serverResponse.value = it
        }
    }

    fun getFavoritesDialogs() {
        repository.getFavoritesDialogs {
            serverResponse.value = it
        }
    }
}