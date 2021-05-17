package com.koleychik.feature_all_dialogs.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.models.results.dialog.DialogsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AllDialogsViewModel @Inject constructor(
    private val repository: DialogsRepository
) : ViewModel() {

    private val _dialogsResponse = MutableLiveData<DialogsResult>(null)
    val dialogsResponse: LiveData<DialogsResult> get() = _dialogsResponse

    fun getDialogs(listIds: List<Long>, start: Int, end: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getDialogs(listIds, start, end)
            withContext(Dispatchers.Main) {
                _dialogsResponse.value = result
            }
        }

    fun getFavoritesDialogs(listIds: List<Long>) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.getFavoritesDialogs(listIds)
        withContext(Dispatchers.Main) {
            _dialogsResponse.value = result
        }
    }

    fun resetListDialogs() {
        _dialogsResponse.value = null
    }

}