package com.koleychik.feature_password_utils.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.checkEmail
import com.koleychik.models.results.CheckResult
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

        val result = repository.resetPassword(email)

        withContext(Dispatchers.Main){
            checkRes.value = result
            isLoading.value = false
        }
    }

}