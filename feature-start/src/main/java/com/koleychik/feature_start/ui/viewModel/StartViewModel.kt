package com.koleychik.feature_start.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.feature_start.StartManager
import com.koleychik.models.results.CheckResult
import com.koleychik.models.results.user.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val manager: StartManager,
) : ViewModel() {

    val userResult = MutableLiveData<UserResult>(null)

    val verificationResult = MutableLiveData<CheckResult>(null)

//    fun verifyEmail() {
//        val checkRes = manager.checkVerifiedEmail {
//            verificationResult.value = it
//        }
//        if (checkRes) {
//            verificationResult.value = VerificationResult.Successful
//        }
//    }

    fun checkUser() = viewModelScope.launch(Dispatchers.IO) {
        manager.checkUser {
            viewModelScope.launch {
                userResult.value = it
            }
        }
    }
}