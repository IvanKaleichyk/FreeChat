package com.koleychik.feature_password_utils.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.checkEmail
import com.koleychik.core_authentication.result.CheckResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpecifyEmailViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val isLoading = MutableLiveData(false)
    val checkRes = MutableLiveData<CheckResult>(null)

    fun resetPassword(email: String) = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            isLoading.value = true
        }

        val emailRes = checkEmail(email)
        if (emailRes !is CheckResult.Successful) {
            withContext(Dispatchers.Main) {
                checkRes.value = emailRes
                isLoading.value = false
            }
            return@launch
        }

        repository.resetPassword(email) {
            viewModelScope.launch(Dispatchers.Main) {
                checkRes.value = it
                isLoading.value = false
            }
        }
    }

}