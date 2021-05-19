package com.kaleichyk.feature_searching.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.feature_searching.SearchingManager
import com.koleychik.models.results.user.toDataState
import com.koleychik.models.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchingViewModel @Inject constructor(
    private val manager: SearchingManager
) : ViewModel() {

    private val _usersState = MutableLiveData<DataState>(DataState.WaitingForStart)
    val usersState: LiveData<DataState> get() = _usersState

    private lateinit var lastSearchingWord: String

    fun searchUsersByName(name: String) = viewModelScope.launch(Dispatchers.IO) {

        if (lastSearchingWord == name) return@launch
        lastSearchingWord = name

        withContext(Dispatchers.Main) {
            _usersState.value = DataState.Loading
        }

        val result = manager.searchByName(name).toDataState()
        withContext(Dispatchers.Main) {
            _usersState.value = result
        }
    }

    fun get50LastUsers() = viewModelScope.launch(Dispatchers.IO) {

        if (::lastSearchingWord.isInitialized) {
            if (lastSearchingWord.isEmpty()) return@launch
        }
        lastSearchingWord = ""

        withContext(Dispatchers.Main) {
            _usersState.value = DataState.Loading
        }

        val result = manager.get50LastUsers().toDataState()

        withContext(Dispatchers.Main) {
            _usersState.value = result
        }
    }

}