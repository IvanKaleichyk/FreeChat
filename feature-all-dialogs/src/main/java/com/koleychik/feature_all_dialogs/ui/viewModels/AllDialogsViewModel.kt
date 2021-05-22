package com.koleychik.feature_all_dialogs.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.core_database.api.DialogsRepository
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

    fun getDialogs(userId: String) =
        viewModelScope.launch(Dispatchers.Main) {
            _dialogsState.value = DataState.Loading

            val result: DataState
            withContext(Dispatchers.IO) {
                result = repository.getAllDialogs(userId).toDataState()
            }

            _dialogsState.value = result
        }

    fun getFavoritesDialogs(userId: String) = viewModelScope.launch(Dispatchers.Main) {

        _dialogsState.value = DataState.Loading

        val result: DataState
        withContext(Dispatchers.IO) {
            result = repository.getFavoritesDialogs(userId).toDataState()
        }

        _dialogsState.value = result
    }

    fun resetListDialogs() {
        _dialogsState.value = DataState.WaitingForStart
    }

}