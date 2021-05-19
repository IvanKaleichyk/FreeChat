package com.koleychik.feature_all_dialogs.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.models.results.dialog.toDataState
import com.koleychik.models.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AllDialogsViewModel @Inject constructor(
    private val repository: DialogsRepository
) : ViewModel() {

    private val _dialogsState = MutableLiveData<DataState>(DataState.WaitingForStart)
    val dialogState: LiveData<DataState> get() = _dialogsState

    fun getDialogs(listIds: List<Long>, start: Int, end: Long) =
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                _dialogsState.value = DataState.Loading
            }

            val result = repository.getDialogs(listIds, start, end).toDataState()

            withContext(Dispatchers.Main) {
                _dialogsState.value = result
            }
        }

    fun getFavoritesDialogs(listIds: List<Long>) = viewModelScope.launch(Dispatchers.IO) {

        withContext(Dispatchers.Main) {
            _dialogsState.value = DataState.Loading
        }

        val result = repository.getFavoritesDialogs(listIds).toDataState()

        withContext(Dispatchers.Main) {
            _dialogsState.value = result
        }
    }

    fun resetListDialogs() {
        _dialogsState.value = DataState.WaitingForStart
    }

}