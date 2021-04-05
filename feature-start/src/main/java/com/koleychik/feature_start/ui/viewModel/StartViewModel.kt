package com.koleychik.feature_start.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.feature_start.StartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val repository: StartRepository,
) : ViewModel() {

    val userResult = MutableLiveData<UserResult>(null)

    val verificationResult = MutableLiveData<CheckResult>(null)

    fun verifyEmail() {
        repository.checkVerifiedEmail { verificationResult.value = it }
    }

    fun checkUser() = viewModelScope.launch(Dispatchers.IO) {
        repository.checkUser {
            viewModelScope.launch {
                userResult.value = it
            }
        }
    }
}