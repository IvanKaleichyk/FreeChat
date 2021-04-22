package com.koleychik.feature_start.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.result.VerificationResult
import com.koleychik.feature_start.StartRepository
import com.koleychik.models.results.user.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val repository: StartRepository,
) : ViewModel() {

    val userResult = MutableLiveData<UserResult>(null)

    val verificationResult = MutableLiveData<VerificationResult>(null)

    fun verifyEmail() {
        val checkRes = repository.checkVerifiedEmail {
            verificationResult.value = it
        }
        if (checkRes) {
            verificationResult.value = VerificationResult.Successful
        }
    }

    fun checkUser() = viewModelScope.launch(Dispatchers.IO) {
        repository.checkUser {
            viewModelScope.launch {
                userResult.value = it
            }
        }
    }
}