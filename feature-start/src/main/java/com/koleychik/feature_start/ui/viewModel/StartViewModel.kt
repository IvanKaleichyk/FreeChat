package com.koleychik.feature_start.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.feature_start.StartManager
import com.koleychik.models.states.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val manager: StartManager,
) : ViewModel() {

    private val _userState = MutableLiveData<DataState>(DataState.WaitingForStart)
    val userState: LiveData<DataState> get() = _userState

    fun checkUser(): Job = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.checkUser().toDataState()
        withContext(Dispatchers.Main) {
            _userState.value = result
        }
    }
}