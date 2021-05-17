package com.kaleichyk.feature_searching.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleichyk.feature_searching.SearchingManager
import com.koleychik.models.results.user.UsersResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchingViewModel @Inject constructor(
    private val manager: SearchingManager
) : ViewModel() {

    val serverResponse = MutableLiveData<UsersResult>(null)

    fun searchUsersByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.searchByName(name)
        withContext(Dispatchers.Main) {
            serverResponse.value = result
        }
    }

    fun get50LastUsers() = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.get50LastUsers()
        withContext(Dispatchers.IO) {
            serverResponse.value = result
        }
    }

}