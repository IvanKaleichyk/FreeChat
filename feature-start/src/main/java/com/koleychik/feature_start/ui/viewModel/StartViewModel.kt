package com.koleychik.feature_start.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.feature_start.StartManager
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val manager: StartManager,
) : ViewModel() {

    val userResult = MutableLiveData<UserResult>(null)

    val verificationResult = MutableLiveData<CheckResult>(null)

    fun checkUser(): Job = viewModelScope.launch(Dispatchers.IO) {
        val result = manager.checkUser()
        withContext(Dispatchers.Main) {
            userResult.value = result
        }
    }
}