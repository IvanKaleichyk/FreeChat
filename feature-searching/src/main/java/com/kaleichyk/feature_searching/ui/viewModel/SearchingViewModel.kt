package com.kaleichyk.feature_searching.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaleichyk.feature_searching.SearchingManager
import com.koleychik.models.results.user.UsersResult
import javax.inject.Inject

internal class SearchingViewModel @Inject constructor(
    private val manager: SearchingManager
) : ViewModel() {

    val serverResponse = MutableLiveData<UsersResult>(null)

    fun searchUsersByName(name: String) {
        manager.searchByName(name) {
            serverResponse.value = it
        }
    }

    fun get50LastUsers() {
        manager.get50LastUsers {
            serverResponse.value = it
        }
    }

}