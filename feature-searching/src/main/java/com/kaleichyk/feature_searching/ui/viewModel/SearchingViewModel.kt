package com.kaleichyk.feature_searching.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaleichyk.feature_searching.SearchingDataSource
import com.koleychik.models.results.user.UsersResult
import javax.inject.Inject

internal class SearchingViewModel @Inject constructor(
    private val dataSource: SearchingDataSource
) : ViewModel() {

    val serverResponse = MutableLiveData<UsersResult>(null)

    fun searchUsersByName(name: String) {
        dataSource.searchByName(name) {
            serverResponse.value = it
        }
    }

    fun get50LastUsers() {
        dataSource.get50LastUsers {
            serverResponse.value = it
        }
    }

}